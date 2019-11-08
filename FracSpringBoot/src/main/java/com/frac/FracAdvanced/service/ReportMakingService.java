package com.frac.FracAdvanced.service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.FluidLibraryModel;
import com.frac.FracAdvanced.model.InjectionPlanModel;
import com.frac.FracAdvanced.model.ReportMakingModel;
import com.frac.FracAdvanced.repository.FluidLibraryRepo;
import com.frac.FracAdvanced.repository.InjectionPlanRepo;
import com.frac.FracAdvanced.repository.OutputWellForcastRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.ProppantRepo;
import com.frac.FracAdvanced.repository.ReservoirFluidRepo;
import com.frac.FracAdvanced.repository.reportMakingModelRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class ReportMakingService {

	@Autowired
	ReservoirFluidRepo rfr;
	@Autowired
	ReservoirFluidService reservoirFluidService;
	@Autowired
	ProjectDetailRepo pdr;
	@Autowired
	InjectionPlanRepo iplanr;
	@Autowired
	reportMakingModelRepo reportmmr;
	@Autowired
	FluidLibraryRepo fluidLR;
	@Autowired
	ProppantRepo propantr;
	@Autowired
	OutputWellForcastRepo outputWellForcastRepo;
	ReportMakingModel m1 = new ReportMakingModel();

	public void calculate_WidthOfFluidLost(Integer pid) {
		String a1 = rfr.findByParamAndDetails("Leak off Coefficient(ft/min^0.5)", pdr.getOne(pid)).get(0).getValue();// C
		double C = Double.parseDouble(a1) * 0.03936;
		int x = iplanr.findBydetails(pdr.getOne(pid)).size(); /// get size of list
		String s2 = iplanr.findBydetails(pdr.getOne(pid)).get(x - 1).getCumStepTime(); // T
		String hf = rfr.findByParamAndDetails("Fracture Gross Height(feet)", pdr.getOne(pid)).get(0).getValue();//
		String k1 = rfr.findByParamAndDetails("Reservoir Permeability(md)", pdr.getOne(pid)).get(0).getValue();//
		String h = rfr.findByParamAndDetails("Pay Height(feet)", pdr.getOne(pid)).get(0).getValue();//
		String re = rfr.findByParamAndDetails("Effective Radius(feet)", pdr.getOne(pid)).get(0).getValue();//
		String rw = rfr.findByParamAndDetails("Well Bore Radius(feet)", pdr.getOne(pid)).get(0).getValue();//
		String A = rfr.findByParamAndDetails("Drinage Area(Acres)", pdr.getOne(pid)).get(0).getValue();//
		String S = rfr.findByParamAndDetails("Skin Factor", pdr.getOne(pid)).get(0).getValue();//
		String V = rfr.findByParamAndDetails("Poisons Ratio", pdr.getOne(pid)).get(0).getValue();//
		String E1 = rfr.findByParamAndDetails("Young's Modulus(psi)", pdr.getOne(pid)).get(0).getValue();//
		String E = rfr.findByParamAndDetails("Shear Modulus(psi)", pdr.getOne(pid)).get(0).getValue();//
		double T = Double.parseDouble(s2);
		double W = 3 * C * Math.pow(T, 0.5);
		String w1 = Double.toString(W);
		// m1.setParam("widthOfFluidLost");
		// m1.setValue(w1);
		reportmmr.save(m1);
	}


	public Map<String, String> method_fractureWidthAndConductivity(int pid) {
		double LT = 0.0;
		double W = 0.0;
		String SS = "";
		reportmmr.deleteBypid(pdr.getOne(pid));
		reportmmr.flush();
		ArrayList<String> arraylist = new ArrayList<String>();
		Map<String, String> mapList = new HashMap<String, String>();
		Set<String> arraySet = new HashSet<String>();

		List<InjectionPlanModel> x = iplanr.findBydetails(pdr.getOne(pid)); // Rate(BPM) and t
		if (true == x.isEmpty()) {
			mapList.put("No value Entered In Injection Plan Button", "Injection Plan");

		}
		String re1 = rfr.findByParamAndDetails("Effective Radius(feet)", pdr.getOne(pid)).get(0).getValue();//
		if (re1 == null | re1.isEmpty() | re1.matches("^[a-zA-Z]*$")) {
			mapList.put("Effective Radius(feet) From Reservoir Fluid Properties", "Reservoir Fluid");

		}

		String rw1 = rfr.findByParamAndDetails("Well Bore Radius(feet)", pdr.getOne(pid)).get(0).getValue();//
		if (rw1 == null | rw1.isEmpty() | rw1.matches("^[a-zA-Z]*$")) {
			SS = SS + "  Well Bore Radius(feet) From Reservoir Fluid Properties,   ";
			mapList.put("Well Bore Radius(feet) From Reservoir Fluid Properties", "Reservoir Fluid");
		}

		String A1 = rfr.findByParamAndDetails("Drinage Area(Acres)", pdr.getOne(pid)).get(0).getValue();//
		if (A1 == null | A1.isEmpty() | A1.matches("^[a-zA-Z]*$")) {
			SS = SS + "  Drinage Area(Acres) From Reservoir Fluid Properties,  ";
			mapList.put("Drinage Area(Acres) From Reservoir Fluid Properties", "Reservoir Fluid");

		}

		String k1 = rfr.findByParamAndDetails("Reservoir Permeability(md)", pdr.getOne(pid)).get(0).getValue();//
		if (k1 == null | k1.isEmpty() | k1.matches("^[a-zA-Z]*$")) {
			mapList.put("Reservoir Permeability(md) From Reservoir Fluid Properties", "Reservoir Fluid");
		}

		String h1 = rfr.findByParamAndDetails("Fracture Gross Height(feet)", pdr.getOne(pid)).get(0).getValue();//
		if (h1 == null | h1.isEmpty() | h1.matches("^[a-zA-Z]*$")) {
			mapList.put("Fracture Gross Height(feet) From Reservoir Fluid Properties", "Reservoir Fluid");
		}

		String C1 = rfr.findByParamAndDetails("Leak off Coefficient(ft/min^0.5)", pdr.getOne(pid)).get(0).getValue();// C
		if (C1 == null | C1.isEmpty() | C1.matches("^[a-zA-Z]*$")) {
			mapList.put("Leak off Coefficient(ft/min^0.5) From Reservoir Fluid Properties", "Reservoir Fluid");

		}

		String V1 = rfr.findByParamAndDetails("Poisons Ratio", pdr.getOne(pid)).get(0).getValue();//
		if (V1 == null | V1.isEmpty() | V1.matches("^[a-zA-Z]*$")) {
			mapList.put("Poisons Ratio From Reservoir Fluid Properties", "Reservoir Fluid");
		}

		String G1 = rfr.findByParamAndDetails("Shear Modulus(psi)", pdr.getOne(pid)).get(0).getValue();//
		if (G1 == null | G1.isEmpty() | G1.matches("^[a-zA-Z]*$")) {
			mapList.put("Shear Modulus(psi) From Reservoir Fluid Properties", "Reservoir Fluid");
		}

		String u1 = fluidLR.findByProId(pid).get(0).getFluidTypeSelected();
		List<FluidLibraryModel> f12 = fluidLR.findByPidFLAndType(pdr.getOne(pid), u1);
		if (true == f12.isEmpty()) {
			mapList.put("The fluid is not sellected from Fluid Library", "Fluid Library");

		}

		String dp1 = propantr.findByParamAndDetails("Poppant Diameter (Inch)", pdr.getOne(pid)).get(0).getValue();
		if (dp1 == null | dp1.isEmpty() | dp1.matches("^[a-zA-Z]*$")) {
			mapList.put("Poppant Diameter From Poppant Properties", "Poppant Properties");
		}

		String fy1 = propantr.findByParamAndDetails("Packed Porosity", pdr.getOne(pid)).get(0).getValue();
		if (fy1 == null | fy1.isEmpty() | fy1.matches("^[a-zA-Z]*$")) {
			arraylist.add("Packed Porosity From Poppant Properties");
			arraySet.add("Poppant Properties");
			mapList.put("Poppant Diameter From Poppant Properties", "Poppant Properties");

		}

		if (mapList.size() > 0) {
			return mapList;
		}
		double rw = Double.parseDouble(rw1);
		double re = Double.parseDouble(re1);
		double A = Double.parseDouble(A1);
		double h = Double.parseDouble(h1);
		double V = Double.parseDouble(V1);
		double G = Double.parseDouble(G1);
		String u12 = f12.get(0).getValue();
		double u = Double.parseDouble(u12) / 1000;
		double dp = Double.parseDouble(dp1) * 2.54;
		double fy = Double.parseDouble(fy1);
		if (C1.equals("null")) {
			for (int i = 0; i < x.size(); i++) {
				String q1 = x.get(i).getRate();
				double qi = Double.parseDouble(q1) / 377.89;
				String t1 = x.get(i).getCumStepTime();
				double t = Double.parseDouble(t1);
				double LT1 = 0.68 * (Math.pow(((G * Math.pow(qi, 3)) / ((1 - V) * u * Math.pow(h, 4))), (1 / 5)))
						* Math.pow(t, (4 / 5));
				LT = (LT1) / 2;
				LT = LT * 3.28;// feet
				W = 2.5 * (Math.pow((((1 - V) * u * Math.pow(qi, 2)) / G * h), (1 / 5))) * Math.pow(t, (1 / 5));
				W = W * 39.37;/// inch
				double kf = ((Math.pow(dp, 2) * (Math.pow(fy, 3))) / (150 * (Math.pow((1 - fy), 2)))) * (W);

				double pnet = 2.5 * (Math.pow(
						((Math.pow(G, 4) * u * Math.pow(qi, 2)) / (Math.pow((1 - V), 4) * (Math.pow(LT, 6)))), 0.2))
						* Math.pow(t, 0.2);
				pnet = pnet * 0.000145; /// Psi

				ReportMakingModel r1 = new ReportMakingModel();
				r1.setLT(LT);
				r1.setW(Double.toString(W));
				r1.setPid(pdr.getOne(pid));
				r1.setTime(Double.toString(t));
				r1.setConductivity(Double.toString(kf));
				r1.setPnet(Double.toString(pnet));
				reportmmr.save(r1);
			}
			return mapList;
		} else {
			for (int i = 0; i < x.size(); i++) {
				String q1 = x.get(i).getRate();
				double qi = Double.parseDouble(q1) / 377.89;
				String t1 = x.get(i).getCumStepTime();/// min
				double t = Double.parseDouble(t1) * 60;
				// round upto 3 decimal numbers...
				DecimalFormat df = new DecimalFormat("#.###");
				df.setRoundingMode(RoundingMode.CEILING);
				double C = Double.parseDouble(C1);
				double LT1 = (qi * (Math.pow(t, 0.5))) / ((3.14) * C * h);
				LT = (LT1) / 2;/// meter
				LT = LT * 3.28;// feet
				W = 4 * (Math.pow(((2 * (1 - V) * u * (Math.pow(qi, 2))) / ((Math.pow(Math.PI, 3)) * G * C * h)), 0.25))
						* (Math.pow(t, 0.125)); /// width in meter
				W = W * 39.37;/// inch
				double wc = W * 0.0833;
				double kf1 = ((Math.pow(dp, 2) * (Math.pow(fy, 3))) / (150 * (Math.pow((1 - fy), 2))));
				double kf2 = kf1 * 101325000000.0;

				double kf = kf2 * (wc);// md-feet
				double k = Double.parseDouble(k1);
				double fcd = (kf2 * W) / (k * LT);

				double pnet = 0.000145 * 2.5 * (Math
						.pow(((Math.pow(G, 4) * u * Math.pow(qi, 2)) / (Math.pow((1 - V), 4) * (Math.pow(h, 6)))), 0.2))
						* Math.pow(t, 0.2);// pascal

				// if else conditions for foi
				Double shubham = 0.0;

				if (re1.equalsIgnoreCase(null)) {

					re = Math.pow((A / Math.PI), 0.5);
					double foi = (Math.log10(re / rw)) / ((Math.log10(re / rw)) + shubham);

				} else {
					double foi = (Math.log10(re / rw)) / ((Math.log10(re / rw)) + shubham);

				}
				ReportMakingModel r1 = new ReportMakingModel();
				r1.setLT(LT);
				r1.setW(Double.toString(W));
				r1.setPid(pdr.getOne(pid));
				r1.setTime(Double.toString(t));
				r1.setConductivity(Double.toString(kf));
				r1.setPnet(Double.toString(pnet));
				reportmmr.save(r1);

			}
			return mapList;
		}

	}

	public List<ReportMakingModel> getVlaueOfReport(Integer pid) {
		List<ReportMakingModel> a1 = reportmmr.findBypid(pdr.getOne(pid));
		return a1;
	}
}
