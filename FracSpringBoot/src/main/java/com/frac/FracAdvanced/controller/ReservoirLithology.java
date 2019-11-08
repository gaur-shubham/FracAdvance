package com.frac.FracAdvanced.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.frac.FracAdvanced.service.ReservoirLithologyService;

/**
 * @author ShubhamGaur
 *
 */
@Controller
@RequestMapping("/reservoirLithology")
public class ReservoirLithology {
	String map="view/reservoirLithology";
	
	@Autowired
	ReservoirLithologyService lithologyservice;
	
	@RequestMapping("/showLithology")
	public String show(Model model,@RequestParam("pid") Integer pid) {
		if(lithologyservice.showList(pid).isEmpty()) {
			model.addAttribute("pid", pid);
			return map+"/import";
		}else {
			model.addAttribute("pid", pid);
			model.addAttribute("list", lithologyservice.showList(pid));
			return map+"/showlist";
		}
	}
	
	@RequestMapping("/rows")
	public String rows(Model model,@RequestParam("pid") Integer pid,@RequestParam("no") Integer number) {
		model.addAttribute("pid", pid);
		model.addAttribute("number", lithologyservice.showRows(number).get(lithologyservice.showRows(number).size()-1));
		model.addAttribute("list", lithologyservice.showRows(number));
		return map+"/import";
	}
	
	@RequestMapping("/savelithology")
	public String saveLithology(Model model,@RequestParam("rl_imppid") Integer pid,
			@RequestParam("rl_input") List<String> input) {
		lithologyservice.saveLithology(pid, input);
		model.addAttribute("pid", pid);
		model.addAttribute("list", lithologyservice.showList(pid));
		return map+"/showlist";
	}
	
	@RequestMapping("/showedit")
	public String showEdit(Model model,@RequestParam("pid") Integer pid) {
		model.addAttribute("pid", pid);
		model.addAttribute("list", lithologyservice.showList(pid));
		return map+"/edit";
	}
	
	@RequestMapping("saveupdate")
	public String saveUpdate(Model model,@RequestParam("pid") Integer pid,@RequestParam("rl_input") List<String> input) {
		lithologyservice.saveEdit(pid, input);
		model.addAttribute("pid", pid);
		model.addAttribute("list", lithologyservice.showList(pid));
		return map+"/showlist";
	}
	
	
}
