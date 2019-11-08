package com.frac.FracAdvanced.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.PumpingModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.PumpingRepo;
import com.frac.FracAdvanced.service.PumpingService;

/**
 * @author ShubhamGaur
 *
 */
@Controller
@RequestMapping(value = "/pumping")
public class Pumping {

	String map = "view/pumping";

	@Autowired
	PumpingRepo repo;
	@Autowired
	ProjectDetailRepo detailrepo;
	@Autowired
	PumpingService pumpservice;

	@RequestMapping("/showpump")
	public String show(Model model, @RequestParam("pid") Integer pid) {
		ProjectDetails details = detailrepo.findById(pid).orElse(null);
		List<PumpingModel> list = repo.findBydetails(details);
		if (list.get(1).getValue().equals("N/A") || list.get(1).getValue().equals("")) {
			model.addAttribute("pid", pid);
			return map + "/import";
		} else {
			model.addAttribute("pid", pid);
			model.addAttribute("list", repo.findBydetails(details));
			model.addAttribute("map1", pumpservice.showList(pid));
			return map + "/showlist";
		}
	}

	@RequestMapping("/settype")
	public String setType(@RequestParam("pid") Integer pid, @RequestParam("type") String type,
			RedirectAttributes attributes) {
		ProjectDetails details = detailrepo.findById(pid).orElse(null);
		List<PumpingModel> model2 = repo.findBydetails(details);
		model2.get(0).setValue(type);
		repo.save(model2.get(0));
		attributes.addFlashAttribute("pid", pid);
		attributes.addFlashAttribute("type", type);
		return "redirect:/pumping/showtype";
	}

	@RequestMapping("/showtype")
	public String showtype(Model model, @ModelAttribute("pid") Integer pid, @ModelAttribute("type") String type) {
		model.addAttribute("list", pumpservice.getList(pid, type));
		model.addAttribute("pid", pid);
		model.addAttribute("type", type);
		return map + "/import";
	}

	@RequestMapping("/savepump1")
	public String savepump1(Model model, @RequestParam("pe_imppid") Integer pid,
			@RequestParam("pe_impPipe Roughness") String rough,
			@RequestParam("pe_impCoiled Tubing Outer Diameter(inch)") String outDia,
			@RequestParam("pe_impCoiled Tubing Inner Diameter(inch)") String inDia,
			@RequestParam("pe_impAbsolute Roughness(Inch)") String absrough) {
		if (!(rough.equals("") || inDia.equals("") || absrough.equals("") || outDia.equals(""))) {
			List<String> value = Arrays.asList(rough, outDia, inDia, absrough);
			ProjectDetails details = detailrepo.findById(pid).orElse(null);
			List<PumpingModel> models = repo.findBydetails(details);
			for (int i = 1; i < models.size(); i++) {
				PumpingModel pumpmodel = models.get(i);
				pumpmodel.setValue(value.get(i - 1));
				repo.save(pumpmodel);
			}
			model.addAttribute("list", repo.findBydetails(details));
			model.addAttribute("map1", pumpservice.showList(pid));
		}
		model.addAttribute("pid", pid);
		return map + "/showlist";
	}

	@RequestMapping("/savepump2")
	public String savepump2(Model model, @RequestParam("pe_imppid") Integer pid,
			@RequestParam("pe_impPipe Roughness") String rough,
			@RequestParam("pe_impCoiled Tubing Outer Diameter(inch)") String outDia,
			@RequestParam("pe_impAbsolute Roughness(Inch)") String absrough) {
		if (!(rough.equals("") || absrough.equals("") || outDia.equals(""))) {
			List<String> value = Arrays.asList(rough, outDia, absrough);
			ProjectDetails details = detailrepo.findById(pid).orElse(null);
			List<PumpingModel> models = repo.findBydetails(details);
			int j = 0;
			for (int i = 1; i < models.size(); i++) {
				if (i != 3) {
					PumpingModel pumpmodel = models.get(i);

					pumpmodel.setValue(value.get(j));
					repo.save(pumpmodel);
					j++;
				} else {
					continue;
				}
			}
			model.addAttribute("list", repo.findBydetails(details));
			model.addAttribute("map1", pumpservice.showList(pid));

		}
		model.addAttribute("pid", pid);
		return map + "/showlist";
	}

	@RequestMapping("showupdate")
	public String showupdate(Model model, @RequestParam("pid") Integer pid,@RequestParam("type") String type) {
		model.addAttribute("pid", pid);
		model.addAttribute("map1", pumpservice.showList(pid));
		model.addAttribute("type", type);
		return map + "/update";
	}

	@RequestMapping("/settype2")
	public String setType2(@RequestParam("pid") Integer pid, @RequestParam("type") String type,
			RedirectAttributes attributes) {
		ProjectDetails details = detailrepo.findById(pid).orElse(null);
		List<PumpingModel> model2 = repo.findBydetails(details);
		model2.get(0).setValue(type);
		repo.save(model2.get(0));
		attributes.addFlashAttribute("pid", pid);
		attributes.addFlashAttribute("type", type);
		return "redirect:/pumping/showtype2";
	}

	@RequestMapping("/showtype2")
	public String showtype2(Model model, @ModelAttribute("pid") Integer pid, @ModelAttribute("type") String type) {
		model.addAttribute("list", repo.findBydetails(detailrepo.findById(pid).orElse(null)));
		model.addAttribute("pid", pid);
		model.addAttribute("type", type);
		return map + "/update";
	}

	@RequestMapping("/savepump11")
	public String savepump11(Model model, @RequestParam("pe_uppid") Integer pid,
			@RequestParam("pe_upPipe Roughness") String rough,
			@RequestParam("pe_upCoiled Tubing Outer Diameter(inch)") String outDia,
			@RequestParam("pe_upCoiled Tubing Inner Diameter(inch)") String inDia,
			@RequestParam("pe_upAbsolute Roughness(Inch)") String absrough) {
		if (!(rough.equals("") || inDia.equals("") || absrough.equals("") || outDia.equals(""))) {

			List<String> value = Arrays.asList(rough, outDia, inDia, absrough);
			ProjectDetails details = detailrepo.findById(pid).orElse(null);
			List<PumpingModel> models = repo.findBydetails(details);
			for (int i = 1; i < models.size(); i++) {
				PumpingModel pumpmodel = models.get(i);
				pumpmodel.setValue(value.get(i - 1));
				repo.save(pumpmodel);
			}
			model.addAttribute("list", repo.findBydetails(details));
			model.addAttribute("map1", pumpservice.showList(pid));


		}
		model.addAttribute("pid", pid);
		return map + "/showlist";
	}

	@RequestMapping("/savepump22")
	public String savepump22(Model model, @RequestParam("pe_uppid") Integer pid,
			@RequestParam("pe_upPipe Roughness") String rough,
			@RequestParam("pe_upCoiled Tubing Outer Diameter(inch)") String outDia,
			@RequestParam("pe_upAbsolute Roughness(Inch)") String absrough) {
		if (!(rough.equals("") || absrough.equals("") || outDia.equals(""))) {
			List<String> value = Arrays.asList(rough, outDia, absrough);
			ProjectDetails details = detailrepo.findById(pid).orElse(null);
			List<PumpingModel> models = repo.findBydetails(details);
			int j = 0;
			for (int i = 1; i < models.size(); i++) {

				if (i != 3) {
					PumpingModel pumpmodel = models.get(i);

					pumpmodel.setValue(value.get(j));
					repo.save(pumpmodel);
					j++;
				} else {
					continue;
				}
			}
			model.addAttribute("list", repo.findBydetails(details));
			model.addAttribute("map1", pumpservice.showList(pid));

		}
		model.addAttribute("pid", pid);
		return map + "/showlist";
	}
}
