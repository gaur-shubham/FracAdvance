/**
 * 
 */
package com.frac.FracAdvanced.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.Method.CreateDefaultData;
import com.frac.FracAdvanced.model.NonDarcyModel;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ProppantModel;
import com.frac.FracAdvanced.model.ReservoirFluidModel;
import com.frac.FracAdvanced.repository.NonDarcyRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.ProppantRepo;
import com.frac.FracAdvanced.repository.ReservoirFluidRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class ProppantService {

	@Autowired
	ProjectDetailRepo detailRepo;
	@Autowired
	ProppantRepo proppantRepo;
	@Autowired
	HttpSession session;
	@Autowired
	CreateDefaultData dataRepo;
	@Autowired
	ReservoirFluidRepo rfr;
	@Autowired
	NonDarcyRepo darcyRepo;

	public List<ProppantModel> showList(Integer pid) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		return proppantRepo.findBydetails(detail);
	}

	public void setProppant(Integer pid, String type) throws Exception {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ProppantModel> list = proppantRepo.findBydetails(details);
		String path = session.getServletContext().getRealPath("/");
		File file = new File(path + "/DefaultData/DefaultData.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		String db = "";
		int line = 0;
		List<String> param = new ArrayList<>();
		List<String> param1 = new ArrayList<>();
		List<String> value = new ArrayList<>();
		List<String> value1 = new ArrayList<>();
		while ((st = br.readLine()) != null) {
			if (line == 0) {
				db += st;
				line++;
			} else {
				if (!(st.startsWith("*") || st.startsWith("//"))) {
					int index = st.indexOf("=");
					param.add(st.substring(0, index).trim());
					value.add(st.substring(index + 1).trim());
					line++;
				} else if ((st.startsWith("//"))) {
					if (db.replaceAll("\\s+", "").equalsIgnoreCase("proppantproperties")) {
						for (int i = param.indexOf(type); i < param.indexOf(type) + 12; i++) {
							param1.add(param.get(i));
							value1.add(value.get(i));
						}
					}
					db = "";
					line = 0;
					param.clear();
					value.clear();
				}

			}
		}
		List<ProppantModel> list1 = new ArrayList<ProppantModel>();
		for (int i = 0; i < list.size(); i++) {
			ProppantModel model = list.get(i);
			model.setParam(param1.get(i));
			model.setValue(value1.get(i));
			list1.add(model);
		}
		proppantRepo.saveAll(list1);
		br.close();
	}

	public void EditProppData(Integer pid, List<String> input) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ProppantModel> list = proppantRepo.findBydetails(details);
		List<ProppantModel> list1 = new ArrayList<ProppantModel>();
		int j = 0;
		for (int i = 1; i < list.size(); i++) {
			ProppantModel model = list.get(i);
			model.setValue(input.get(j));
			list1.add(model);
			j++;
		}
		proppantRepo.saveAll(list1);
	}

	public String getWellTypeFromReservoirFluidMethod(int pid) {

		ProjectDetails p1 = detailRepo.getOne(pid);
		List<ReservoirFluidModel> l1 = rfr.findBydetails(p1);
		String welltype = l1.get(0).getWellTypeSelected();
		return welltype;
	}

	public List<String> darcyEqList() {
		List<String> list = new ArrayList<String>();
		list.add("Belhaj et al");
		list.add("Cole and Hartman");
		list.add("Cooke_Sand 8/12");
		list.add("Cooke_Sand 10/20");
		list.add("Cooke_Sand 20/40");
		list.add("Cooke_Sand 40/60");
		list.add("Ergun");
		list.add("Frederick and Graves");
		list.add("Geertsma");
		list.add("Janice and Katz");
		list.add("Jones");
		list.add("Kutasov");
		list.add("Li et al");
		list.add("Mac Donal et al");
		list.add("Maloney et al");
		list.add("Martins et al");
		list.add("Penny and Jin_Bauxite 20/40");
		list.add("Penny and Jin_LWC 20/40");
		list.add("Penny and Jin_RCS 20/40");
		list.add("Penny and Jin_RCS 20/40");
		list.add("Pursell et al_12/20");
		list.add("Pursell et al_20/40");
		list.add("Tek et al");
		list.add("Thauvin and Mohanty");
		return list;
	}

	public void savekfAndBeta(Integer pid, String equation) {
		Map<String, String> map = calcKfAndBeta(pid, equation);
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<NonDarcyModel> list1 = darcyRepo.findBydetails(details);
		List<NonDarcyModel> list = new ArrayList<>();
		Integer i = 0, j = 0;
		NonDarcyModel darcyModel = new NonDarcyModel();
		for (String key : map.keySet()) {
			String value = map.get(key);
			if (list1.isEmpty()) {
				darcyModel = new NonDarcyModel();
				if (j == 0) {
					NonDarcyModel darcyModel1 = new NonDarcyModel();
					darcyModel1.setParam("Equation");
					darcyModel1.setValue(equation);
					darcyModel1.setDetails(details);
					list.add(darcyModel1);
					j++;
				}
				darcyModel.setDetails(details);
				darcyModel.setParam(key);
				darcyModel.setValue(value);
				list.add(darcyModel);
			} else {
				darcyModel = list1.get(i);
				if (i == 0) {
					darcyModel.setValue(equation);
					i++;
					list.add(darcyModel);
					NonDarcyModel darcyModel1 = list1.get(i);
					darcyModel1.setValue(value);
					i++;
					list.add(darcyModel);
				} else {
					darcyModel.setValue(value);
					i++;
				}
				list.add(darcyModel);
			}
		}
		darcyRepo.saveAll(list);
	}

	public String showEquation(Integer pid) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<NonDarcyModel> list = darcyRepo.findBydetails(details);
		String equation = "";
		if (!list.isEmpty()) {
			equation = list.get(0).getValue();
		}
		return equation;
	}

	public List<NonDarcyModel> showDarcy(Integer pid) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<NonDarcyModel> list = darcyRepo.findBydetails(details);
		List<NonDarcyModel> list1 = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).getParam().equalsIgnoreCase("Beta")
					&& !list.get(i).getParam().equalsIgnoreCase("equation")) {
				list1.add(list.get(i));
			}
		}
		return list1;
	}

	public Map<String, String> calcKfAndBeta(Integer pid, String equation) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ProppantModel> list = proppantRepo.findBydetails(details);
		Map<String, String> map = new LinkedHashMap<>();
		Double a = 0.0, b = 0.0, c = 0.0, kf = 0.0, porosity = 0.0, diameter = 0.0, beta = 0.0,x=101325000000.0;
		switch (equation) {
		case "Belhaj et al":
			a = 3.51 * Math.pow(10, 6);
			b = 1.00;
			c = 1.00;
			break;
		case "Cole and Hartman":
			a = 2.49 * Math.pow(10, 11);
			b = 1.79;
			c = -0.54;
			break;
		case "Cooke_Sand 8/12":
			a = 5.38 * Math.pow(10, 11);
			b = 1.24;
			c = 0.00;
			break;
		case "Cooke_Sand 10/20":
			a = 8.51 * Math.pow(10, 11);
			b = 1.34;
			c = 0.00;
			break;
		case "Cooke_Sand 20/40":
			a = 3.41 * Math.pow(10, 12);
			b = 1.54;
			c = 0.00;
			break;
		case "Cooke_Sand 40/60":
			a = 2.14 * Math.pow(10, 12);
			b = 1.60;
			c = 0.00;
			break;
		case "Ergun":
			a = 1.29 * Math.pow(10, 4);
			b = 0.50;
			c = 1.50;
			break;
		case "Frederick and Graves":
			a = 1.98 * Math.pow(10, 11);
			b = 1.64;
			c = 0.00;
			break;
		case "Geertsma":
			a = 4.85 * Math.pow(10, 5);
			b = 0.50;
			c = 5.50;
			break;
		case "Janice and Katz":
			a = 5.55 * Math.pow(10, 7);
			b = 1.25;
			c = 0.75;
			break;
		case "Jones":
			a = 6.15 * Math.pow(10, 10);
			b = 1.55;
			c = 0.00;
			break;
		case "Kutasov":
			a = 1.38 * Math.pow(10, 6);
			b = 0.50;
			c = 1.50;
			break;
		case "Li et al":
			a = 4.22 * Math.pow(10, 8);
			b = 0.85;
			c = 1.15;
			break;
		case "Mac Donal et al":
			a = 1.38 * Math.pow(10, 4);
			b = 0.50;
			c = 1.50;
			break;
		case "Maloney et al":
			a = 1.16 * Math.pow(10, 4);
			b = 0.50;
			c = 7.10;
			break;
		case "Martins et al":
			a = 8.32 * Math.pow(10, 9);
			b = 1.04;
			c = 0.00;
			break;
		case "Penny and Jin_Bauxite 20/40":
			a = 2.69 * Math.pow(10, 9);
			b = 0.98;
			c = 0.00;
			break;
		case "Penny and Jin_LWC 20/40":
			a = 1.22 * Math.pow(10, 11);
			b = 1.25;
			c = 0.00;
			break;
		case "Penny and Jin_RCS 20/40":
			a = 3.47 * Math.pow(10, 11);
			b = 1.35;
			c = 0.00;
			break;
		case "Penny and Jin_Sand 20/40":
			a = 5.19 * Math.pow(10, 11);
			b = 1.45;
			c = 0.00;
			break;
		case "Pursell et al_12/20":
			a = 5.30 * Math.pow(10, 10);
			b = 1.14;
			c = 0.00;
			break;
		case "Pursell et al_20/40":
			a = 2.35 * Math.pow(10, 10);
			b = 1.12;
			c = 0.00;
			break;
		case "Tek et al":
			a = 1.68 * Math.pow(10, 9);
			b = 1.25;
			c = 0.75;
			break;
		case "Thauvin and Mohanty":
			a = 7.62 * Math.pow(10, 9);
			b = 1.00;
			c = 0.00;
			break;
		default:
			break;
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getParam().equalsIgnoreCase("Poppant Diameter (Inch)")) {
				diameter = Double.parseDouble(list.get(i).getValue());
			} else if (list.get(i).getParam().equalsIgnoreCase("Packed Porosity")) {
				porosity = Double.parseDouble(list.get(i).getValue());
			}
		}
		kf = (Math.pow((diameter*2.54), 2) * Math.pow(porosity, 3)) / (150 * (Math.pow((1 - porosity), 2)));
		kf=kf*x; 
		beta = a / (Math.pow(kf, b) * Math.pow(porosity, b));
		map.put("A", a.toString());
		map.put("B", b.toString());
		map.put("C", c.toString());
		map.put("Kf", kf.toString());
		map.put("Beta", beta.toString());
		return map;
	}
}
