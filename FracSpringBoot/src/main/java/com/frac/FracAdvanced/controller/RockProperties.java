package com.frac.FracAdvanced.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.RockPropertiesRepo;
import com.frac.FracAdvanced.service.RockPropertiesService;

/**
 * @author ShubhamGaur
 *
 */
@Controller
@RequestMapping("/rockProperties")
public class RockProperties {
	
	String map="view/rockproperties";
	
	@Autowired 
	ProjectDetailRepo detailrepo;
	@Autowired
	RockPropertiesService rockservice;
	@Autowired
	RockPropertiesRepo rockrepo;
	
	@RequestMapping("/showrock")
	public String show(Model model,@RequestParam("pid") Integer pid) {
		ProjectDetails details=detailrepo.findById(pid).orElse(null);
		if(rockrepo.findBydetails(details).get(0).getStage().equalsIgnoreCase("N/A")) {
		model.addAttribute("list", rockrepo.findBydetails(details));
		model.addAttribute("pid", pid);
		return map+"/import";
		}
		else {
			model.addAttribute("list", rockrepo.findByStageAndDetails("1", details));
			model.addAttribute("pid", pid);
			return map+"/showlist";
		}
		
	}
	
	@RequestMapping("/savenext")
	public String saveNext(Model model,@RequestParam("rp_imppid") Integer pid,
			@RequestParam("rp_impRock Embedment Strength(psi)") String rockstrength,
			@RequestParam("rp_impRock Density(lbm/ft3)") String density,
			@RequestParam("rp_impFracture Gradient(psi/ft)") String gradient,
			@RequestParam("rp_impYoung Modulus(psi)") String young,
			@RequestParam("rp_impShear Modulus(psi)") String shear,
			@RequestParam("rp_impTensile Strength(psi)") String tenstrength,
			@RequestParam("rp_impPoisson Ratio") String ratio,@RequestParam("rp_impstage") String stage) {
		
		List<String> value = Arrays.asList(stage,density,gradient,young,shear,ratio,rockstrength,tenstrength);
		rockservice.saveData(value, pid);
		ProjectDetails details=detailrepo.findById(pid).orElse(null);
		model.addAttribute("pid", pid);
		model.addAttribute("list", rockrepo.findByStageAndDetails(stage, details));
		Integer stage1=Integer.parseInt(stage)+1;
		model.addAttribute("stage", stage1.toString());
		return map+"/import";
	}
	
	@RequestMapping("/shownext")
	public String showNext(Model model,@RequestParam("rp_showpid") Integer pid,@RequestParam("rp_showstage") String stage) {
		ProjectDetails details=detailrepo.findById(pid).orElse(null);
		Integer x=Integer.valueOf(stage)+1;
		if(x<=(rockrepo.countBydetails(details)/7)) {
			model.addAttribute("list", rockrepo.findByStageAndDetails(x.toString(), details));
			model.addAttribute("pid", pid);
			return map+"/showlist";
		}else{
		model.addAttribute("list", rockrepo.findByStageAndDetails(stage, details));
		model.addAttribute("pid", pid);
		return map+"/showlist";
		}
	}
	@RequestMapping("/shownext1")
	public String showNext1(Model model,@RequestParam("rp_uppid") Integer pid,
			@RequestParam("rp_upstage") String stage,@RequestParam("rp_upinput") List<String> value,
			@RequestParam("rp_upinputid") List<Integer> id) {
		ProjectDetails details=detailrepo.findById(pid).orElse(null);
		value.add(0, stage);
		rockservice.updateData(pid, id, value);
		Integer x=Integer.valueOf(stage)+1;
		if(x<=(rockrepo.countBydetails(details)/7)) {
			model.addAttribute("list", rockrepo.findByStageAndDetails(x.toString(), details));
			model.addAttribute("pid", pid);
			return map+"/update";
		}else{
		model.addAttribute("list", rockrepo.findByStageAndDetails(stage, details));
		model.addAttribute("pid", pid);
		return map+"/update";
		}
	}
	
	@RequestMapping("/showprev")
	public String showPrev(Model model,@RequestParam("rp_showpid") Integer pid,@RequestParam("rp_showstage") String stage) {
		ProjectDetails details=detailrepo.findById(pid).orElse(null);
		Integer x=Integer.valueOf(stage);
		if(x>1&&x<=(rockrepo.countBydetails(details)/7)) {
			x--;
			model.addAttribute("list", rockrepo.findByStageAndDetails(x.toString(), details));
			model.addAttribute("pid", pid);
			return map+"/showlist";
		}else{
		model.addAttribute("list", rockrepo.findByStageAndDetails(stage, details));
		model.addAttribute("pid", pid);
		return map+"/showlist";
		}
	}
	
	@RequestMapping("/showprev1")
	public String showPrev1(Model model,@RequestParam("rp_uppid") Integer pid,@RequestParam("rp_upstage") String stage) {
		ProjectDetails details=detailrepo.findById(pid).orElse(null);
		Integer x=Integer.valueOf(stage);
		if(x>1&&x<=(rockrepo.countBydetails(details)/7)) {
			x--;
			model.addAttribute("list", rockrepo.findByStageAndDetails(x.toString(), details));
			model.addAttribute("pid", pid);
			return map+"/update";
		}else{
		model.addAttribute("list", rockrepo.findByStageAndDetails(stage, details));
		model.addAttribute("pid", pid);
		return map+"/update";
		}
	}
	
	@RequestMapping("/saverock")
	public String saveRock(Model model,@RequestParam("rp_imppid") Integer pid,
			@RequestParam("rp_impRock Embedment Strength(psi)") String rockstrength,
			@RequestParam("rp_impRock Density(lbm/ft3)") String density,
			@RequestParam("rp_impFracture Gradient(psi/ft)") String gradient,
			@RequestParam("rp_impYoung Modulus(psi)") String young,
			@RequestParam("rp_impShear Modulus(psi)") String shear,
			@RequestParam("rp_impTensile Strength(psi)") String tenstrength,
			@RequestParam("rp_impPoisson Ratio") String ratio,@RequestParam("rp_impstage") String stage) {
		List<String> value = Arrays.asList(stage,density,gradient,young,shear,ratio,rockstrength,tenstrength);
		rockservice.saveData(value, pid);
		ProjectDetails details=detailrepo.findById(pid).orElse(null);
		model.addAttribute("list", rockrepo.findByStageAndDetails("1", details));
		model.addAttribute("pid", pid);
		return map+"/showlist";
	}

	@RequestMapping("/showupdate")
	public String showUpdate(Model model,@RequestParam("pid") Integer pid) {
		ProjectDetails details=detailrepo.findById(pid).orElse(null);
		model.addAttribute("list", rockrepo.findByStageAndDetails("1", details));
		model.addAttribute("pid", pid);
		return map+"/update";
	}
	
	@RequestMapping("/saveupdate")
	public String saveUpdate(Model model,@RequestParam("rp_upinput") List<String> value,
			@RequestParam("rp_upstage") String stage,@RequestParam("rp_uppid") Integer pid,
			@RequestParam("rp_upinputid") List<Integer> id) {
		ProjectDetails details=detailrepo.findById(pid).orElse(null);
		value.add(0, stage);
		rockservice.updateData(pid, id, value);
		model.addAttribute("list", rockrepo.findByStageAndDetails("1", details));
		model.addAttribute("pid", pid);
		return map+"/showlist";
	}

}
