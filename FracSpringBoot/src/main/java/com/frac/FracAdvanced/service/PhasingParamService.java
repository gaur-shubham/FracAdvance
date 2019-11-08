package com.frac.FracAdvanced.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.frac.FracAdvanced.model.PhasingParamModel;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.repository.PhasingParamRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class PhasingParamService {
	
	@Autowired	
	private PhasingParamRepo paramRepo;
	@Autowired
	private ProjectDetailRepo detailRepo;
	
	public Map<String,List<String>> PhasingDefault() {
		String[ ] angle= {"0","180","120","90","60","45",};
		String[ ] alpha= {"N/A","0.5","0.648","0.726","0.813","0.860"};
		String[ ] a1= {"-2.091","-2.025","-2.018","-1.905","-1.898","-1.788"};
		String[ ] a2= {"0.0453","0.0943","0.0634","0.1038","0.1023","0.2398"};
		String[ ] b1= {"5.1313","3.0373","1.6136","1.5674","1.3654","1.1915"};
		String[ ] b2= {"1.8672","1.8115","1.7770","1.6935","1.6490","1.6392"};
		String[ ] c1= {"0.16","0.026","0.0066","0.0019","0.0003","0.000046"};
		String[ ] c2= {"2.675","4.532","5.320","6.155","7.509","8.791"};
		Map<String,List<String>> map=new LinkedHashMap<>();
		List<String> angles=Arrays.asList(angle);
		List<String> alphas=Arrays.asList(alpha);
		List<String> a1s=Arrays.asList(a1);
		List<String> a2s=Arrays.asList(a2);
		List<String> b1s=Arrays.asList(b1);
		List<String> b2s=Arrays.asList(b2);
		List<String> c1s=Arrays.asList(c1);
		List<String> c2s=Arrays.asList(c2);
		map.put("angle", angles);
		map.put("alpha", alphas);
		map.put("a1", a1s);
		map.put("a2", a2s);
		map.put("b1", b1s);
		map.put("b2", b2s);
		map.put("c1", c1s);
		map.put("c2", c2s);
		return map;
		
	}
	
	public void savePhasingDefault(Integer pid) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<PhasingParamModel> list = new ArrayList<>();
		Map<String,List<String>> map=PhasingDefault();
		List<String> angles=new ArrayList<String>();
		List<String> alphas=new ArrayList<String>();
		List<String> a1s=new ArrayList<String>();
		List<String> a2s=new ArrayList<String>();
		List<String> b1s=new ArrayList<String>();
		List<String> b2s=new ArrayList<String>();
		List<String> c1s=new ArrayList<String>();
		List<String> c2s=new ArrayList<String>();
		for(String key:map.keySet()) {
			if(key.equalsIgnoreCase("angle")) {
				angles=map.get(key);
			}else if(key.equalsIgnoreCase("alpha")) {
				alphas=map.get(key);
			}else if(key.equalsIgnoreCase("a1")) {
				a1s=map.get(key);
			}else if(key.equalsIgnoreCase("a2")) {
				a2s=map.get(key);
			}else if(key.equalsIgnoreCase("b1")) {
				b1s=map.get(key);
			}else if(key.equalsIgnoreCase("b2")) {
				b2s=map.get(key);
			}else if(key.equalsIgnoreCase("c1")) {
				c1s=map.get(key);
			}else if(key.equalsIgnoreCase("c2")) {
				c2s=map.get(key);
			}
		}
		
		for(int i=0;i<angles.size();i++) {
			PhasingParamModel model=new PhasingParamModel();
			model.setDetails(details);
			model.setAngle(angles.get(i));
			model.setAlpha(alphas.get(i));
			model.setA1(a1s.get(i));
			model.setA2(a2s.get(i));
			model.setB1(b1s.get(i));
			model.setB2(b2s.get(i));
			model.setC1(c1s.get(i));
			model.setC2(c2s.get(i));
			list.add(model);
		}
		paramRepo.saveAll(list);
	} 
	
	public List<PhasingParamModel> showPhasing(Integer pid){
		ProjectDetails details=detailRepo.findById(pid).orElse(null);
		return paramRepo.findBydetails(details);
	}
	
}
