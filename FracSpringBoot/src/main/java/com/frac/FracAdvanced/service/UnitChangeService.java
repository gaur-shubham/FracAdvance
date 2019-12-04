/**
 * 
 */
package com.frac.FracAdvanced.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.StressAnalysisModel;
import com.frac.FracAdvanced.repository.FluidLibraryRepo;
import com.frac.FracAdvanced.repository.InjectedFluid1Repo;
import com.frac.FracAdvanced.repository.InjectionPlanRepo;
import com.frac.FracAdvanced.repository.MiniFracRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.ProppantRepo;
import com.frac.FracAdvanced.repository.ReservoirFluidRepo;
import com.frac.FracAdvanced.repository.ReservoirLithologyRepo;
import com.frac.FracAdvanced.repository.StressAnalysisRepo;
import com.frac.FracAdvanced.repository.WellDataRepo;
import com.frac.FracAdvanced.repository.WellForcastRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class UnitChangeService {
	
	@Autowired
	private ProjectDetailRepo detailRepo;
	@Autowired
	private InjectedFluid1Repo injectedFluidRepo;
	@Autowired
	private MiniFracRepo fracRepo;
	@Autowired
	private ReservoirFluidRepo fluidRepo;
	@Autowired
	private WellDataRepo wellDataRepo;
	@Autowired
	private StressAnalysisRepo stressAnalysisRepo;
	@Autowired
	private ReservoirLithologyRepo lithologyRepo;
	@Autowired
	private InjectionPlanRepo injectionPlanRepo;
	@Autowired
	private FluidLibraryRepo fluidLibraryRepo;
	@Autowired
	private ProppantRepo proppantRepo;
	@Autowired
	private WellForcastRepo forcastRepo;
	
	public void convertDataUnit(Integer pid, String unit) {
		Double density=0.0,depth=0.0,porePress=0.0;
		
		if(unit.equalsIgnoreCase("metric")) {
			density=16.0185;
			depth=0.3048;
			porePress=0.00689476;
		}else if(unit.equalsIgnoreCase("field")) {
			density=0.062428;
			depth=3.28084;
			porePress=145.038;
		}
		System.out.println("service>> "+pid);
		System.out.println(">> "+unit);
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<StressAnalysisModel> stressList = stressAnalysisRepo.findBydetails(detail);
		List<StressAnalysisModel> tempStressList = stressAnalysisRepo.findBydetails(detail);
		for(int i=0;i<stressList.size();i++) {
			Double den=0.0,dep=0.0,pore=0.0;
			StressAnalysisModel analysisModel=stressList.get(i);
			den=Double.parseDouble(analysisModel.getDensity())*density;
			dep=Double.parseDouble(analysisModel.getDepth())*depth;
			pore=Double.parseDouble(analysisModel.getPore())*porePress;
			analysisModel.setDensity(den.toString());
			analysisModel.setDepth(dep.toString());
			analysisModel.setPore(pore.toString());
			tempStressList.add(analysisModel);
		}
		stressAnalysisRepo.saveAll(tempStressList);
	}
}
