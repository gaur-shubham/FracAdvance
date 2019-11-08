package com.frac.FracAdvanced.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ReportParamModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.ReportParamRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class ReportParamService {

	@Autowired
	private ProjectDetailRepo detailRepo;
	@Autowired
	private ReportParamRepo paramRepo;

	public void saveReportParam(Integer pid, Map<String, String> map) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ReportParamModel> list1 = paramRepo.findBydetails(details);
		List<ReportParamModel> list = new ArrayList<>();
		ReportParamModel model = new ReportParamModel();
		int i = 0;
		for (String key : map.keySet()) {
			if (list1.size() > 0) {
				model = list1.get(i);
				i++;
			} else {
				model = new ReportParamModel();
			}
			String value = map.get(key);
			model.setDetails(details);
			model.setParam(key);
			model.setValue(value);
			list.add(model);
		}
		paramRepo.saveAll(list);
	}

	public void saveReportParam1(Integer pid, Map<String, String> map) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ReportParamModel> list1 = paramRepo.findBydetails(details);
		List<ReportParamModel> list = new ArrayList<>();
		ReportParamModel model = new ReportParamModel();
		int i = 0;
		for (String key : map.keySet()) {
			if (list1.size() > 0 && list1.size() > 12) {
				model = list1.get(i + 12);
				i++;
			} else {
				model = new ReportParamModel();
			}
			String value = map.get(key);
			model.setDetails(details);
			model.setParam(key);
			model.setValue(value);
			list.add(model);
		}
		paramRepo.saveAll(list);
	}
	
	public void saveReportParam2(Integer pid, Map<String, String> map) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ReportParamModel> list1 = paramRepo.findBydetails(details);
		List<ReportParamModel> list = new ArrayList<>();
		ReportParamModel model = new ReportParamModel();
		int i = 0;
		for (String key : map.keySet()) {
			if (list1.size() > 0 && list1.size() > 14) {
				model = list1.get(i + 14);
				i++;
			} else {
				model = new ReportParamModel();
			}
			String value = map.get(key);
			model.setDetails(details);
			model.setParam(key);
			model.setValue(value);
			list.add(model);
		}
		paramRepo.saveAll(list);
	}
	
	public void saveReportParam3(Integer pid, Map<String, String> map) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ReportParamModel> list1 = paramRepo.findBydetails(details);
		List<ReportParamModel> list = new ArrayList<>();
		ReportParamModel model = new ReportParamModel();
		int i = 0;
		for (String key : map.keySet()) {
			if (list1.size() > 0 && list1.size() > 18) {
				model = list1.get(i + 18);
				i++;
			} else {
				model = new ReportParamModel();
			}
			String value = map.get(key);
			model.setDetails(details);
			model.setParam(key);
			model.setValue(value);
			list.add(model);
		}
		paramRepo.saveAll(list);
	}
	
	public boolean simulationDone(Integer pid) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ReportParamModel> list = paramRepo.findBydetails(details);
		if(list.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
}
