package com.frac.FracAdvanced.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.frac.FracAdvanced.model.FluidLibraryModel;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.repository.FluidLibraryRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;

import java.util.*;
@Component
public class FluidLibraryService {
	
	@Autowired
	FluidLibraryRepo flr;
	@Autowired
	ProjectDetailRepo pdr;
	public List<FluidLibraryModel> method1(String pid, String ftype) throws Exception
	{
		
	
		flr.findByProId(Integer.parseInt(pid)).get(0).setFluidTypeSelected(ftype);;
			flr.save(flr.findByProId(Integer.parseInt(pid)).get(0));
			
			
			
		ArrayList<FluidLibraryModel> a1= new ArrayList<FluidLibraryModel>();
		
		int pid1= Integer.parseInt(pid);
		List<FluidLibraryModel> x12=flr.findByProId(pid1);	
			
		for(int i=0;i<x12.size();i++) {		
				if((x12.get(i).getType()).equalsIgnoreCase(ftype))
		{
        a1.add(x12.get(i));			
             }				
		}	
		return a1;
	}
	
	public ArrayList<String> method2GetFluidType(String pid)
	{
		Set<String> uniqueTypeSet= new HashSet<String>();
		int pid1= Integer.parseInt(pid);
		List<FluidLibraryModel> x12=flr.findByProId(pid1);
		for(int i=0;i<x12.size();i++) {		
			String uniqueList=   x12.get(i).getType();
			uniqueTypeSet.add(uniqueList);
		}
		ArrayList<String> fluidTypeList=new ArrayList<String>(uniqueTypeSet);
		return fluidTypeList;
		
	}
	
	
	public void methodremoveFluidFromLibrary(String pid,String type)
	{
		
		int pid1= Integer.parseInt(pid);
		List<FluidLibraryModel> x12=flr.findByProId(pid1);
		for(int i=0;i<x12.size();i++) {		
			if(type.equalsIgnoreCase("Surface Crosslink")||type.equalsIgnoreCase("Linear Gel"))
			{}
			else if(x12.get(i).getType().equalsIgnoreCase(type))
			{
				int id1= x12.get(i).getId();
				flr.deleteById(id1);
			}			
		}}
	
	
	
	public List<FluidLibraryModel> methodEdit(String pid, String ftype, List<String> value, List<String> parameter) throws Exception
	{		
		ArrayList<FluidLibraryModel> a1= new ArrayList<FluidLibraryModel>();
		int pid1= Integer.parseInt(pid);
		List<FluidLibraryModel> x12=flr.findByProId(pid1);	
		int i1=0;	
		for(int i=0;i<x12.size();i++) {			 
			if((x12.get(i).getType()).equals(ftype))
		{				
      x12.get(i).setParameter(parameter.get(i1));
      x12.get(i).setValue(value.get(i1));
      flr.save(x12.get(i));               
			i1=i1+1;
			
             }	/*if((i1>0)) {break;}*/	}
		//get the value
		List<FluidLibraryModel> x123=flr.findByProId(pid1);
         for(int i=0;i<x123.size();i++) {			
			if((x123.get(i).getType()).equals(ftype))
		{				
               a1.add(x123.get(i));			
             }		}
		return a1;
	}
	
/// calculating          
	
	public List<FluidLibraryModel> methodCalculateViscosity(String pid, float k,float neta, float gama, String ftype) throws Exception
	{		
		double viscosity=47880*k*(Math.pow(gama, (neta-1)));		
		ArrayList<FluidLibraryModel> a1= new ArrayList<FluidLibraryModel>();
		int pid1= Integer.parseInt(pid);
		List<FluidLibraryModel> x12=flr.findByProId(pid1);	
		int i1=0;	
		for(int i=0;i<x12.size();i++) {			
			if((x12.get(i).getType()).equals(ftype))
		{				
     
      x12.get(i).setValue(String.valueOf(viscosity));
      flr.save(x12.get(i));               
			i1=i1+1;
			
			break;  }		}
		//get the value
		List<FluidLibraryModel> x123=flr.findByProId(pid1);
         for(int i=0;i<x123.size()-1;i++) {			
			if((x123.get(i).getType()).equals(ftype))
		{				
               a1.add(x123.get(i));			
             }		}
		return a1;
	}
	/// user methods
	public List<FluidLibraryModel> userMethod(String pid, List<String> parameter,List<String> value,  String ftype) throws Exception
	{		
		
		ArrayList<FluidLibraryModel> a1= new ArrayList<FluidLibraryModel>();
		int pid1= Integer.parseInt(pid);
		ProjectDetails pd1=	pdr.getOne(pid1);
		//List<FluidLibraryModel> x12=flr.findByProId(pid1);	
		
		
		for(int i=0; i<parameter.size();i++)
		{FluidLibraryModel g1=new FluidLibraryModel();
			g1.setType(ftype);
		g1.setPidFL(pd1);
		g1.setParameter(parameter.get(i));
		g1.setValue(value.get(i));
		flr.save(g1);
		}
		//get the value
		List<FluidLibraryModel> x123=flr.findByProId(pid1);
         for(int i=0;i<x123.size()-1;i++) {			
			if((x123.get(i).getType()).equals(ftype))
		{				
               a1.add(x123.get(i));			
             }		}
		return a1;
	}
	
	
	public List<FluidLibraryModel> methodEditNewtonian(String pid, String ftype, List<String> value, List<String> parameter) throws Exception
	{		
		ArrayList<FluidLibraryModel> a1= new ArrayList<FluidLibraryModel>();
		int pid1= Integer.parseInt(pid);
		List<FluidLibraryModel> x12=flr.findByProId(pid1);	
		int i1=0;	
		for(int i=0;i<x12.size();i++) {			 
			if((x12.get(i).getType()).equals(ftype))
		{		
      x12.get(i).setParameter(parameter.get(i1));
      x12.get(i).setValue(value.get(i1));
      flr.save(x12.get(i));               
			i1=i1+1;
			
             }	if((i1>0)) {break;}	}
		//get the value
		List<FluidLibraryModel> x123=flr.findByProId(pid1);
         for(int i=0;i<x123.size();i++) {			
			if((x123.get(i).getType()).equals(ftype))
		{				
               a1.add(x123.get(i));			
             }		}
		return a1;
	}
	public List<FluidLibraryModel> userMethod2(String pid, String value,  String ftype) throws Exception
	{		
		
		ArrayList<FluidLibraryModel> a1= new ArrayList<FluidLibraryModel>();
		int pid1= Integer.parseInt(pid);
		List<FluidLibraryModel> x12=flr.findByProId(pid1);	
		int i1=0;	
		for(int i=0;i<x12.size();i++) {			
			if((x12.get(i).getType()).equals(ftype))
		{				    
      x12.get(i).setValue(value);
      flr.save(x12.get(i));               
			i1=i1+1;			
             }		}
		//get the value
		List<FluidLibraryModel> x123=flr.findByProId(pid1);
         for(int i=0;i<x123.size()-1;i++) {			
			if((x123.get(i).getType()).equals(ftype))
		{				
               a1.add(x123.get(i));			
             }		}
		return a1;
	}
	
	
}
