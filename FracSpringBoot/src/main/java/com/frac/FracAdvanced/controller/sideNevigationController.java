package com.frac.FracAdvanced.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class sideNevigationController {
	
	
	@RequestMapping("/wellLogformatController")
	public String wellLogformat() {
		
		return "view/sideNavigation/inputformat/wellLogFormat.html";
		
	}

	
	
	@RequestMapping("/userManualController")
	public String userManual() {
		
		return "view/sideNavigation/manuals/userManual.html";
		
	}
	
	
	
	
	@RequestMapping("/caseStudyController")
	public String caseStudyController() {
		
		return "view/sideNavigation/caseStudy/caseStudyView.html";
		
	}
	
	@RequestMapping("/helpController")
	public String helpController() {
		
		return "view/sideNavigation/help/helpView.html";
		
	}
}
