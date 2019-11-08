/**
 * 
 */
package com.frac.FracAdvanced.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.Method.CreateDefaultData;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.RockPropertiesModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.RockPropertiesRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class RockPropertiesService {

	@Autowired
	RockPropertiesRepo rockRepo;
	@Autowired
	CreateDefaultData create;
	@Autowired
	ProjectDetailRepo detailsRepo;
	
	public void saveData(List<String> value,Integer pid) {
		ProjectDetails details=detailsRepo.findById(pid).orElse(null);
		List<RockPropertiesModel> list=rockRepo.findBydetails(details);
		if(list.get(list.size()-1).getStage().equalsIgnoreCase("N/A")) {
		List<RockPropertiesModel> list1=new ArrayList<>();
		int j=1;
		for(int i=1;i<=list.size();i++) {
			if(list.get(i-1).getStage().equalsIgnoreCase("N/A")) {
			RockPropertiesModel model=list.get(i-1);
			model.setValue(value.get(j));
			model.setStage(value.get(0));
			list1.add(model);
			j++;}
		}
		rockRepo.saveAll(list1);
	}else {
		List<String> param=Arrays.asList("Stage", "Rock Density(lbm/ft3)", "Fracture Gradient(psi/ft)", "Young Modulus(psi)", "Shear Modulus(psi)", "Poisson Ratio", "Rock Embedment Strength(psi)", "Tensile Strength(psi)");
		List<String> value1=Arrays.asList("N/A", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0");
		create.saveRockData(param, value1, pid);
		saveData(value,pid);
	}
	}
	
	
	public void updateData(Integer pid,List<Integer> id,List<String> value) {
		ProjectDetails details=detailsRepo.findById(pid).orElse(null);
		List<RockPropertiesModel> list=rockRepo.findByStageAndDetails(value.get(0), details);
		int j=1;
		for(int i=0;i<id.size();i++) {
			RockPropertiesModel model=list.get(i);
			model.setValue(value.get(j));
			rockRepo.save(model);
			j++;
		}
	}
}
