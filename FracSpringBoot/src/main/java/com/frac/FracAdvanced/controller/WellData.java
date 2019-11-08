package com.frac.FracAdvanced.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.frac.FracAdvanced.model.WellDataModel;
import com.frac.FracAdvanced.service.WellDataService;

@Controller
public class WellData {
	@Autowired
	WellDataService service;

		@RequestMapping("/wellData")
		public String show(@RequestParam("pro_Id") String pid,
				           Model model) throws Exception
		{
		                   List<WellDataModel> list4=service.wellUpdateMethod1(pid);
		                   model.addAttribute("list",list4);
		                   model.addAttribute("pid",pid);			
			               return "view/WellDataFolder/GetData";}
		
		
		@RequestMapping("/updateWellData")
		public String update(Model model,
				         @RequestParam("pid") String pid,
				         @RequestParam("name1") List<String> value,
				         @RequestParam("wellType") String wellType,
				         @RequestParam("completionType") String completionType) throws Exception			
		{
			List<WellDataModel> list4=service.wellUpdateMethod(pid, completionType, wellType, value);
			model.addAttribute("list",list4);
			model.addAttribute("pid",pid);
		
			return "view/WellDataFolder/GetData";
		}
		
		@RequestMapping("/updateWellData2")
		public String wellUpdate2(Model model,
				         @RequestParam("pid") String pid,
				         @RequestParam("name1") List<String> List4,
				         @RequestParam("wellType") String wellType,
				         @RequestParam("completionType") String completionType) throws Exception			
		{			
			List<WellDataModel> list5=service.wellUpdateMethod1(pid);
			model.addAttribute("list", list5);		
			model.addAttribute("pid",pid);
			return "view/WellDataFolder/wellDataShow";
		}
		
}
