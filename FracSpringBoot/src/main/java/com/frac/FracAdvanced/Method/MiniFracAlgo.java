package com.frac.FracAdvanced.Method;

import java.io.BufferedWriter; 
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frac.FracAdvanced.model.OutputMiniFrac;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.repository.OutputMiniFracRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;


/**
 * @author ShubhamGaur
 *
 */
@Component
public class MiniFracAlgo {
	
	@Autowired
	private  OutputMiniFracRepo service;
	@Autowired
	private  ProjectDetailRepo prodetails;
	@Autowired
	private  HttpSession session;
	
	
public void calculate(Map<String, String> inputfilemap,int pId) throws Exception {
        
        String alpha = "low leak";
        double gtd = 0.0;
        double Gtd = 0.0;
        double td = 0.0;
        double t = 0.0;
        double p = 0.0;
        double tp = 0.0;
        double g0 = 0.0;
        double dpdG = 0.0;
        double dp = 0.0;
        double dG = 0.0;
        double dp1 = 0.0;
        double dG1 = 0.0;
        double GdpdG=0.0;
        DecimalFormat df = new DecimalFormat("#.################");
        Map<String, String> Gtdmap = new LinkedHashMap<>();
        Map<String,String> dpdGmap=new LinkedHashMap<>();
        Map<String,String> GdpdGmap=new LinkedHashMap<>();
        for (int i = 0; i < inputfilemap.size() / 2; i++) {
            String key = "DURATION" + i;
            String key1 = "PRESSURE" + i;
            String key3="PUMPINGTIME";
            String Gtdm = "Gtd" + i;
            p = Double.parseDouble(inputfilemap.get(key1));
            t = Double.parseDouble(inputfilemap.get(key));
            tp = Double.parseDouble(inputfilemap.get(key3));
            
            td = (t - tp) / tp;
            if (alpha.equalsIgnoreCase("low leak")) {
                g0 = 1.3333333333333333;
                /*************Calculating g(td) ****************/
                gtd = ((4 * ((Math.pow((1 + td), 1.5)) - (Math.pow(td, 1.5))))/3);
                /*************Calculating G(td) ****************/
                Gtd = ((4 *(gtd - g0))/ (3.14));
                String Gtds = String.valueOf(Gtd);
                Gtdmap.put(Gtdm, Gtds);

            } else if (alpha.equalsIgnoreCase("high leak")) {
                g0 = Math.PI / 2;
                

            }
        }
        for (int i = 0; i < inputfilemap.size() / 2; i++) {
            if (i != (inputfilemap.size() / 2)-1) {
                String key1 = "Gtd" + i;
                String key2 = "PRESSURE" + i;
                String key3 = "PRESSURE" + (i + 1);
                String key4 = "Gtd" + (i + 1);
                String dpdgm="dpdG"+i;
                String Gdpdgm="GdpdG"+i;
                dp = Double.parseDouble(inputfilemap.get(key2));
                dp1 = Double.parseDouble(inputfilemap.get(key3));
                dG = Double.parseDouble(Gtdmap.get(key1));
                dG1 = Double.parseDouble(Gtdmap.get(key4));
                double dpd=dp-dp1;
                double dgd=dG1-dG;
                dpdG=dpd/dgd;
                
                String dpdGs=String.valueOf(dpdG);
                dpdGmap.put(dpdgm, dpdGs);
                GdpdG=dpdG*dG1;
                
                String GdpdGs=String.valueOf(GdpdG);
                GdpdGmap.put(Gdpdgm, GdpdGs);
            } else {
                break;
            }
        }
        
                                    /***************Saving Output Into Database.*********/
        
        String path=session.getServletContext().getRealPath("/");
        String filename="/InputFile/OutputFile.txt";
        Files.deleteIfExists(Paths.get(path + filename));
        FileWriter write=new FileWriter(path+filename);
		BufferedWriter buffer=new BufferedWriter(write);
		buffer.write("");
		List<OutputMiniFrac> outputlist=new ArrayList<>();
		for(int i=0;i<Gtdmap.size()-1;i++) {
			OutputMiniFrac outmini=new OutputMiniFrac();
			
			String key1="Gtd"+i;
			String value1=Gtdmap.get(key1);
			String key2="dpdG"+i;
			String value2=dpdGmap.get(key2);
			String key3="GdpdG"+i;
			String value3=GdpdGmap.get(key3);
			
			buffer.newLine();
			outmini.setGtd(value1);
			buffer.write(key1+"="+value1);
			buffer.newLine();
			
			outmini.setDpdG(value2);
			buffer.write(key2+"="+value2);
			buffer.newLine();
			
			outmini.setGdpdG(value3);
			buffer.write(key3+"="+value3);
			outputlist.add(outmini);
			
			Optional<ProjectDetails> detail= prodetails.findById(pId);
			outmini.setProdetails(detail.get());
			//minifracList.add(mini);
		}
		service.saveAll(outputlist);
		buffer.close();
    }
}
