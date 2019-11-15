package com.frac.FracAdvanced.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.frac.FracAdvanced.model.MainFracGraphModel;
import com.frac.FracAdvanced.model.OutputMiniFrac;
import com.frac.FracAdvanced.model.OutputStressModel;
import com.frac.FracAdvanced.model.ReportMakingModel;
import com.frac.FracAdvanced.model.WellForcastDeclinedCurveModel;
import com.frac.FracAdvanced.repository.OutputMiniFracRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.service.GenerateReportService;
import com.frac.FracAdvanced.service.InjectionPlanService;
import com.frac.FracAdvanced.service.MainFracGraphService;
import com.frac.FracAdvanced.service.OutputStressService;
import com.frac.FracAdvanced.service.ReportMakingService;
import com.frac.FracAdvanced.service.WellForcastDeclinedCurverService;
/**
 * @author ShubhamGaur
 *
 */
@Controller
public class GraphController {
	
	@Autowired
	private OutputMiniFracRepo output;
	@Autowired
	private ProjectDetailRepo detailrepo; 
	@Autowired
	private OutputStressService stressservice;
	@Autowired
	private WellForcastDeclinedCurverService curveservice;
	@Autowired
	private MainFracGraphService fracGraphService;  
	@Autowired
	private GenerateReportService reportservice;
	@Autowired
	private ReportMakingService ReportService;
	@Autowired
	private InjectionPlanService injectionservice;
	
	@PostMapping("/graphs")
	public String graphs(@RequestParam("p_Id") Integer pid,RedirectAttributes attributes) {
		attributes.addFlashAttribute("ProjectDetail", detailrepo.findById(pid).orElse(null));
		
		return "redirect:/showgraphs";
	}
	@RequestMapping("/redirectgraphs/{pid}")
	public String redirectgraphs(@PathVariable("pid") Integer pid,RedirectAttributes attributes) {
		attributes.addFlashAttribute("ProjectDetail", detailrepo.findById(pid).orElse(null));
		return "redirect:/showgraphs";
	}
	
	
	@RequestMapping("/showMiniGraph/{pid}")
	@ResponseBody
	public List<OutputMiniFrac> showminiGraph(@PathVariable("pid") Integer pid) {
		return output.findByProId(pid);
	}
	
	@RequestMapping("/showStressGraph/{pid}")
	@ResponseBody
	public List<OutputStressModel> showstressGraph(@PathVariable("pid") Integer pid){
		return stressservice.showList(pid);
	}
	
	@RequestMapping("/showdeclinedcurve/{pid}")
	@ResponseBody
	public List<WellForcastDeclinedCurveModel> showDeclinedCurve(@PathVariable("pid") Integer pid){
		return curveservice.showlist3(pid);
	}
	@RequestMapping("/showmainfrac/{pid}")
	@ResponseBody
	public List<MainFracGraphModel> showMainFrac(@PathVariable("pid") Integer pid){
		return fracGraphService.showmainfrac(pid);
	}
	
	@RequestMapping("/showhalflength/{pid}")
	@ResponseBody
	public String showHalfLength(@PathVariable("pid") Integer pid) {
		return pid.toString();
	}
	
	@RequestMapping("/showGraphController/{pid}")
	@ResponseBody
	public List<ReportMakingModel> showConductivityGraph(@PathVariable("pid") Integer pid){		
		List<ReportMakingModel> charts=ReportService.getVlaueOfReport(pid);
		return charts;
	}
	
	@RequestMapping("/graphPrevbt/{pid}")
	public String show(@PathVariable("pid") Integer pid,RedirectAttributes attributes) {
		attributes.addFlashAttribute("ProjectDetail", detailrepo.findById(pid).orElse(null));
		return "redirect:/list";
	}
	
	@RequestMapping("/generatereport")
	public String generateReport(Model model,@RequestParam("pid") Integer pid) {
		model.addAttribute("map1", reportservice.reportProjectData(pid));
		model.addAttribute("map2", reportservice.fracGeomOutData(pid));
		model.addAttribute("map3", reportservice.optimumDesignOutData(pid));
		model.addAttribute("minifrac", output.findByProId(pid));
		model.addAttribute("stress",stressservice.showList(pid));
		model.addAttribute("decline",curveservice.showlist3(pid));
		model.addAttribute("mainfrac",fracGraphService.showmainfrac(pid));
		//model.addAttribute("testing",fracGraphService.test(pid));
		
		model.addAttribute("otheroutput",ReportService.getVlaueOfReport(pid));
		model.addAttribute("list", injectionservice.showInjectionPlan(pid));
		return "/view/reportview";
	}
}
