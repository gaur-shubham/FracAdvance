/**
 * 
 */
package com.frac.FracAdvanced.Method;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.frac.FracAdvanced.service.OutputStressService;

/**
 * @author ShubhamGaur
 *
 */
@Component
public class StressAnalysisAlgo {

	@Autowired
	private OutputStressService outputservice;


	public void calculate(Map<String, String> map, Integer pid) {
		Map<String, Double> OStressMap = new LinkedHashMap<String, Double>();
		Map<String, Double> evstressMap = new LinkedHashMap<String, Double>();
		Map<String, Double> ehstressMap = new LinkedHashMap<String, Double>();
		Map<String, Double> tmhMap = new LinkedHashMap<String, Double>();
		Map<Double, Double> findDepth = new LinkedHashMap<Double, Double>();
		Map<String, String> depths = new LinkedHashMap<String, String>();
		Map<String, String> BDPress = new LinkedHashMap<String, String>();
		double TStrength = 2000;
		double Tectonic = 2000;
		double alpha = 0.7;

		double Density = 0.0;
		double PRatio = 0.0;
		double pp = 0.0;
		double depth = 0.0;
		double depth1 = 0.0;
		double OStress = 0.0;
		double evstress = 0.0;
		double ehstress = 0.0;
		double tmh = 0.0;
		double tmaxh = 0.0;
		double BDpressure = 0.0;
		double tmhmin = 0.0;

		for (int i = 0; i < map.size() / 4; i++) {
			String key = "PorePressure" + i;
			pp = Double.parseDouble(map.get(key));
			String key1 = "Depth" + i;
			depth = Double.parseDouble(map.get(key1));
			String key2 = "PoissonRatio" + i;
			PRatio = Double.parseDouble(map.get(key2));
			String key3 = "Density" + i;
			Density = Double.parseDouble(map.get(key3));
			OStress = (Density * depth) / 144;
			String keyOStress = "OStress" + i;
			OStressMap.put(keyOStress, OStress);
			evstress = OStress - (alpha * pp);
			String keyevstress = "evstress" + i;
			evstressMap.put(keyevstress, evstress);
			ehstress = ((PRatio) / (1 - PRatio)) * evstress;
			String keyehstress = "ehstress" + i;
			ehstressMap.put(keyehstress, ehstress);
			tmh = ehstress + (alpha * pp);
			String keytmh = "tmh" + i;
			tmhMap.put(keytmh, tmh);
			findDepth.put(tmh, depth);
			tmhmin = ehstress + (0.7) * pp;
			tmaxh = tmhmin + Tectonic;
			BDpressure = (3 * tmhmin) - tmaxh + TStrength - pp;
			depths.put("depth" + i, String.valueOf(depth));
			BDPress.put("BDpressure" + i, String.valueOf(BDpressure));
		}
		double a[] = new double[2];
		a[0] = BDpressure;
		a[1] = depth1;
		outputservice.saveCalc(BDPress, depths, tmhMap, pid);
	}

}
