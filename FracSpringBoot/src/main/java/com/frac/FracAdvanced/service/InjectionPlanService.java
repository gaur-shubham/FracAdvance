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

import com.frac.FracAdvanced.model.InjectionPlanModel;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.repository.InjectionPlanRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class InjectionPlanService {

	@Autowired
	InjectionPlanRepo planRepo;
	@Autowired
	ProjectDetailRepo detailRepo;

	public List<InjectionPlanModel> showInjectionPlan(Integer pid) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		return planRepo.findBydetails(detail);
	}

	public void saveInjectionPlan(Integer pid, List<String> input) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		String slurry = calcSlurry(input.get(4), input.get(5), input.get(6));
		String cumslurry = calcCumSlurry(pid, slurry);
		String stageprop = calcStageProp(input.get(4), input.get(5), input.get(6));
		String cumprop = calcCumProp(pid, stageprop);
		String steptime = calcStepTime(slurry, input.get(3));
		String cumulStepTime=calcumulStepTime(pid,steptime);
		InjectionPlanModel model = new InjectionPlanModel();
		model.setStep(input.get(0));
		model.setFluidtype(input.get(1));
		model.setStagetype(input.get(2));
		model.setRate(input.get(3));
		model.setCleanvol(input.get(4));
		model.setSlurryvol(slurry);
		model.setCumslurry(cumslurry);
		model.setBegprop(input.get(5));
		model.setEndprop(input.get(6));
		model.setStageprop(stageprop);
		model.setCumprop(cumprop);
		model.setSteptime(steptime);
		model.setDetails(detail);
		model.setCumStepTime(cumulStepTime);
		planRepo.save(model);
	}

	public void SaveUpdate(Integer pid, List<String> input) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<InjectionPlanModel> injectionPlanModels = planRepo.findBydetails(detail);
		List<InjectionPlanModel> injectionPlanModels2 = new ArrayList<InjectionPlanModel>();
		int j = 0;
		String slurry = calcSlurry(input.get(4), input.get(5), input.get(6));
		String cumslurry = calcCumSlurry(pid, slurry);
		String stageprop = calcStageProp(input.get(4), input.get(5), input.get(6));
		String cumprop = calcCumProp(pid, stageprop);
		String steptime = calcStepTime(slurry, input.get(3));
		for (int i = 0; i < injectionPlanModels.size(); i++) {
			InjectionPlanModel model = injectionPlanModels.get(i);
			model.setStep(input.get(j));
			j++;
			model.setFluidtype(input.get(j));
			j++;
			model.setStagetype(input.get(j));
			j++;
			model.setRate(input.get(j));
			j++;
			model.setCleanvol(input.get(j));
			j++;
			model.setSlurryvol(slurry);
			model.setCumslurry(cumslurry);
			model.setBegprop(input.get(j));
			j++;
			model.setEndprop(input.get(j));
			j++;
			model.setStageprop(stageprop);
			model.setCumprop(cumprop);
			model.setSteptime(steptime);
			injectionPlanModels2.add(model);
		}
		planRepo.saveAll(injectionPlanModels2);
	}

	public void deleteInjection(Integer pid, Integer id) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<InjectionPlanModel> injectionPlanModels = planRepo.findBydetails(detail);
		int line = 0;
		for (int i = 0; i < injectionPlanModels.size(); i++) {
			if (injectionPlanModels.get(i).getId() == id) {
				line = i;
			}
		}
		planRepo.deleteById(id);
		List<InjectionPlanModel> newinjectionPlanModels = planRepo.findBydetails(detail);
		List<InjectionPlanModel> list = new ArrayList<InjectionPlanModel>();
		for (int i = 0; i < newinjectionPlanModels.size(); i++) {
			int j = i;
			InjectionPlanModel model = newinjectionPlanModels.get(i);
			if (i == 0) {
				model.setCumprop(model.getStageprop());
			} else {
				Double cumprop = Double.parseDouble(newinjectionPlanModels.get(i).getStageprop())
						+ Double.parseDouble(newinjectionPlanModels.get(--j).getCumprop());
				model.setCumprop(cumprop.toString());
				list.add(model);
			}
		}
		planRepo.saveAll(list);
	}

	public String calcSlurry(String clean, String begin, String end) {
		Double clean1 = Double.parseDouble(clean);
		Double begin1 = Double.parseDouble(begin);
		Double end1 = Double.parseDouble(end);
		Double slurry = 0.0;
		slurry = clean1 * ((((begin1 + end1) / 2) * 0.0456) + 1);
		return slurry.toString();
	}

	public String calcCumSlurry(Integer pid, String slurry) {
		Double slurry1 = Double.parseDouble(slurry);
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<InjectionPlanModel> injectionPlanModels = planRepo.findBydetails(detail);
		if (injectionPlanModels.size() > 0) {
			slurry1 += Double.parseDouble(injectionPlanModels.get(injectionPlanModels.size() - 1).getCumslurry());
		}
		return slurry1.toString();
	}
	
	public String calcumulStepTime(Integer pid, String steptime) {
		Double steptime1 = Double.parseDouble(steptime);
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<InjectionPlanModel> injectionPlanModels = planRepo.findBydetails(detail);
		if (injectionPlanModels.size() > 0) {
			steptime1 += Double.parseDouble(injectionPlanModels.get(injectionPlanModels.size() - 1).getCumStepTime());
		}
		return steptime1.toString();
	}

	public String calcStageProp(String clean, String begin, String end) {
		Double clean1 = Double.parseDouble(clean);
		Double begin1 = Double.parseDouble(begin);
		Double end1 = Double.parseDouble(end);
		Double StageProp = 0.0;
		StageProp = clean1 * 42 * ((begin1 + end1) / 2);
		return StageProp.toString();
	}

	public String calcCumProp(Integer pid, String stageprop) {
		Double prop = Double.parseDouble(stageprop);
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<InjectionPlanModel> injectionPlanModels = planRepo.findBydetails(detail);
		if (injectionPlanModels.size() > 0) {
			prop += Double.parseDouble(injectionPlanModels.get(injectionPlanModels.size() - 1).getCumprop());
		}
		return prop.toString();
	}

	public String calcStepTime(String slurry, String rate) {
		Double rate1 = Double.parseDouble(rate);
		Double slurry1 = Double.parseDouble(slurry);
		Double steptime = 0.0;
		steptime = slurry1 / rate1;
		return steptime.toString();
	}

	public void readFile(Integer pid, MultipartFile file) throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		Row row;
		Cell c;
		int step = 0, rate = 0, ftype = 0, clean = 0, stype = 0, strtprop = 0, endprop = 0;
		List<String> steps = new ArrayList<String>();
		List<String> rates = new ArrayList<String>();
		List<String> ftypes = new ArrayList<String>();
		List<String> cleans = new ArrayList<String>();
		List<String> stypes = new ArrayList<String>();
		List<String> strtprops = new ArrayList<String>();
		List<String> endprops = new ArrayList<String>();
		List<String> slurry = new ArrayList<String>();
		List<String> cumslurry = new ArrayList<String>();
		List<String> prop = new ArrayList<String>();
		List<String> cumprop = new ArrayList<String>();
		List<String> steptime = new ArrayList<String>();
		for (int i = worksheet.getFirstRowNum(); i < worksheet.getLastRowNum(); i++) {
			row = worksheet.getRow(i);
			if (row != null) {
				for (int j = 0; j < row.getLastCellNum(); j++) {
					c = row.getCell(j);
					if (c != null & i == 0) {
						if (c.toString().equalsIgnoreCase("step")) {
							step = j;
						} else if (c.toString().equalsIgnoreCase("Fluid Type")) {
							ftype = j;
						} else if (c.toString().equalsIgnoreCase("Stage Type")) {
							stype = j;
						} else if (c.toString().equalsIgnoreCase("Rate (BPM)")) {
							rate = j;
						} else if (c.toString().equalsIgnoreCase("Clean Vol(BBL)")) {
							clean = j;
						} else if (c.toString().equalsIgnoreCase("Start Prop Conc")) {
							strtprop = j;
						} else if (c.toString().equalsIgnoreCase("End Prop Conc")) {
							endprop = j;
						}
					} else if (c != null) {
						if (j == step && !c.toString().equals("")) {
							steps.add(String.valueOf(c.getNumericCellValue()));
						} else if (j == ftype && !c.toString().equals("")) {
							ftypes.add(c.toString());
						} else if (j == stype && !c.toString().equals("")) {
							stypes.add(c.toString());
						} else if (j == rate && !c.toString().equals("")) {
							rates.add(String.valueOf(c.getNumericCellValue()));
						} else if (j == clean && !c.toString().equals("")) {
							cleans.add(String.valueOf(c.getNumericCellValue()));
						} else if (j == strtprop && !c.toString().equals("")) {
							strtprops.add(String.valueOf(c.getNumericCellValue()));
						} else if (j == endprop && !c.toString().equals("")) {
							endprops.add(String.valueOf(c.getNumericCellValue()));
						}
					}
				}

			}
		}
		workbook.close();
		for (int i = 0; i < cleans.size(); i++) {
			int j = i;
			slurry.add(calcSlurry(cleans.get(i), strtprops.get(i), endprops.get(i)));
			if (i == 0) {
				cumslurry.add(slurry.get(i));
			} else {
				cumslurry.add(
						String.valueOf((Double.parseDouble(slurry.get(i)) + Double.parseDouble(cumslurry.get(--j)))));
			}
		}

		for (int i = 0; i < cleans.size(); i++) {
			int j = i;
			prop.add(calcStageProp(cleans.get(i), strtprops.get(i), endprops.get(i)));
			if (i == 0) {
				cumprop.add(prop.get(i));
			} else {
				cumprop.add(String.valueOf((Double.parseDouble(prop.get(i)) + Double.parseDouble(cumprop.get(--j)))));
			}
		}
		for (int i = 0; i < cleans.size(); i++) {
			steptime.add(calcStepTime(slurry.get(i), rates.get(i)));
		}
		saveByFile(pid, steps, ftypes, stypes, rates, cleans, strtprops, endprops, slurry, cumslurry, prop, cumprop,
				steptime);
	}

	public void saveByFile(Integer pid, List<String> steps, List<String> ftypes, List<String> stypes,
			List<String> rates, List<String> cleans, List<String> strtprops, List<String> endprops, List<String> slurry,
			List<String> cumslurry, List<String> prop, List<String> cumprop, List<String> steptime) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<InjectionPlanModel> list = new ArrayList<>();
		for (int i = 0; i < cleans.size(); i++) {
			InjectionPlanModel model = new InjectionPlanModel();
			model.setBegprop(strtprops.get(i));
			model.setCleanvol(cleans.get(i));
			model.setCumprop(cumprop.get(i));
			model.setCumslurry(cumslurry.get(i));
			model.setEndprop(endprops.get(i));
			model.setFluidtype(ftypes.get(i));
			model.setRate(rates.get(i));
			model.setSlurryvol(slurry.get(i));
			model.setStageprop(prop.get(i));
			model.setStagetype(stypes.get(i));
			model.setStep(steps.get(i));
			model.setSteptime(steptime.get(i));
			model.setDetails(detail);
			list.add(model);
		}
		planRepo.saveAll(list);
	}

}
