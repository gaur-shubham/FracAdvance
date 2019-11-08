/**
 * 
 */
package com.frac.FracAdvanced.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frac.FracAdvanced.service.MainFracGraphService;

import java.util.*;

/**
 * @author ShubhamGaur
 *
 */
@Component
public class MainFracGraph {
	
	@Autowired
	MainFracGraphService fracGraphService;
	
	public void SaveMainFrac(Integer pid) {
		String[ ] arr1= {"218","299","363","397","487","607","688","761","833"};
		String[ ] arr2= {"0.00616","0.00699","0.00756","0.00784","0.00850","0.00928","0.00976","0.01016","0.01054"};
		List<String> valuesY=MainFracCalc(arr1, arr2).get("Y");
		List<String> valuesX=MainFracCalc(arr1, arr2).get("X");
		fracGraphService.saveFracGraph(pid, valuesX,valuesY);
	}
	
	public Map<String,List<String>> MainFracCalc(String[ ] arr1,String[ ] arr2) {
		List<String> length=Arrays.asList(arr1);
		List<String> width=Arrays.asList(arr2);
		Map<String,List<String>> map=new LinkedHashMap<>();
		List<String> finalYList=new ArrayList<>();
		List<String> finalXList=new ArrayList<>();
		for(int i=0;i<length.size();i++) {
			Double x=0.0,y=0.0;
			List<String> ys=new ArrayList<String>();
			List<String> xs=new ArrayList<String>();
			List<String> ysRev=new ArrayList<String>();
			List<String> xsRev=new ArrayList<String>();
			while(x<Double.parseDouble(length.get(i))) {
				Double a=Double.parseDouble(width.get(i));
				Double b=Double.parseDouble(length.get(i));
				y=Math.pow((((Math.pow(a, 2)*Math.pow(b, 2))-(Math.pow(x, 2)*Math.pow(a, 2)))/Math.pow(b, 2)), 0.5);
				ys.add(y.toString());
				xs.add(x.toString());
				x+=30.0;
			}
			xs.add(x.toString());
			ys.add("0");
			for(int j=ys.size()-1;j>=0;j--) {
				ysRev.add("-"+ys.get(j));
			}
			for(int j=xs.size()-1;j>=0;j--) {
				xsRev.add(xs.get(j));
			}
			finalYList.addAll(ys);
			finalXList.addAll(xs);
			finalYList.addAll(ysRev);
			finalXList.addAll(xsRev);
		}
		map.put("Y",finalYList);
		map.put("X",finalXList);
		return map;
	}
	
}
