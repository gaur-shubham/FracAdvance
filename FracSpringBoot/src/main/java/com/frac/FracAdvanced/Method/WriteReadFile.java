package com.frac.FracAdvanced.Method;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.frac.FracAdvanced.model.MiniFracModel;
import com.frac.FracAdvanced.model.StressAnalysisModel;
import com.frac.FracAdvanced.repository.MiniFracRepo;
import com.frac.FracAdvanced.service.StressAnalysisService;

/**
 * @author ShubhamGaur
 *
 */
@Component
public class WriteReadFile {
	
	@Autowired
	private  MiniFracRepo service;
	@Autowired
	private  HttpSession session;
	@Autowired
	private StressAnalysisService stressService;


	public  void createMinifracInputFile(Integer pid) throws Exception {
		List<MiniFracModel> inputList = service.findByProId(pid);
		String path = session.getServletContext().getRealPath("/");
		String filename = "InputFile.txt";
		int i = 0;
		FileWriter write = new FileWriter(path + "/InputFile/" + filename);
		BufferedWriter buffer = new BufferedWriter(write);
		if (inputList.size() > 0) {
			MiniFracModel pump = inputList.get(0);
			buffer.write("/*****************~Mini Frac~**********************/");
			buffer.newLine();
			buffer.write("PUMPINGTIME=" + pump.getPumptime());
		}

		for (MiniFracModel frac : inputList) {
			buffer.newLine();
			buffer.write("DURATION" + i + "=" + frac.getTime());
			buffer.newLine();
			buffer.write("PRESSURE" + i + "=" + frac.getPressure());
			i++;
		}
		if (inputList.size() > 0) {
			buffer.newLine();
			buffer.write("//");
		}
		buffer.close();
	}

	public  void createStressInputFile(Integer pid) throws Exception {
		List<StressAnalysisModel> list = stressService.showList(pid);
		String path = session.getServletContext().getRealPath("/");
		String filename = "InputFile.txt";
		FileWriter write = new FileWriter(path + "/InputFile/" + filename, true);
		BufferedWriter buffer = new BufferedWriter(write);

		if (list.size() > 0) {
			buffer.newLine();
			buffer.write("/*****************~Stress Analysis~**********************/");
		}
		for (int i = 0; i < list.size(); i++) {
			buffer.newLine();
			buffer.write("PoissonRatio" + i + "=" + list.get(i).getRatio());
			buffer.newLine();
			buffer.write("Density" + i + "=" + list.get(i).getDensity());
			buffer.newLine();
			buffer.write("Depth" + i + "=" + list.get(i).getDepth());
			buffer.newLine();
			buffer.write("PorePressure" + i + "=" + list.get(i).getPore());
		}
		if (list.size() > 0) {
			buffer.newLine();
			buffer.write("//");
		}
		buffer.close();
	}

	public  Map<String, String> readInputFile(String name) throws Exception {
		String filename = "/InputFile/InputFile.txt";
		Map<String, String> map = new LinkedHashMap<>();
		Map<String, String> map1 = new LinkedHashMap<>();
		String path = session.getServletContext().getRealPath("/");
		
		FileReader fr = new FileReader(path + filename);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while ((line = br.readLine()) != null) {
			if (!(line.startsWith("//") || line.startsWith("/****") || line.isEmpty())) {
				int index = line.indexOf("=");
				String key = line.substring(0, index);
				String value = line.substring(index + 1);
				map.put(key, value.trim());
			} else {
				continue;
			}
		}
		if (name.equalsIgnoreCase("MiniFrac")) {
			for (int i = 0; i < map.size(); i++) {
				String key1 = "PUMPINGTIME";
				String key2 = "DURATION" + i;
				String key3 = "PRESSURE" + i;
				if (map.containsKey(key1) && map.containsKey(key2) && map.containsKey(key3)) {
					map1.put(key1, map.get(key1));
					map1.put(key2, map.get(key2));
					map1.put(key3, map.get(key3));
				}
			}
		} else if (name.equalsIgnoreCase("StressAnalysis")) {
			for (int i = 0; i < map.size(); i++) {
				String key1 = "PoissonRatio" + i;
				String key2 = "Density" + i;
				String key3 = "Depth" + i;
				String key4 = "PorePressure" + i;
				if (map.containsKey(key1) && map.containsKey(key2) && map.containsKey(key3) && map.containsKey(key4)) {
					map1.put(key1, map.get(key1));
					map1.put(key2, map.get(key2));
					map1.put(key3, map.get(key3));
					map1.put(key4, map.get(key4));
				}
			}
		}
		return map1;
	}
}
