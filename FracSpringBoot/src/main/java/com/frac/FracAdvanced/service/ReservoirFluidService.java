/**
 * 
 */
package com.frac.FracAdvanced.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ReservoirFluidModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.ReservoirFluidRepo;

@Service
public class ReservoirFluidService {

	@Autowired
	ReservoirFluidRepo fluidRepo;
	@Autowired
	ProjectDetailRepo detailRepo;

	public java.util.List<ReservoirFluidModel> getListMethod(int pid, String ftype, String radioValue)
			throws Exception {
		ArrayList<ReservoirFluidModel> a1 = new ArrayList<ReservoirFluidModel>();

		java.util.List<ReservoirFluidModel> x12 = fluidRepo.findByFluidtypeAndDetails(ftype, detailRepo.getOne(pid));
		return x12;
		/*
		 * if(ftype.equalsIgnoreCase("Optimum Fracture Design Input")) {
		 * java.util.List<ReservoirFluidModel>
		 * x12=fluidRepo.findByFluidtypeAndDetails(ftype, detailRepo.getOne(pid));
		 * 
		 * for(int i=0; i<x12.size();i++) {
		 * if(radioValue.equals(x12.get(i).getWellType())) {a1.add(x12.get(i));}} return
		 * a1; } else { java.util.List<ReservoirFluidModel>
		 * x12=fluidRepo.findByFluidtypeAndDetails(ftype, detailRepo.getOne(pid));
		 * return x12; }
		 */
	}

	public java.util.List<ReservoirFluidModel> methodEditValue(int pid, String ftype, java.util.List<String> value)
			throws Exception {

		// java.util.List<ReservoirFluidModel>
		// x12=fluidRepo.findByFluidTypeAndWellTypeAndDetails(ftype, wellType,
		// detailRepo.getOne(pid));

		java.util.List<ReservoirFluidModel> x12 = fluidRepo.findByFluidtypeAndDetails(ftype, detailRepo.getOne(pid));
		for (int i = 0; i < x12.size(); i++) {
			x12.get(i).setValue(value.get(i));
			fluidRepo.save(x12.get(i));
		}
		java.util.List<ReservoirFluidModel> x123 = fluidRepo.findByFluidtypeAndDetails(ftype, detailRepo.getOne(pid));
		return x123;
	}

	public void methodEditWellTypeSelected(int pid, String wellTypeSelected, String ftype) throws Exception {

		ProjectDetails p1 = detailRepo.getOne(pid);
		List<ReservoirFluidModel> all = fluidRepo.findBydetails(p1);
		for (int i = 0; i < all.size(); i++) {
			all.get(i).setWellTypeSelected(wellTypeSelected);
			fluidRepo.save(all.get(i));
		}
	}
}
