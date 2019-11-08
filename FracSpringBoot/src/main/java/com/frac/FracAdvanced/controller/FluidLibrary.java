package com.frac.FracAdvanced.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.frac.FracAdvanced.model.FluidLibraryModel;
import com.frac.FracAdvanced.service.FluidLibraryService;

@Controller
public class FluidLibrary {
	@Autowired
	FluidLibraryService fls;
	
	@RequestMapping("/showFLFirstController")
	public String showFL(@RequestParam("pid") String pid,
			           Model model) throws Exception
	{		
		List<FluidLibraryModel> b1=fls.method1(pid,"Linear Gel");	
		         ArrayList<String> fluidTypeList =fls.method2GetFluidType(pid);
		         model.addAttribute("fluidTypeList", fluidTypeList);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);			
		return "/view/FluidLibrary/Show";
		
	}
	//buttonValue
	@RequestMapping("/showFLSecondController")
	public String newtonian(@RequestParam("pid") String pid,
			              @RequestParam("fluidT") String ftype,
			              @RequestParam("buttonValue") String buttonValue,
			              Model model) throws Exception
	{
		List<FluidLibraryModel> b1=fls.method1(pid,ftype);	
		ArrayList<String> fluidTypeList =fls.method2GetFluidType(pid);
        model.addAttribute("fluidTypeList", fluidTypeList);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		model.addAttribute("buttonValue1", buttonValue);
		return "/view/FluidLibrary/Show";
		
	}
	
	
	@RequestMapping("/showFLThirdController")
	public String showFL3(@RequestParam("pid") String pid,		              
			              @RequestParam("parameter") List<String> parameter,
			              @RequestParam("value") List<String> value,
			              @RequestParam("ftype1") List<String> ftype,
			              Model model) throws Exception
	{
		String typeNmae=ftype.get(0);		
		List<FluidLibraryModel> b1=fls.methodEdit(pid,typeNmae,value, parameter);	
		ArrayList<String> fluidTypeList =fls.method2GetFluidType(pid);
        model.addAttribute("fluidTypeList", fluidTypeList);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		return "/view/FluidLibrary/Show";
		
	}
	
	
	
	@RequestMapping("/systemNewtonian")
	public String showFL4(@RequestParam("pid") String pid,		              
			              @RequestParam("parameter") List<String> parameter,
			              @RequestParam("value") List<String> value,
			              @RequestParam("ftype1") List<String> ftype,
			              Model model) throws Exception
	{
		String typeNmae=ftype.get(0);	
		List<FluidLibraryModel> b1=fls.methodEditNewtonian(pid,typeNmae,value, parameter);	
		ArrayList<String> fluidTypeList =fls.method2GetFluidType(pid);
        model.addAttribute("fluidTypeList", fluidTypeList);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		return "/view/FluidLibrary/Show";
		
	}
	
	
	@RequestMapping("/showFLFifthController")
	public String showFL5(@RequestParam("pid") String pid,		              
			              @RequestParam("k") float k,
			              @RequestParam("neta") float neta,
			              @RequestParam("gama") float gama,
			              @RequestParam("ftype1") String ftype,
			              Model model) throws Exception
	{
		List<FluidLibraryModel> b1=fls.methodCalculateViscosity(pid,k,neta, gama,ftype);	
		ArrayList<String> fluidTypeList =fls.method2GetFluidType(pid);
        model.addAttribute("fluidTypeList", fluidTypeList);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		
		return "/view/FluidLibrary/show";
		
	}
	
	@RequestMapping("/showFLSixthController")
	public String showFL6(@RequestParam("pid") String pid,		              
		                  @RequestParam("parameter") List<String> parameter,
                          @RequestParam("valuev") List<String> value,
                          @RequestParam("ftypevalue") String ftype,
			              Model model) throws Exception
	{				
		List<FluidLibraryModel> b1=fls.userMethod(pid,parameter,value,ftype);	
		ArrayList<String> fluidTypeList =fls.method2GetFluidType(pid);
        model.addAttribute("fluidTypeList", fluidTypeList);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		return "/view/FluidLibrary/Show";
		
	}
	
	
	@RequestMapping("/showFLseventhController")
	public String showFL7(@RequestParam("pid") String pid,		              		                 
                          @RequestParam("value") String value,
                          @RequestParam("ftype10") String ftype,
			              Model model) throws Exception
	{	
		List<FluidLibraryModel> b1=fls.userMethod2(pid,value,ftype);	
		ArrayList<String> fluidTypeList =fls.method2GetFluidType(pid);
        model.addAttribute("fluidTypeList", fluidTypeList);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		return "/view/FluidLibrary/Show";
		
	}
	
	
	@RequestMapping("/showFLeighthController")
	public String showFL8(@RequestParam("pid") String pid,		              
			              @RequestParam("k") float k,
			              @RequestParam("neta") float neta,
			              @RequestParam("gama") float gama,
			              @RequestParam("ftype10") String ftype,
			              Model model) throws Exception
	{			
		List<FluidLibraryModel> b1=fls.methodCalculateViscosity(pid,k,neta, gama,ftype);
		ArrayList<String> fluidTypeList =fls.method2GetFluidType(pid);
        model.addAttribute("fluidTypeList", fluidTypeList);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		return "/view/FluidLibrary/Show";
		
	}
	@RequestMapping("/removeFluidFromLibrary")
	public String showFL9(@RequestParam("pid") String pid,		              			              
			              @RequestParam("fluidT") String ftype,
			              Model model) throws Exception
	{			
		
		fls.methodremoveFluidFromLibrary(pid,ftype);
		List<FluidLibraryModel> b1=fls.method1(pid,"fluidType2");
		ArrayList<String> fluidTypeList =fls.method2GetFluidType(pid);
        model.addAttribute("fluidTypeList", fluidTypeList);
		model.addAttribute("pid", pid);
		model.addAttribute("list", b1);
		return "/view/FluidLibrary/Show";
		
	}
	
}
