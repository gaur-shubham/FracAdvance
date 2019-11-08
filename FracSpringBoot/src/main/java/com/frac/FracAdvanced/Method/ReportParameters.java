package com.frac.FracAdvanced.Method;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frac.FracAdvanced.model.InjectionPlanModel;
import com.frac.FracAdvanced.model.PhasingParamModel;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ReportMakingModel;
import com.frac.FracAdvanced.model.ReportParamModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.service.InjectionPlanService;
import com.frac.FracAdvanced.service.PhasingParamService;
import com.frac.FracAdvanced.service.ReportMakingService;
import com.frac.FracAdvanced.service.ReportParamService;

/**
 * @author ShubhamGaur
 *
 */
@Component
public class ReportParameters {

	@Autowired
	private ErrorFunction errorFunction;
	@Autowired
	private ReportParamService paramService;
	@Autowired
	private PhasingParamService phasingParamService;
	@Autowired
	private InjectionPlanService planService;
	@Autowired
	private ReportMakingService makingService;

	public void SaveParam(Integer pid) {
		paramService.saveReportParam(pid, Formulae1());
		paramService.saveReportParam1(pid, Formulae2(getPhasingDefault(pid)));
		paramService.saveReportParam2(pid, calcFromInjection(pid));
		paramService.saveReportParam3(pid, FractureMaxValue(pid));
		
	}

	public Map<String, String> Formulae1() {
		Map<String, String> map = new LinkedHashMap<>();
		Double widthFL = 0.0, leakCoeff = 0.00025, injectTime = 132.0;
		Double fracArea = 0.0, totalVol = 0.0, fracWidth = 0.4;
		Double volumeFL = 0.0;
		Double volInject = 110880.0, volPad = 0.0, volSand = 0.0;
		Double sandMass = 700000.0;
		Double FracEfficiency = 0.0, FracEffPercent = 0.0;
		Double exponent = 0.0;
		Double halfLength = 0.0, fx = 0.0, x = 0.0;
		Double flowRate = 20.0, fracHeight = 300.0;
		Double pie = 3.14;
		Double propWidth = 0.0, maxWidth = 0.0, shearMod = 2114583.333, maxPropConc = 8.0,avgWidth=0.0;
		Double propLength = 0.0;
		Double avgVisco = 0.0, consistencyIndex = 0.03, powerLawIndex = 0.52;
		Double effecFracHeight=0.0;
		Double volumeFLbarrel=0.0,totalVolbarrel=0.0;
		Double widthErrorPercent=0.0,heightErrorPercent=0.0;
		widthFL = 3 * leakCoeff * Math.pow(injectTime, 0.5);
		fracArea = (volInject / 7.48) / ((fracWidth / 12) + widthFL);
		volumeFL = fracArea * widthFL * 7.48;
		volumeFLbarrel=volumeFL/42;
		FracEfficiency = (fracWidth / 12) / (widthFL + (fracWidth / 12));
		FracEffPercent = FracEfficiency * 100;
		exponent = (1 - FracEfficiency) / (1 + FracEfficiency);
		volPad = volInject * exponent;
		volSand = sandMass / 22.1;
		totalVol = volInject + volPad + volSand;
		totalVolbarrel=totalVol/42;
		x = 6.57 * ((leakCoeff / fracWidth) * (Math.sqrt(totalVol / flowRate)));
		fx = (Math.exp(Math.pow(x, 2)) * (1-errorFunction.erf(x))) + ((2 * x) / Math.sqrt(pie)) - 1;
		halfLength = ((0.037 * fracWidth * flowRate) / (2 * fracHeight * Math.pow(leakCoeff, 2))) * (fx);
		avgVisco = 47880 * consistencyIndex
				* Math.pow((40.48 * (flowRate / (Math.pow(fracWidth, 2) * fracHeight))), (powerLawIndex - 1));
		maxWidth = 0.272
				* (Math.pow((((flowRate * avgVisco * Math.pow(halfLength, 2)) / (shearMod * fracHeight))), 0.25));
		avgWidth=0.785*maxWidth;
		effecFracHeight=(2*halfLength)/4;
		propWidth = 0.0754 * maxWidth * (maxPropConc / ((0.045 * maxPropConc) + 1));
		propLength = (0.0605 * sandMass) / (propWidth * fracHeight);
		
		widthErrorPercent=((avgWidth-fracWidth)/fracWidth)*100;
		heightErrorPercent=((effecFracHeight-fracHeight)/fracHeight)*100;
		map.put("Fracture Area(ft2)", fracArea.toString());
		map.put("Volume Of Fluid Loss(bbl)", volumeFLbarrel.toString());
		map.put("Fracture Efficiency(%)", FracEffPercent.toString());
		map.put("Total Injection Volume(bbl)", totalVolbarrel.toString());
		map.put("Effective Half Length(ft)", halfLength.toString());
		map.put("Propped Width(Inch)", propWidth.toString());
		map.put("Propped Length(ft)", propLength.toString());
		map.put("Average Width(Inch)", avgWidth.toString());
		map.put("Average Viscosity(cp)", avgVisco.toString());
		map.put("Effective Fracture Height(ft)", effecFracHeight.toString());
		map.put("Fracture Width Error %", widthErrorPercent.toString());
		map.put("Fracture Height Error %", heightErrorPercent.toString());
		
		return map;
	}

	public List<String> getPhasingDefault(Integer pid) {
		List<PhasingParamModel> list1 = phasingParamService.showPhasing(pid);
		List<String> list = new ArrayList<String>();
		Double angle = 180.0; // angle is User Input
		PhasingParamModel model = new PhasingParamModel();
		for (int i = 0; i < list1.size(); i++) {
			if (Double.parseDouble(list1.get(i).getAngle()) == angle) {
				model = list1.get(i);
			}
		}
		list.add(angle.toString());
		list.add(model.getAlpha());
		list.add(model.getA1());
		list.add(model.getA2());
		list.add(model.getB1());
		list.add(model.getB2());
		list.add(model.getC1());
		list.add(model.getC2());
		return list;
	}

	public Map<String, String> Formulae2(List<String> value) {

		Map<String, String> map = new LinkedHashMap<>();
		Double angle = 0.0, alpha = 0.0, a1 = 0.0, a2 = 0.0, b1 = 0.0, b2 = 0.0, c1 = 0.0, c2 = 0.0;
		int i = 0;
		if(value.size()>0) {
		angle = Double.parseDouble(value.get(i++));
		alpha = Double.parseDouble(value.get(i++));
		a1 = Double.parseDouble(value.get(i++));
		a2 = Double.parseDouble(value.get(i++));
		b1 = Double.parseDouble(value.get(i++));
		b2 = Double.parseDouble(value.get(i++));
		c1 = Double.parseDouble(value.get(i++));
		c2 = Double.parseDouble(value.get(i));
		}
		Double perfSkin = 0.0, horizontalSkin = 0.0, verticalSkin = 0.0, dimenQuantity = 0.0;
		Double effectiveWellboreRad = 0.0, perfLen = 0.666667, wellboreRad = 0.328;
		Double dimenRad = 0.0, perfRad = 0.020833, a = 0.0, b = 0.0;
		Double dimenHeight = 0.0, perfHeight = 0.5, permRatio = 10.0;
		Double dimenWellboreRad = 0.0;
		Double perfFriction=0.0,flowRate=20.0,fluidDensity=9.97464,perfNo=30.0,perfDia=0.375,dischargeCoeff=0.0,visco=99.79853;
		// Calculating Horizontal Skin
		if (angle == 0) {
			effectiveWellboreRad = perfLen / 4;
		} else {
			effectiveWellboreRad = alpha * (wellboreRad + perfLen);
		}
		horizontalSkin = Math.log(wellboreRad / effectiveWellboreRad);
		// Calculating Vertical Skin
		dimenRad = (perfRad * (1 + Math.sqrt((1 / permRatio)))) / (2 * perfHeight);
		a = (a1 * Math.log10(dimenRad)) + a2;
		b = (b1 * dimenRad) + b2;
		dimenHeight = (perfHeight / perfLen) * (Math.sqrt(permRatio));
		verticalSkin = (Math.pow(10, a)) * (Math.pow(dimenHeight, b - 1)) * (Math.pow(dimenRad, b));
		// Calculating Dimensionless Quantity
		dimenWellboreRad = wellboreRad / (perfLen + wellboreRad);
		dimenQuantity = c1 * (Math.exp(c2 * dimenWellboreRad));
		// Calculating Perforation Skin
		perfSkin = horizontalSkin + verticalSkin + dimenQuantity;
		map.put("Perforation Skin", perfSkin.toString());
		dischargeCoeff=Math.pow((1-(Math.exp((-2.2*perfDia)/(Math.pow(visco, 0.1))))), 0.4);
		perfFriction=(0.2369*Math.pow(flowRate, 2)*fluidDensity)/(Math.pow(perfNo,2)*Math.pow(perfDia,4)*Math.pow(dischargeCoeff,2));
		map.put("Perforation Pressure Drop(psi)", perfFriction.toString());
		return map;
	}
	
	public Map<String,String> calcFromInjection(Integer pid) {
		List<InjectionPlanModel> list = planService.showInjectionPlan(pid);
		Double totGel=0.0,totPad=0.0,totCrossLink=0.0,maxppg=0.0;
		Double avgppg=0.0;
		Map<String,String> map=new LinkedHashMap<>();
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getFluidtype().equalsIgnoreCase("cross link")) {
				totCrossLink+=Double.parseDouble(list.get(i).getCleanvol());
				if(list.get(i).getStagetype().equalsIgnoreCase("pad")) {
					totPad+=Double.parseDouble(list.get(i).getCleanvol());
				}
			}else if(list.get(i).getFluidtype().equalsIgnoreCase("linear gel")) {
				totGel+=Double.parseDouble(list.get(i).getCleanvol());
				if(list.get(i).getStagetype().equalsIgnoreCase("pad")) {
					totPad+=Double.parseDouble(list.get(i).getCleanvol());
				}
			}else if(list.get(i).getStagetype().equalsIgnoreCase("pad")) {
				totPad+=Double.parseDouble(list.get(i).getCleanvol());
			}
			avgppg=(Double.parseDouble(list.get(i).getBegprop())+Double.parseDouble(list.get(i).getEndprop()))/2.0;
			if(maxppg<avgppg) {
				maxppg=avgppg;
			}
		}
		map.put("Total Pad Volume(bbl)", totPad.toString());
		map.put("Total Gel Volume(bbl)", totGel.toString());
		map.put("Total Cross link Volume(bbl)", totCrossLink.toString());
		map.put("Max Proppant Concentration(ppg)", maxppg.toString());
		return map;
	}
	
	public Map<String,String> FractureMaxValue(Integer pid) {
		List<ReportMakingModel> list = makingService.getVlaueOfReport(pid);
		Double maxFracLen=0.0,avgFracWidth=0.0,endNetPress=0.0;
		Map<String,String> map=new LinkedHashMap<>();
		if(list.size()>0){
		maxFracLen=list.get(list.size()-1).getLT();
		endNetPress=Double.parseDouble(list.get(list.size()-1).getPnet());
		}
		Double widthSum=0.0;
		for(int i=0;i<list.size();i++) {
			widthSum+=Double.parseDouble(list.get(i).getW());
		}
		avgFracWidth=widthSum/list.size();
		map.put("Max Created Fracture Length(ft)",maxFracLen.toString());
		map.put("Average Fracture Width(Inch)",avgFracWidth.toString());
		map.put("Net Pressure At End Of Pumping(psi)",endNetPress.toString());
		return map;
	}
	
	public void errorPercent() {
		
	}

}
