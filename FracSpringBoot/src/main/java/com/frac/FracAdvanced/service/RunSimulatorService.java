/**
 * 
 */
package com.frac.FracAdvanced.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.Method.MainFracGraph;
import com.frac.FracAdvanced.Method.MiniFracAlgo;
import com.frac.FracAdvanced.Method.ReportParameters;
import com.frac.FracAdvanced.Method.StressAnalysisAlgo;
import com.frac.FracAdvanced.Method.WriteReadFile;
import com.frac.FracAdvanced.repository.OutputMiniFracRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class RunSimulatorService {

	@Autowired
	private OutputMiniFracRepo output;
	@Autowired
	private MainFracGraph maingraph;
	@Autowired
	private ReportParameters report;
	@Autowired
	private ReportMakingService reportms;
	@Autowired
	private WriteReadFile writeReadFile;
	@Autowired
	private MiniFracAlgo miniFracAlgo;
	@Autowired
	private StressAnalysisAlgo stressAnalysisAlgo;
	
	public String createInput(Integer pid) throws Exception{
		if (!output.findByProId(pid).isEmpty()) {
			output.deleteByProId(pid);
		}
		writeReadFile.createMinifracInputFile(pid);
		writeReadFile.createStressInputFile(pid);
		return "success1";
	}
	
	public String calcOutput(Integer pid) throws Exception{
		miniFracAlgo.calculate(writeReadFile.readInputFile("minifrac"), pid);
		stressAnalysisAlgo.calculate(writeReadFile.readInputFile("stressanalysis"), pid);
		return "success2";
	}
	
	public String saveOutput(Integer pid) {
		maingraph.SaveMainFrac(pid);
		reportms.method_fractureWidthAndConductivity(pid);
		report.SaveParam(pid);
		return "success3";
	}

}
