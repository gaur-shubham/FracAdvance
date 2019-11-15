package com.frac.FracAdvanced.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.frac.FracAdvanced.Method.CreateDefaultData;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.service.PhasingParamService;

/**
 * @author ShubhamGaur
 *
 */
@Controller
public class Application {
	
	@Autowired
	ProjectDetailRepo repo;
	@Autowired
	CreateDefaultData defaultdata;
	@Autowired
	PhasingParamService phasingservice; 
	
	@RequestMapping("/login")
	public String login(Model model) {
		return "view/login_logout/login";
	}
	
	@RequestMapping("/loginFail")
	public String loginFail(Model model) {
		model.addAttribute("fail","true");
		return "view/login_logout/login";
	}
	@RequestMapping("/logoutController")
	public String logout(Model model) {
		return "view/login_logout/logout";
	}
	
	@RequestMapping("/signupController")
	public String signup(Model model) {
		return "view/login_logout/Signup";
	}
		
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("list", repo.findAll());
		return "projectDetails/list";
	}
	
	@RequestMapping("/create")
	public String create() {
		return "projectDetails/create";
	}
	@RequestMapping("/showDetail")
	public String showProject(Model model) {
		ProjectDetails detail=new ProjectDetails();
		model.addAttribute("ProjectDetail", detail);
		return "projectDetails/projectDetail";
	}
	
	@RequestMapping("/accessNotAllowed")
	public String noAccess(Model model)
	{
		return "/view/login_logout/AccessDenied";
	}
	
	@RequestMapping("/detail")
	public String saveProject(Model model,@RequestParam Map<String,String> requestparams,
			RedirectAttributes attributes)throws Exception {
		ProjectDetails detail=new ProjectDetails();
		detail.setProjectName(requestparams.get("projectName"));
		detail.setWellName(requestparams.get("wellName"));
		detail.setCompanyName(requestparams.get("companyName"));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		detail.setDateCreated(dtf.format(now).toString()+" IST");
		repo.save(detail);
		defaultdata.setDefault(detail.getId());
		defaultdata.saveWellData(detail.getId());
		defaultdata.saveFluidLibraryDefault(detail.getId()); 
		defaultdata.saveReservoirFluidDefault(detail.getId());
		phasingservice.savePhasingDefault(detail.getId());
		attributes.addFlashAttribute("ProjectDetail", detail);
		return "redirect:/list";
	}
	
	@RequestMapping("/showlist/{pid}")
	public String show(@PathVariable("pid") Integer pid,RedirectAttributes attributes) {
		attributes.addFlashAttribute("ProjectDetail", repo.findById(pid));
		return "redirect:/list";
	}
	
	@RequestMapping("/deleteproject/{pid}")
	public String delete(@PathVariable("pid") Integer pid) {
		repo.deleteById(pid);
		repo.flush();
		return "redirect:/";
	}
	
	@RequestMapping("/projdetailPrevbt")
	public String prevButton(){
		return "redirect:/";
	}
}