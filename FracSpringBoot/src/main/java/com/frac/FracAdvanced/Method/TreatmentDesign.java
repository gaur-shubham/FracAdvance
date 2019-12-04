/**
 * 
 */
package com.frac.FracAdvanced.Method;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

/**
 * @author ShubhamGaur
 *
 */
@Component
public class TreatmentDesign {
	// using Newton Raphson method for treatment design

	public void newtonRaphsonMethod() {

		DecimalFormat df = new DecimalFormat("#.######");
		df.setRoundingMode(RoundingMode.CEILING);
		DecimalFormat df1 = new DecimalFormat("#.###");
		df1.setRoundingMode(RoundingMode.CEILING);

		// Constant
		Double pi = 3.14;
		Double k = 1.56;

 		// User Inputs
		Double payzoneThick = 100.0;
		Double youngMod = 5075000.0;
		Double poisionRatio = 0.2;
		Double fluidVisco = 100.0;
		Double leakOffCoeff = 0.0025;
		Double proppDens = 193.44;
		Double proppPoro = 0.35;
		Double fracHalfLen = 606.0;
		Double fluidInjecRate = 20.0;
		Double shearMod = 2114583.0 ;
		Double FracHieght = 300.0;
		Double macProppConc = 8.0;
		Double wellRad = 0.42;
		Double skin = -5.59;
		Double effecWellRad = 112.449;
		Double proppMass = 13596.0;
		Double rp = 0.495049505;

		// Formulae OR inputs used in formulae
		Double avgWidth = 0.0;
		Double fracArea = 0.0;
		Double ft = 0.0; // f(t)
		Double derFt = 0.0; // f(t') --> derivative
		Double t = 60.0;
		
		Double InjectionVol = 0.0;
		Double fracVol = 0.0;
		Double padVol = 0.0;
		Double efficiency = 0.0;
		Double kl = 0.0;

		// average avgWidth in Inch
		avgWidth = 0.3
				* (Math.pow(((fluidInjecRate * fluidVisco * (1 - poisionRatio) * fracHalfLen) / (shearMod)), 0.25))
				* (0.59);
		fracArea = 2 * fracHalfLen * FracHieght;
		// average avgWidth in Meter
		avgWidth = avgWidth/12;
		// RoundedOff AvgWidth(m)
		Double roundAvgWidth = Double.valueOf(df.format(avgWidth));

		System.out.println("avg width>>" + avgWidth);
		System.out.println("avg temp>>" + roundAvgWidth);
		System.out.println("frac area>>" + fracArea);
		System.out.println("kl>>" + kl);
		System.out.println("k>>" + k);
		System.out.println("t>>" + t);
		System.out.println("''' "+!df1.format(kl).equals(df1.format(k)));
		
		while (!df1.format(kl).equals(df1.format(k))) {
			Double temp = 0.0;
			System.out.println("''' "+!df1.format(kl).equals(df1.format(k)));
			System.out.println("--------"+!df1.format(temp).equals(df1.format(t)));
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			while (!df1.format(temp).equals(df1.format(t))) {
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("inside while");
				temp = t;
				ft = (-fluidInjecRate * t) + (avgWidth * fracArea)
						+ (2 * k * leakOffCoeff * fracArea * rp * Math.pow(t, 0.5));
				derFt = -fluidInjecRate + ((k * leakOffCoeff * avgWidth * rp) / (Math.pow(t, 0.5)));
				t = t - (ft / derFt);

				System.out.println("temp>" + temp);
				System.out.println("ft>" + ft);
				System.out.println("der>" + derFt);
				System.out.println("t>" + t);
				System.out.println("^^^" + df1.format(temp) + "==" + df1.format(t));
			}
		 	InjectionVol = fluidInjecRate * t;
			fracVol = fracArea * avgWidth;
			efficiency = fracVol / InjectionVol;
			kl = 0.5 * (((8 / 3) * efficiency) + (pi * (1 - efficiency)));
			if (kl < 1.57 && kl > 1.46) {
				break;
			}
		}
		
		padVol = InjectionVol * ((1 - efficiency) / (1 + efficiency));
		System.out.println("InjectionVol>>" + InjectionVol);
		System.out.println("fracVol>>" + fracVol);
		System.out.println("efficiency>>" + efficiency);
		System.out.println("kl>>" + kl);
		System.out.println("pad Vol>> " + padVol);
	}

}
