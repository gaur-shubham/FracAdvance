/**
 * 
 */
package com.frac.FracAdvanced.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.OutputStressModel;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.repository.OutputStressRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class OutputStressService {

	@Autowired
	private OutputStressRepo stressRepo;
	@Autowired
	private ProjectDetailRepo detailRepo;

	public void saveCalc(Map<String, String> BDPress, Map<String, String> depths, Map<String, Double> tmhMap,
			Integer pid) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<OutputStressModel> models1 = stressRepo.findBydetails(detail);
		List<OutputStressModel> models = new ArrayList<OutputStressModel>();
		OutputStressModel model = new OutputStressModel();
		for (int i = 0; i < depths.size(); i++) {
			String key = "BDpressure" + i;
			String key1 = "depth" + i;
			String key2 = "tmh" + i;
			if(models1.size()<=0) {
				model=new OutputStressModel();
			}
			else{
				model=models1.get(i);
			}
			model.setBreakdown(BDPress.get(key));
			model.setDepth(depths.get(key1));
			model.setHorizontal(String.valueOf(tmhMap.get(key2)));
			model.setDetails(detail);
			models.add(model);
		}
		stressRepo.saveAll(models);
	}
	
	public List<OutputStressModel> showList(Integer pid){
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<OutputStressModel> models = stressRepo.findBydetails(detail);
		return models;
	}
}
