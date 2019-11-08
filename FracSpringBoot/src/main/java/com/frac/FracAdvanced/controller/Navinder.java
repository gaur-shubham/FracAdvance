package com.frac.FracAdvanced.controller;


import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.frac.FracAdvanced.model.ProjectDetails;
import org.springframework.beans.factory.annotation.Autowired;
import com.frac.FracAdvanced.repository.MiniFracRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.service.ReportParamService;

/**
 * @author ShubhamGaur
 *
 */
@Controller
@SessionAttributes(value = {"ProjectDetail"})
public class Navinder {
	
	@Autowired
	MiniFracRepo service;
	@Autowired
	ReportParamService reportparamservice; 
	@Autowired
	ProjectDetailRepo prodetails;
	
	@RequestMapping("/list")
	public String show(@ModelAttribute("ProjectDetail") ProjectDetails details,HttpSession session,Model model){
		session.setAttribute("ProjectDetail", details);
		model.addAttribute("doneSim", reportparamservice.simulationDone(details.getId()));
		return "projectDetails/projectDetail";
	}
	
	@RequestMapping("/showgraphs")
	public String graphView(@ModelAttribute("ProjectDetail") ProjectDetails details,HttpSession session) {
		session.setAttribute("ProjectDetail", details);
		return "view/showgraph";
	}
	
	
}
