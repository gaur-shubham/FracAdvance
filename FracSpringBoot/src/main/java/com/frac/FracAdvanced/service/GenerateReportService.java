package com.frac.FracAdvanced.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ReportParamModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.ReportParamRepo;
/**
 * @author ShubhamGaur
 *
 */
@Component
public class GenerateReportService {

		@Autowired
		ProjectDetailRepo detailRepo;
		@Autowired
		ReportParamRepo paramRepo;
		
		public Map<String,String> reportProjectData(Integer pid) {
			Map<String,String> map=new LinkedHashMap<>();
			ProjectDetails details=detailRepo.findById(pid).orElse(null);
			map.put("Project Name",details.getProjectName());
			map.put("Well Name",details.getWellName());
			map.put("Company Name",details.getCompanyName());
			map.put("Date Created",details.getDateCreated());
		return map;
		}
		
	public Map<String,String> fracGeomOutData(Integer pid) {
		Map<String,String> map=new LinkedHashMap<>();
		ProjectDetails details=detailRepo.findById(pid).orElse(null);
		List<ReportParamModel> list1=paramRepo.findBydetails(details);
		for(int i=0;i<list1.size();i++) {
			if (list1.get(i).getParam().equalsIgnoreCase("Max Created Fracture Length(ft)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Average Fracture Width(Inch)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Propped Width(Inch)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Total Injection Volume(bbl)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Total Pad Volume(bbl)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Total Gel Volume(bbl)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Total Cross Link Volume(bbl)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Max Proppant Concentration(ppg)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Net Pressure At End Of Pumping(psi)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Perforation Skin")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Perforation Pressure Drop(psi)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			}
		}
	return map;
	}
	
	public Map<String,String> optimumDesignOutData(Integer pid) {
		Map<String,String> map=new LinkedHashMap<>();
		ProjectDetails details=detailRepo.findById(pid).orElse(null);
		List<ReportParamModel> list1=paramRepo.findBydetails(details);
		for(int i=0;i<list1.size();i++) {
			if (list1.get(i).getParam().equalsIgnoreCase("Effective Half Length(ft)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Average Width(Inch)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Propped Length(ft)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Fracture Area(ft2)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Effective Fracture Height(ft)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Volume Of Fluid Loss(bbl)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Average Viscosity(cp)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			} else if (list1.get(i).getParam().equalsIgnoreCase("Fracture Efficiency(%)")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			}else if (list1.get(i).getParam().equalsIgnoreCase("Fracture Width Error %")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			}else if (list1.get(i).getParam().equalsIgnoreCase("Fracture Height Error %")) {
				map.put(list1.get(i).getParam(), list1.get(i).getValue());
			}
		}
	return map;
	}
	
}
