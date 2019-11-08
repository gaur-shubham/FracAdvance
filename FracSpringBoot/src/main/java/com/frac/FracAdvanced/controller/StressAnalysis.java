/**
 * 
 */
package com.frac.FracAdvanced.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.frac.FracAdvanced.service.StressAnalysisService;

/**
 * @author ShubhamGaur
 *
 */

@Controller
@RequestMapping("/stressanalysis")
public class StressAnalysis {

	String map="view/stressanalysis";
		@Autowired
		StressAnalysisService analysisservice;
	
		@RequestMapping("/showstress")
		public String show(Model model,@RequestParam("pid") Integer pid) {
			if(analysisservice.showList(pid).size()==0) {
			model.addAttribute("pid", pid);
			return map+"/import";
			}else {
				model.addAttribute("pid", pid);
				model.addAttribute("list", analysisservice.showList(pid));
				return map+"/showlist";
			}
		}
		
		@RequestMapping("/savestressfield")
		public String saveStressField(Model model,@RequestParam("sa_impinput") List<String> input,
				@RequestParam("sa_imppid") Integer pid) {
			analysisservice.saveField(pid, input);
			model.addAttribute("pid",pid);
			model.addAttribute("list", analysisservice.showList(pid));
			return map+"/import";
		}
		
		@RequestMapping("/delstressfield")
		public String delStressField(Model model,@RequestParam("pid") Integer pid,@RequestParam("id") Integer id) {
			analysisservice.deleteField(pid, id);
			model.addAttribute("pid",pid);
			model.addAttribute("list", analysisservice.showList(pid));
			return map+"/import";
		}
		
		@RequestMapping("/showupdate")
		public String showUpdate(Model model,@RequestParam("pid") Integer pid) {
			model.addAttribute("pid", pid);
			model.addAttribute("list", analysisservice.showList(pid));
			return map+"/update";
		}
		
		@RequestMapping("/saveupdate")
		public String saveUpdate(Model model,@RequestParam("sa_upinput") List<String> input,
				@RequestParam("sa_uppid") Integer pid,@RequestParam("sa_uphiddenid") List<Integer> id) {
			analysisservice.saveUpdate(pid, id, input);
			model.addAttribute("pid", pid);
			model.addAttribute("list", analysisservice.showList(pid));
			return map+"/showlist";
		}
		
		@RequestMapping("/saveanalysis")
		public String saveAnalysis(Model model,@RequestParam("sa_impinput") List<String> input,
				@RequestParam("sa_imppid") Integer pid) {
			analysisservice.saveField(pid, input);
			model.addAttribute("pid", pid);
			model.addAttribute("list", analysisservice.showList(pid));
			return map+"/showlist";
		}
		
		@RequestMapping("/delstressupfield")
		public String delStressUpField(Model model,@RequestParam("pid") Integer pid,@RequestParam("id") Integer id) {
			analysisservice.deleteField(pid, id);
			model.addAttribute("pid",pid);
			model.addAttribute("list", analysisservice.showList(pid));
			return map+"/update";
		}
		
		@RequestMapping(value = "/uploadfile",method = RequestMethod.POST)
		public String uploadFile(Model model, @RequestParam("sa_impfile") MultipartFile file,
				@RequestParam("pid") Integer pid) throws Exception {
			analysisservice.readFile(pid, file);
			model.addAttribute("pid", pid);
			model.addAttribute("list", analysisservice.showList(pid));
			return map+"/showlist";
		}
}
