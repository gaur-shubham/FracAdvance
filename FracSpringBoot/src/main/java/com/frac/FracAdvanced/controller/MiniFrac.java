package com.frac.FracAdvanced.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.frac.FracAdvanced.Method.InputFile;
import com.frac.FracAdvanced.model.MiniFracModel;
import com.frac.FracAdvanced.repository.MiniFracRepo;
import com.frac.FracAdvanced.service.MiniFracService;
/**
 * @author ShubhamGaur
 *
 */
@Controller
public class MiniFrac {
	
	String map="view/minifrac";
	
	@Autowired
	private MiniFracRepo fracRepo;
	@Autowired
	private MiniFracService fracservice; 

	
	@RequestMapping("/miniFrac")
	public String Show(Model model, @RequestParam("pro_Id") Integer pid) {
		List<MiniFracModel> list=fracRepo.findByProId(pid);
		if(list.isEmpty()) {
			model.addAttribute("hiddenId", pid);
			return map+"/import";
		}else
		{	
			model.addAttribute("list", list);
			model.addAttribute("hiddenId", pid);
			return map+"/showlist";
		}
	}
	
	@PostMapping("/upload")
	public String upload(Model model,@RequestParam("files") MultipartFile file,
			@RequestParam("pumptime") Double pumptime,
			@RequestParam("pid") int pid)throws Exception {
		List<MiniFracModel> minifracList = InputFile.process(file,pumptime,pid);
		model.addAttribute("list", minifracList);
		model.addAttribute("hiddenId", pid);
		return map+"/showlist";
	}
	
	@RequestMapping("/update")
	public String update(@RequestParam("pid") Integer pid,Model model) {
		List<MiniFracModel> list=fracRepo.findByProId(pid);
		model.addAttribute("list", list);
		model.addAttribute("hiddenId", pid);
		return map+"/update";
	}
	
	@RequestMapping("/save")
	public String save(Model model,@RequestParam("pid") Integer pid,@RequestParam("rid") List<Integer> id,
			@RequestParam("pumptime") String pumptime,@RequestParam("pressure") List<String> pressure,
			@RequestParam("time") List<String> time){
		List<MiniFracModel> list=new ArrayList<>();
		for(int i=0;i<id.size();i++) {
			MiniFracModel fracModel=fracRepo.getOne(id.get(i));
			fracModel.setPressure(pressure.get(i));
			fracModel.setTime(time.get(i));
			fracModel.setPumptime(pumptime);
			list.add(fracModel);
		}
		fracRepo.saveAll(list);
		List<MiniFracModel> list1=fracRepo.findByProId(pid);
		model.addAttribute("list", list1);
		model.addAttribute("hiddenId", pid);
		return map+"/showlist";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("id") Integer id,@RequestParam("pid") Integer pid,Model model) {
		List<MiniFracModel> list;
		fracRepo.deleteById(id);
		list=fracRepo.findByProId(pid);
		model.addAttribute("hiddenId", pid);
		model.addAttribute("list", list);
		return map+"/update";
	}
	
	@RequestMapping("/addfield")
	public String addfield(Model model,@RequestParam("pid") Integer pid,@RequestParam("mf_impinput") List<String> value) {
		fracservice.savefield(pid, value);
		model.addAttribute("hiddenId", pid);
		model.addAttribute("pumptime", fracservice.showfields(pid).get(0).getPumptime());
		model.addAttribute("list", fracservice.showfields(pid));
		return map+"/import";
	}
}
