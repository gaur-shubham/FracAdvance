package com.frac.FracAdvanced.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.MainFracGraphModel;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.repository.MainFracGraphRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class MainFracGraphService {
	
	@Autowired
	MainFracGraphRepo fracGraphRepo;
	@Autowired
	ProjectDetailRepo detailRepo;
	
	public void saveFracGraph(Integer pid,List<String> valuesX,List<String> valuesY) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<MainFracGraphModel> list1=fracGraphRepo.findBydetails(detail);
		List<MainFracGraphModel> list=new ArrayList<>();
		MainFracGraphModel model=new MainFracGraphModel();
		for(int i=0;i<valuesX.size();i++) {
			if(list1.isEmpty()) {
				model=new MainFracGraphModel();
			}
			else {
				model=list1.get(i);
			}
			model.setX(valuesX.get(i));
			model.setY(valuesY.get(i));
			model.setDetails(detail);
			list.add(model);
		}
		fracGraphRepo.saveAll(list);
	}
	
	public List<MainFracGraphModel> showmainfrac(Integer pid){
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		return fracGraphRepo.findBydetails(detail);
	}
	
	public Map<String, List<MainFracGraphModel>> test(Integer pid) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		Map<String, List<MainFracGraphModel>> map = new LinkedMap<>();
		List<MainFracGraphModel> list = fracGraphRepo.findBydetails(detail);
		List<MainFracGraphModel> temp = new ArrayList<>();
		String t = list.get(0).getY();
		Integer j = 1;
		for (Integer i = 0; i < list.size(); i++) {
			temp.add(list.get(i));
			if (list.get(i).getY().equalsIgnoreCase("-" + t)) {
				map.put(j.toString(), temp);
				j++;
				if (i < list.size() - 1) {
					t = list.get(i + 1).getY();
					temp = new ArrayList<>();
				}
			}
		}
		return map;
	}
}
