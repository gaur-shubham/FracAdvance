/**
 * 
 */
package com.frac.FracAdvanced.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.frac.FracAdvanced.service.ProppantService;

/**
 * @author ShubhamGaur
 *
 */
@Controller
@RequestMapping("/proppant")
public class Proppant {

	String map = "view/proppant";
	@Autowired
	ProppantService proppantservice;

	@RequestMapping("/showproppant")
	public String show(Model model, @RequestParam("pid") Integer pid) {
		model.addAttribute("pid", pid);
		model.addAttribute("list", proppantservice.showList(pid));
		model.addAttribute("ptype", proppantservice.showList(pid).get(0).getParam());
		model.addAttribute("wellType", proppantservice.getWellTypeFromReservoirFluidMethod(pid));
		model.addAttribute("darcyList", proppantservice.showDarcy(pid));
		model.addAttribute("eqtype", proppantservice.showEquation(pid));
		model.addAttribute("darcyEqList", proppantservice.darcyEqList());
		return map + "/import";
	}

	@RequestMapping("/setproppant")
	public String setProppant(RedirectAttributes redirectattribute, Model model, @RequestParam("val") String type,
			@RequestParam("pid") Integer pid) throws Exception {
		proppantservice.setProppant(pid, type);
		redirectattribute.addFlashAttribute("pid", pid);
		return "redirect:/showproppant";
	}

	@RequestMapping("/editproppantdata")
	public String editProppantData(Model model, @RequestParam("pid") Integer pid) {
		model.addAttribute("pid", pid);
		model.addAttribute("list", proppantservice.showList(pid));
		model.addAttribute("type", proppantservice.showList(pid).get(0).getParam());
		return map + "/edit";
	}

	@RequestMapping("/changeproppant")
	public String changeProppant(Model model, @RequestParam("val") String type, @RequestParam("pid") Integer pid)
			throws Exception {
		proppantservice.setProppant(pid, type);
		model.addAttribute("pid", pid);
		model.addAttribute("list", proppantservice.showList(pid));
		model.addAttribute("type", proppantservice.showList(pid).get(0).getParam());
		return map + "/edit";
	}

	@RequestMapping("/saveproppantdata")
	public String saveProppantData(Model model, @RequestParam("pp_editinput") List<String> input,
			@RequestParam("pp_editpid") Integer pid) {
		proppantservice.EditProppData(pid, input);
		model.addAttribute("pid", pid);
		model.addAttribute("list", proppantservice.showList(pid));
		model.addAttribute("type", proppantservice.showList(pid).get(0).getParam());
		return map + "/import";
	}

	@RequestMapping("/darcytable")
	public String darcyTable(Model model, @RequestParam("pid") Integer pid) {
		model.addAttribute("darcyEqList", proppantservice.darcyEqList());
		model.addAttribute("pid", pid);
		model.addAttribute("list", proppantservice.showList(pid));
		model.addAttribute("ptype", proppantservice.showList(pid).get(0).getParam());
		model.addAttribute("wellType", proppantservice.getWellTypeFromReservoirFluidMethod(pid));
		model.addAttribute("darcyList", proppantservice.showDarcy(pid));
		model.addAttribute("eqtype", proppantservice.showEquation(pid));

		return map + "/import";
	}

	@RequestMapping("/darcycalc")
	public String darcyCalc(Model model, @RequestParam("pid") Integer pid, @RequestParam("equation") String equation,
			RedirectAttributes attributes) {
		proppantservice.savekfAndBeta(pid, equation);
		model.addAttribute("darcyEqList", proppantservice.darcyEqList());
		model.addAttribute("pid", pid);
		model.addAttribute("list", proppantservice.showList(pid));
		model.addAttribute("ptype", proppantservice.showList(pid).get(0).getParam());
		model.addAttribute("wellType", proppantservice.getWellTypeFromReservoirFluidMethod(pid));
		model.addAttribute("darcyList", proppantservice.showDarcy(pid));
		model.addAttribute("eqtype", equation);
		return map + "/import";
	}
}
