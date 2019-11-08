/**
 * 
 */
package com.frac.FracAdvanced.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.StressAnalysisModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.StressAnalysisRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class StressAnalysisService {

	@Autowired
	StressAnalysisRepo analysisRepo;
	@Autowired
	ProjectDetailRepo detailRepo;

	public void saveField(Integer pid, List<String> input) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		StressAnalysisModel analysisModel = new StressAnalysisModel();
		analysisModel.setRatio(input.get(0));
		analysisModel.setDensity(input.get(1));
		analysisModel.setDepth(input.get(2));
		analysisModel.setPore(input.get(3));
		analysisModel.setDetails(detail);
		analysisRepo.save(analysisModel);
	}

	public List<StressAnalysisModel> showList(Integer pid) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<StressAnalysisModel> analysisModels = analysisRepo.findBydetails(detail);
		return analysisModels;
	}

	public void deleteField(Integer pid, Integer id) {
		analysisRepo.deleteById(id);
	}

	public void saveUpdate(Integer pid, List<Integer> id, List<String> input) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<StressAnalysisModel> analysisModels = analysisRepo.findBydetails(detail);
		List<StressAnalysisModel> list = new ArrayList<StressAnalysisModel>();
		int j = 0;
		for (int i = 0; i < id.size(); i++) {
			StressAnalysisModel model = analysisModels.get(i);
			model.setRatio(input.get(j));
			j++;
			model.setDensity(input.get(j));
			j++;
			model.setDepth(input.get(j));
			j++;
			model.setPore(input.get(j));
			j++;
			model.setDetails(detail);
			list.add(model);

		}
		analysisRepo.saveAll(list);
	}

	public void readFile(Integer pid, MultipartFile file) throws Exception {
		String fileName = file.getOriginalFilename();
		if (fileName.endsWith("txt")) {
			// **************** read a txt file here **********************
		} else if (fileName.endsWith("xlsx")) {
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			Integer sheetNumber=0;
			for(int i=0;i<workbook.getNumberOfSheets();i++) {
				if(workbook.getSheetName(i).replaceAll("\\s+", "").equalsIgnoreCase("stressanalysis")) {
					sheetNumber=i;
				}
			}
			Row row;
			Cell c;
			int depth = 0, poisson = 0, density = 0, pp = 0;
			List<String> depths = new ArrayList<String>();
			List<String> densitys = new ArrayList<String>();
			List<String> pps = new ArrayList<String>();
			List<String> poissons = new ArrayList<String>();
			XSSFSheet worksheet = workbook.getSheetAt(sheetNumber);
			for (int i = worksheet.getFirstRowNum(); i < worksheet.getLastRowNum(); i++) {
				row = worksheet.getRow(i);
				if (row != null) {
					for (int j = 0; j < row.getLastCellNum(); j++) {
						c = row.getCell(j);
						if (c != null && i == 0) {
							if (c.toString().equalsIgnoreCase("Poisson's ratio")) {
								poisson = j;
							} else if (c.toString().equalsIgnoreCase("Density(lb/ft3)")) {
								density = j;
							} else if (c.toString().equalsIgnoreCase("Depth(ft)")) {
								depth = j;
							} else if (c.toString().equalsIgnoreCase("Pp(psi)")) {
								pp = j;
							}
						} else if (c != null) {
							if (j == depth && !c.toString().equals("")) {
								depths.add(String.valueOf(c.getNumericCellValue()));
							} else if (j == density && !c.toString().equals("")) {
								densitys.add(String.valueOf(c.getNumericCellValue()));
							} else if (j == pp && !c.toString().equals("")) {
								pps.add(String.valueOf(c.getNumericCellValue()));
							} else if (j == poisson && !c.toString().equals("")) {
								poissons.add(String.valueOf(c.getNumericCellValue()));
							}
						}
					}
				}
			}
			workbook.close();
			saveByFile(pid, depths, densitys, pps, poissons);
		}
	}

	public void saveByFile(Integer pid, List<String> depths, List<String> densitys, List<String> pps,
			List<String> poissons) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<StressAnalysisModel> list = new ArrayList<StressAnalysisModel>();
		for (int i = 0; i < pps.size(); i++) {
			StressAnalysisModel model = new StressAnalysisModel();
			model.setDensity(densitys.get(i));
			model.setDepth(depths.get(i));
			model.setRatio(poissons.get(i));
			model.setPore(pps.get(i));
			model.setDetails(detail);
			list.add(model);
		}
		analysisRepo.saveAll(list);
	}
}
