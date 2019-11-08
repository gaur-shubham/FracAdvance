package com.frac.FracAdvanced.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.frac.FracAdvanced.model.InjectedFluidModel;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.repository.InjectedFluidRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;

/**
 * @author ShubhamGaur
 *
 */
@Controller
@RequestMapping(value = "injectedfluid")
public class InjectedFluid {

	String map = "view/injectedFluid";

	@Autowired
	private ProjectDetailRepo prodetails;

	@Autowired
	private InjectedFluidRepo repo;

	@RequestMapping("/showfluid")
	public String show(@RequestParam("pro_Id") Integer pid, Model model) {
		ProjectDetails details = prodetails.findById(pid).orElse(null);
		List<InjectedFluidModel> list = repo.findBydetails(details);
		if (list.isEmpty()) {
			model.addAttribute("pid", pid);
			return map + "/import";
		} else {
			model.addAttribute("list", list);
			model.addAttribute("pid", pid);
			return map + "/showlist";
		}
	}
	
	@RequestMapping("/savefluid")
	public String save(Model model,@RequestParam("imppid") Integer pid,@RequestParam("imprate") String rate,
			@RequestParam("impcoeff") String coeff,@RequestParam("imploss") String loss,
			@RequestParam("impskin") String skin,@RequestParam("imppi") String pi,
			@RequestParam("impmargin") String margin,@RequestParam("impstress") String stress) {
		List<InjectedFluidModel> list=new ArrayList<>();
		InjectedFluidModel fluidModel=new InjectedFluidModel();
		fluidModel.setCoeff(coeff);
		fluidModel.setLoss(loss);
		fluidModel.setMargin(margin);
		fluidModel.setPi(pi);
		fluidModel.setRate(rate);
		fluidModel.setSkin(skin);
		fluidModel.setStress(stress);
		Optional<ProjectDetails> detail= prodetails.findById(pid);
		fluidModel.setDetails(detail.get());
		list.add(fluidModel);
		repo.saveAll(list);
		
		ProjectDetails details=prodetails.findById(pid).orElse(null);
		List<InjectedFluidModel> list1=repo.findBydetails(details);
		model.addAttribute("list", list1);
		model.addAttribute("pid", pid);
		return map+"/showlist";
	}
	
	@RequestMapping("/updatefluid")
	public String update(Model model,@RequestParam("pid") Integer pid) {
		ProjectDetails details=prodetails.findById(pid).orElse(null);
		List<InjectedFluidModel> list=repo.findBydetails(details);
		model.addAttribute("list", list);
		model.addAttribute("pid", pid);
		return map+"/update";
	}
	
	@RequestMapping("/updatesavefluid")
	public String updatesave(Model model,@RequestParam("updpid") Integer pid,@RequestParam("updrate") String rate,
			@RequestParam("updcoeff") String coeff,@RequestParam("updloss") String loss,
			@RequestParam("updskin") String skin,@RequestParam("updpi") String pi,
			@RequestParam("updmargin") String margin,@RequestParam("updstress") String stress,
			@RequestParam("updid") Integer id) {
		InjectedFluidModel fluidModel= repo.getOne(id);
		fluidModel.setCoeff(coeff);
		fluidModel.setLoss(loss);
		fluidModel.setMargin(margin);
		fluidModel.setPi(pi);
		fluidModel.setRate(rate);
		fluidModel.setSkin(skin);
		fluidModel.setStress(stress);
		
		repo.save(fluidModel);
		
		ProjectDetails details=prodetails.findById(pid).orElse(null);
		List<InjectedFluidModel> list1=repo.findBydetails(details);
		model.addAttribute("list", list1);
		model.addAttribute("pid", pid);
		return map+"/showlist";
	}
}
