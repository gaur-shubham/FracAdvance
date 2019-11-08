/**
 * 
 */
package com.frac.FracAdvanced.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frac.FracAdvanced.model.OutputStressModel;
import com.frac.FracAdvanced.model.WellForcastDeclinedCurveModel;
import com.frac.FracAdvanced.service.OutputWellForcastService;
import com.frac.FracAdvanced.service.WellForcastDeclinedCurverService;
import com.frac.FracAdvanced.service.WellForcastService;

/**
 * @author ShubhamGaur
 *
 */
@Controller
@RequestMapping("/wellforcast")
public class WellForcast {

	String map="view/wellforcast";
	
	@Autowired
	private WellForcastService forcastservice;
	@Autowired
	private OutputWellForcastService outforcastservice;
	@Autowired
	private WellForcastDeclinedCurverService curveservice;
	
	@RequestMapping("showforcast")
	public String show(Model model,@RequestParam("pid") Integer pid) {
		model.addAttribute("pid",pid);
		model.addAttribute("list", forcastservice.showList(pid));
		return map+"/showlist";
	}
	
	@RequestMapping("/editforcast")
	public String saveForcast(Model model,@RequestParam("imppid") Integer pid) {
		model.addAttribute("list", forcastservice.showList(pid));
		model.addAttribute("pid",pid);
		model.addAttribute("type",forcastservice.showList(pid).get(0).getValue());
		return map+"/edit";
	}
	
	@RequestMapping("/saveforcast")
	public String saveforcast(Model model,@RequestParam("imppid") Integer pid,@RequestParam("wf_input") List<String> input) {
		forcastservice.saveForcast(pid, input);
		Map<String,Double> map1=forcastservice.saveCalculation(pid);
		outforcastservice.saveOutforcast(pid, map1);
		model.addAttribute("pid",pid);
		model.addAttribute("list", forcastservice.showList(pid));
		return map+"/showlist";
	}
	
	@RequestMapping("/showcurveanalysis")
	public String showCurveAnalysis(Model model,@RequestParam("pid") Integer pid) {
		model.addAttribute("pid",pid);
		model.addAttribute("qafter", outforcastservice.showList(pid).get(1).getValue());
		model.addAttribute("list", curveservice.showList1(pid, "Exponential"));
		model.addAttribute("list1", curveservice.showlist3(pid));
		return map+"/curveanalysis";
	}
	
	@RequestMapping("/showcurveinput")
	public String showCurveInput(Model model,@RequestParam("type") String type,@RequestParam("pid") Integer pid) {
		model.addAttribute("list", curveservice.showList1(pid, type));
		model.addAttribute("pid",pid);
		model.addAttribute("type",type);
		return map+"/curveanalysis";
	}
	
	@RequestMapping("/savefield")
	public String saveField(Model model,@RequestParam("wf_input2") String time,@RequestParam("pid") Integer pid,
			@RequestParam("wf_type") String type) {
		curveservice.saveField(pid, time,type);
		model.addAttribute("list", curveservice.showList2(pid,type));
		model.addAttribute("list1", curveservice.showlist3(pid));
		model.addAttribute("pid",pid);
		model.addAttribute("type",type);
		
		return map+"/curveanalysis";
	}
	
	@RequestMapping("/save1")
	public String test(Model model,@RequestParam("wf_input1") List<String> input,@RequestParam("pid") Integer pid,
			@RequestParam("wf_type") String type) {
		curveservice.save1(pid, input);
		curveservice.saveBm(pid);
		model.addAttribute("list", curveservice.showList2(pid,type));
		model.addAttribute("pid",pid);
		model.addAttribute("type",type);
		model.addAttribute("list1", curveservice.showlist3(pid));
		return map+"/curveanalysis";
	}
	
	@RequestMapping("/showdeclinecurve")
	@ResponseBody
	public List<WellForcastDeclinedCurveModel> showDeclineCurve(Model model,@RequestParam("pid") Integer pid) {
		model.addAttribute("list", curveservice.showlist3(pid));
		return curveservice.showlist3(pid);
	}
}
