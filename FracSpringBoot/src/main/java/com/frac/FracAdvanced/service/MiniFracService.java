package com.frac.FracAdvanced.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.MiniFracModel;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.repository.MiniFracRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class MiniFracService {
	
	@Autowired
	MiniFracRepo fracRepo;
	@Autowired
	ProjectDetailRepo detailRepo;
	
	public void savefield(Integer pid, List<String> value) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		MiniFracModel fracModel=new MiniFracModel();
		fracModel.setPumptime(value.get(0));
		fracModel.setPressure(value.get(1));
		fracModel.setTime(value.get(2));
		fracModel.setDetails(detail);
		fracRepo.save(fracModel);
	}
	
	public List<MiniFracModel> showfields(Integer pid) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		return fracRepo.findBydetails(detail);
	}
}
