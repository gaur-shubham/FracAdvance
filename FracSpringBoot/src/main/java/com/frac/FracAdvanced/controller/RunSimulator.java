package com.frac.FracAdvanced.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpSession;
import com.frac.FracAdvanced.Method.MainFracGraph;
import com.frac.FracAdvanced.Method.MiniFracAlgo;
import com.frac.FracAdvanced.Method.ReportParameters;
import com.frac.FracAdvanced.Method.StressAnalysisAlgo;
import com.frac.FracAdvanced.Method.WriteReadFile;
import com.frac.FracAdvanced.repository.OutputMiniFracRepo;
import com.frac.FracAdvanced.service.ReportMakingService;
import com.frac.FracAdvanced.service.RunSimulatorService;

/**
 * @author ShubhamGaur
 *
 */

@Controller
public class RunSimulator {

	
	@Autowired
	private ReportMakingService reportms;
	@Autowired
	private RunSimulatorService simulatorService;
	private Integer in=0,out=0,cal=0;
	
	@RequestMapping("/simulate")
	public String simulate(@RequestParam("pId") Integer pid, Model model, HttpSession session,
			RedirectAttributes attributes) throws Exception {
		
		Map<String, String> result = reportms.method_fractureWidthAndConductivity(pid);
		if (result.size() == 0) {
			attributes.addFlashAttribute("pid", pid);
			return "redirect:/successSim";
		} else {
			model.addAttribute("ErrorList", result);
			Set<String> setList = new HashSet<String>();
			Set<Map.Entry<String, String>> st = result.entrySet();
			for (Map.Entry<String, String> me : st) {
				setList.add(me.getValue());
			}

			Enumeration<String> e = Collections.enumeration(setList);
			while (e.hasMoreElements()) {
				String zz = e.nextElement();
				if (zz == "Reservoir Fluid") {
					model.addAttribute("ReservoirFluid", "ReservoirFluid");
				}
				if (zz == "Injection Plan") {
					model.addAttribute("InjectionPlan", "InjectionPlan");
				}

				if (zz == "Poppant Properties") {
					model.addAttribute("PoppantProperties", "PoppantProperties");
				}

				if (zz == "Fluid Library") {
					model.addAttribute("FluidLibrary", "FluidLibrary");
				}
			}
			return "view/ErrorMassage";
		}
	}

	@RequestMapping("/successSim")
	@ResponseBody
	public String RunningBarController(@ModelAttribute("pid") Integer pid) throws Exception {
		if (in == 0) {
			in++;
			return simulatorService.createInput(pid);
		} else if (cal == 0) {
			cal++;
			return simulatorService.calcOutput(pid);
		} else if (out == 0) {
			out++;
			return simulatorService.saveOutput(pid);
		} else {
			cal=0;
			out=0;
			in=0;
			return "success";
		}
	}
	
}
