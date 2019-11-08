/**
 * 
 */
package com.frac.FracAdvanced.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.StressAnalysisModel;
import com.frac.FracAdvanced.model.WellForcastModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.WellForcastRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class WellForcastService {
	
	@Autowired
	private WellForcastRepo forcastRepo;
	@Autowired
	private ProjectDetailRepo detailRepo;
	
	public List<WellForcastModel> showList(Integer pid){
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<WellForcastModel> forcasts = forcastRepo.findBydetails(detail);
	return forcasts;	
	}
	
	public void saveForcast(Integer pid,List<String> input) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<WellForcastModel> forcasts=forcastRepo.findBydetails(detail);
		List<WellForcastModel> list=new ArrayList<WellForcastModel>();
		for(int i=0;i<input.size();i++) {
			WellForcastModel model=forcasts.get(i);
			model.setValue(input.get(i));
			list.add(model);
		}
		forcastRepo.saveAll(list);
	}
	
	public Map<String,Double> saveCalculation(Integer pid) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<WellForcastModel> list=forcastRepo.findBydetails(detail);
		Map<String,Double> map=new LinkedHashMap<String, Double>();
		double kf=0.0,wf=0.0,xf=0.0,
				height=0.0,perm=0.0,
				respress=0.0,fbpress=0.0,
				effecrad=0.0,wellrad=0.0,
				skin=0.0,temp=0.0,
				formfactor=0.0,visco=0.0;
		double u=0.0,f=0.0,sf=0.0,qbefore=0.0,qafter=0.0;
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getParam().equalsIgnoreCase("kf")) {
				kf=Double.parseDouble(list.get(i).getValue());
			}else if(list.get(i).getParam().equalsIgnoreCase("wf")) {
				wf=Double.parseDouble(list.get(i).getValue());
			}else if(list.get(i).getParam().equalsIgnoreCase("xf")) {
				xf=Double.parseDouble(list.get(i).getValue());
			}else if(list.get(i).getParam().equalsIgnoreCase("Frac Height")) {
				height=Double.parseDouble(list.get(i).getValue());
			}else if(list.get(i).getParam().equalsIgnoreCase("Permiability")) {
				perm=Double.parseDouble(list.get(i).getValue());
			}else if(list.get(i).getParam().equalsIgnoreCase("Reservoir Pressure")) {
				respress=Double.parseDouble(list.get(i).getValue());
			}else if(list.get(i).getParam().equalsIgnoreCase("Flowing Bottomhole Pressure")) {
				fbpress=Double.parseDouble(list.get(i).getValue());
			}else if(list.get(i).getParam().equalsIgnoreCase("Effective Radius")) {
				effecrad=Double.parseDouble(list.get(i).getValue());
			}else if(list.get(i).getParam().equalsIgnoreCase("Wellbore Radius")) {
				wellrad=Double.parseDouble(list.get(i).getValue());
			}else if(list.get(i).getParam().equalsIgnoreCase("Skin")) {
				skin=Double.parseDouble(list.get(i).getValue());
			}else if(list.get(i).getParam().equalsIgnoreCase("Temperature")) {
				temp=Double.parseDouble(list.get(i).getValue());
			}else if(list.get(i).getParam().equalsIgnoreCase("Formation Factor")) {
				formfactor=Double.parseDouble(list.get(i).getValue());
			}else if(list.get(i).getParam().equalsIgnoreCase("viscosity")) {
				visco=Double.parseDouble(list.get(i).getValue());
			}
		}
		u=Math.log((kf*wf)/(perm*xf));
		f=(1.65-(0.328*u)+(0.1016*Math.pow(u, 2)))/(1+(0.18*u)+(0.064*Math.pow(u, 2))+(0.005*Math.pow(u, 3)));
		sf=(f-(Math.log(xf/wellrad)));
		qbefore=(perm*height*(respress-fbpress))/(141.2*formfactor*visco*((Math.log((0.472*effecrad)/wellrad)+skin)));
		qafter=(perm*height*(respress-fbpress))/(141.2*formfactor*visco*((Math.log((0.472*effecrad)/wellrad)+sf)));
		map.put("sf", sf);
		map.put("qafter", qafter);
		return map;
	}
}
