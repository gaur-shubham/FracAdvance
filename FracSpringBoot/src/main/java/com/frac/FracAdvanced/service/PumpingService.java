/**
 * 
 */
package com.frac.FracAdvanced.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.PumpingModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.PumpingRepo;

/**
 * @author ShubhamGaur
 *
 */

@Service
public class PumpingService {

	@Autowired
	private PumpingRepo pumpingRepo;
	@Autowired
	private ProjectDetailRepo detailrepo;

	public List<String> getList(Integer pid, String type) {
		ProjectDetails details = detailrepo.findById(pid).orElse(null);
		List<PumpingModel> models = pumpingRepo.findBydetails(details);
		List<String> list = new ArrayList<String>();
		if (type.equalsIgnoreCase("ct")) {
			for (int i = 0; i < models.size(); i++) {
				if (models.get(i).getParam().equalsIgnoreCase("Pipe Roughness")
						|| models.get(i).getParam().equalsIgnoreCase("Coiled Tubing Inner Diameter(inch)")
						|| models.get(i).getParam().equalsIgnoreCase("Coiled Tubing Outer Diameter(inch)")
						|| models.get(i).getParam().equalsIgnoreCase("Absolute Roughness(Inch)")) {
					list.add(models.get(i).getParam());
				}
			}
		} else if (type.equalsIgnoreCase("bullhead")) {
			for (int i = 0; i < models.size(); i++) {
				if (models.get(i).getParam().equalsIgnoreCase("Pipe Roughness")
						|| models.get(i).getParam().equalsIgnoreCase("Coiled Tubing Outer Diameter(inch)")
						|| models.get(i).getParam().equalsIgnoreCase("Absolute Roughness(Inch)")) {
					list.add(models.get(i).getParam());
				}
			}
		}
		return list;
	}

	public Map<String, String> showList(Integer pid) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		ProjectDetails details = detailrepo.findById(pid).orElse(null);
		List<PumpingModel> models = pumpingRepo.findBydetails(details);
		if (models.get(0).getValue().equalsIgnoreCase("ct")) {
			for (int i = 1; i < models.size(); i++) {
				map.put(models.get(i).getParam(), models.get(i).getValue());
			}
		} else if (models.get(0).getValue().equalsIgnoreCase("bullhead")) {
			for (int i = 1; i < models.size(); i++) {
				if (!(models.get(i).getParam().equalsIgnoreCase("Coiled Tubing Inner Diameter(inch)"))) {
					map.put(models.get(i).getParam(), models.get(i).getValue());
				}
			}
		}
		return map;
	}
}
