/**
 * 
 */
package com.frac.FracAdvanced.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.frac.FracAdvanced.model.InjectedFluid1Model;
import com.frac.FracAdvanced.model.InjectedFluidModel;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.repository.InjectedFluid1Repo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;

/**
 * @author ShubhamGaur
 *
 */

@Controller
@RequestMapping(value="injectedfluid1")
public class InjectedFluid1 {

	String map = "view/injectedFluid1";	
	
		@Autowired
		ProjectDetailRepo prodetails;
		@Autowired
		InjectedFluid1Repo repo;
	@RequestMapping("/showfluid1")
	public String show(@RequestParam("pro_Id") Integer pid, Model model) {
		ProjectDetails details = prodetails.findById(pid).orElse(null);
		List<InjectedFluid1Model> list = repo.findBydetails(details);
		if (list.get(0).getValue().equals("0.0")&&list.get(list.size()-1).getValue().equals("0.0")){
			model.addAttribute("list", list);
			model.addAttribute("pid", pid);
			return map + "/import";
		} else {
			model.addAttribute("list", list);
			model.addAttribute("pid", pid);
			return map + "/showlist";
		}
	}
	
	@RequestMapping("/savefluid1")
	public String save(Model model,@RequestParam("imppid") Integer pid,@RequestParam("if_impInjection Rate(bbl/min)") String rate,
			@RequestParam("if_impLeak Off Coefficient(ft/sec^1/2)") String coeff,@RequestParam("if_impSpurt Loss(gal/ft2)") String loss,
			@RequestParam("if_impSkin") String skin,@RequestParam("if_impPI(bbl/day-psi)") String pi,
			@RequestParam("if_impSafety Margin(200-500 psi)") String margin,@RequestParam("if_impTectonic Stress(psi)") String stress,
			@RequestParam("if_impid") List<Integer> id) {
		ProjectDetails details=prodetails.findById(pid).orElse(null);
		String val[]= {rate,coeff,loss,skin,pi,margin,stress};
		for(int i=0;i<id.size();i++) {
		InjectedFluid1Model fluidModel=repo.getOne(id.get(i));
		fluidModel.setValue(val[i]);
		repo.save(fluidModel);
		}
		List<InjectedFluid1Model> list1=repo.findBydetails(details);
		model.addAttribute("list", list1);
		model.addAttribute("pid", pid);
		return map+"/showlist";
	}
	
	@RequestMapping("/updatefluid1")
	public String update(Model model,@RequestParam("pid") Integer pid) {
		ProjectDetails details=prodetails.findById(pid).orElse(null);
		List<InjectedFluid1Model> list=repo.findBydetails(details);
		model.addAttribute("list", list);
		model.addAttribute("pid", pid);
		return map+"/update";
	}
	
	@RequestMapping("/saveupdatefluid1")
	public String saveupdate(Model model,@RequestParam("uppid") Integer pid,@RequestParam("if_upInjection Rate(bbl/min)") String rate,
			@RequestParam("if_upLeak Off Coefficient(ft/sec^1/2)") String coeff,@RequestParam("if_upSpurt Loss(gal/ft2)") String loss,
			@RequestParam("if_upSkin") String skin,@RequestParam("if_upPI(bbl/day-psi)") String pi,
			@RequestParam("if_upSafety Margin(200-500 psi)") String margin,@RequestParam("if_upTectonic Stress(psi)") String stress,
			@RequestParam("if_upid") List<Integer> id) {
		ProjectDetails details=prodetails.findById(pid).orElse(null);
		String val[]= {rate,coeff,loss,skin,pi,margin,stress};
		for(int i=0;i<id.size();i++) {
		InjectedFluid1Model fluidModel=repo.getOne(id.get(i));
		fluidModel.setValue(val[i]);
		repo.save(fluidModel);
		}
		List<InjectedFluid1Model> list1=repo.findBydetails(details);
		model.addAttribute("list", list1);
		model.addAttribute("pid", pid);
		return map+"/showlist";
	}
}
