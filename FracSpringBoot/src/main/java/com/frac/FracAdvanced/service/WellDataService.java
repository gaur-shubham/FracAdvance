/**
 * 
 */

/**
 * @author ShubhamGaur
 *
 */
package com.frac.FracAdvanced.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.WellDataModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.WellDataRepo;


@Component
public class WellDataService {
	
	@Autowired
	WellDataRepo rs;	
	@Autowired
	ProjectDetailRepo pr;
	
	public List<WellDataModel> wellUpdateMethod1(String pid)
	{	
		  int pid1= Integer.parseInt(pid);	 
	      List<WellDataModel> list4= rs.findByProId(pid1);
	     
	      return list4;
	}
	public List<WellDataModel> wellUpdateMethod(String pid2,String completionType, String wellType, List<String> list1)
	{
		
		int pid3= Integer.parseInt(pid2);	
		
		  List<WellDataModel> list5= rs.findByProId(pid3);
		  
	   for(int i=0;i<list5.size()-1;i++)
	   {
		list5.get(i).setWellType(wellType);
		list5.get(i).setCompletionType(completionType);
		list5.get(i).setValue(list1.get(i));
		rs.save(list5.get(i));		
	   }
	  List<WellDataModel> list4= rs.findByProId(pid3);
	    return list4;
	}
}

