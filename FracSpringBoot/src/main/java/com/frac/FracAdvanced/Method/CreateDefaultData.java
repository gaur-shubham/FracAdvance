/**
 * 
 */
package com.frac.FracAdvanced.Method;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.FluidLibraryModel;
import com.frac.FracAdvanced.model.InjectedFluid1Model;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ProppantModel;
import com.frac.FracAdvanced.model.PumpingModel;
import com.frac.FracAdvanced.model.ReservoirFluidModel;
import com.frac.FracAdvanced.model.RockPropertiesModel;
import com.frac.FracAdvanced.model.WellDataModel;
import com.frac.FracAdvanced.model.WellForcastDeclinedCurveModel;
import com.frac.FracAdvanced.model.WellForcastModel;
import com.frac.FracAdvanced.repository.FluidLibraryRepo;
import com.frac.FracAdvanced.repository.InjectedFluid1Repo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.ProppantRepo;
import com.frac.FracAdvanced.repository.PumpingRepo;
import com.frac.FracAdvanced.repository.ReservoirFluidRepo;
import com.frac.FracAdvanced.repository.RockPropertiesRepo;
import com.frac.FracAdvanced.repository.WellDataRepo;
import com.frac.FracAdvanced.repository.WellForcastDeclinedCurveRepo;
import com.frac.FracAdvanced.repository.WellForcastRepo;

/**
 * @author ShubhamGaur
 *
 */

@Service
public class CreateDefaultData {

	@Autowired
	private HttpSession session;
	@Autowired
	private WellDataRepo wellrepo;
	@Autowired
	private InjectedFluid1Repo injectedrepo;
	@Autowired
	private ProjectDetailRepo detailRepo;
	@Autowired
	private PumpingRepo pumprepo;
	@Autowired
	private RockPropertiesRepo rockrepo;
	@Autowired
	private ReservoirFluidRepo fluidRepo;
	@Autowired
	private ProppantRepo proppantRepo;
	@Autowired
	private  WellForcastRepo forcastRepo;
	@Autowired
	private FluidLibraryRepo flr;
	@Autowired
	private WellForcastDeclinedCurveRepo curveRepo;

	public void setDefault(Integer pid) throws Exception {
		String path = session.getServletContext().getRealPath("/");
		File file = new File(path + "/DefaultData/DefaultData.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		String db = "";
		int line = 0;
		List<String> param = new ArrayList<>();
		List<String> value = new ArrayList<>();
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
					if (db.replaceAll("\\s+", "").equalsIgnoreCase("injectedfluid")) {
						saveInjectedfluidData(param, value, pid);
					} else if (db.replaceAll("\\s+", "").equalsIgnoreCase("pumpingEquipment")) {
						savePumpingData(param, value, pid);
					} else if (db.replaceAll("\\s+", "").equalsIgnoreCase("rockProperties")) {
						saveRockData(param, value, pid);
					} else if (db.replaceAll("\\s+", "").equalsIgnoreCase("reservoirfluid")) {
						saveReservoirFluidData(param, value, pid);
					}else if (db.replaceAll("\\s+", "").equalsIgnoreCase("proppantproperties")) {
						saveProppantData(param, value, pid);
					}else if (db.replaceAll("\\s+", "").equalsIgnoreCase("wellforcast")) {
						saveForcastData(param, value, pid);
					}else if (db.replaceAll("\\s+", "").equalsIgnoreCase("wellForcastDeclineCurveAnalysis")) {
						saveForcastCruveData(param, value, pid);
					}
					db = "";
					line = 0;
					param.clear();
					value.clear();
				}
			}
		}
		br.close();
	}


	public void saveInjectedfluidData(List<String> param, List<String> value, Integer pid) {
		List<InjectedFluid1Model> dataModels = new ArrayList<>();
		for (int i = 0; i < param.size(); i++) {
			InjectedFluid1Model dataModel = new InjectedFluid1Model();
			ProjectDetails details = detailRepo.findById(pid).orElse(null);
			dataModel.setDetails(details);
			dataModel.setParam(param.get(i));
			dataModel.setValue(value.get(i));
			dataModels.add(dataModel);
		}
		injectedrepo.saveAll(dataModels);
	}

	public void savePumpingData(List<String> param, List<String> value, Integer pid) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		for (int i = 0; i < param.size(); i++) {
			PumpingModel model = new PumpingModel();
			model.setParam(param.get(i));
			model.setValue(value.get(i));
			model.setDetails(details);
			pumprepo.save(model);
		}
	}

	public void saveRockData(List<String> param, List<String> value, Integer pid) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		for (int i = 1; i < param.size(); i++) {
			RockPropertiesModel model = new RockPropertiesModel();
			model.setParam(param.get(i));
			model.setValue(value.get(i));
			model.setStage(value.get(0));
			model.setDetails(details);
			rockrepo.save(model);
		}
	}

	public void saveReservoirFluidData(List<String> param, List<String> value, Integer pid) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		int fluidindex = param.indexOf("fluidtype");
		for (int i = 1; i < param.size(); i++) {
			if (param.get(i).equalsIgnoreCase("fluidtype")) {
				fluidindex = i;
			}
			ReservoirFluidModel model = new ReservoirFluidModel();
			if (!(param.get(i).equalsIgnoreCase("fluidtype"))) {
				model.setParam(param.get(i));
				model.setValue(value.get(i));
				model.setFluidtype(value.get(fluidindex));
				model.setDetails(details);
				fluidRepo.save(model);
			}
		}
	}
	
	public void saveProppantData(List<String> param, List<String> value, Integer pid) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ProppantModel> list=new ArrayList<ProppantModel>();
		for (int i = 0; i < 12; i++) {
			if(list.size()>0&&list.get(0).getParam().equalsIgnoreCase(param.get(i))) {
				break;
			}
			ProppantModel model = new ProppantModel();
			model.setParam(param.get(i));
			model.setValue(value.get(i));
			model.setDetails(details);
			list.add(model);
		}
		proppantRepo.saveAll(list);
	}
	
	public void saveForcastData(List<String> param, List<String> value, Integer pid) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<WellForcastModel> list=new ArrayList<WellForcastModel>();
		for(int i=0;i<param.size();i++){
			WellForcastModel forcastModel=new WellForcastModel();
			forcastModel.setDetails(details);
			forcastModel.setParam(param.get(i));
			forcastModel.setValue(value.get(i));
			list.add(forcastModel);
		}
		forcastRepo.saveAll(list);
	}
	
	public void saveWellData(int pid)
	{
	ProjectDetails pd1=	detailRepo.getOne(pid);
		String[] as=null;
		 String path=session.getServletContext().getRealPath("/");		 
	try {			
		FileReader file=new FileReader(path+"/DefaultData/DefaultData1.txt");	 		
		  BufferedReader br = new BufferedReader(file); 	  
		  String st; 
		  while ((st = br.readLine()) != null) 
		  {         as = st.split("[=]");			  
			        WellDataModel m1= new WellDataModel();
			        m1.setPid(pd1);
			        m1.setParameter(as[0]);
			        m1.setValue(as[1]);
			        m1.setWellType("slanted");
			        m1.setCompletionType("slotted Liner");
			        wellrepo.save(m1);			        
		  }	 
		  br.close();
	} catch(Exception e) { System.out.println(e);}  } 
	                
	                
	             public void saveFluidLibraryDefault(int pid) throws Exception
	            	{
	            	ProjectDetails pd1=	detailRepo.getOne(pid);
	            		String[] as=null;
	            		 String path=session.getServletContext().getRealPath("/");		 
	            	try {			
	            		FileReader file=new FileReader(path+"/DefaultData/DefaultDataFL.txt");	 		
	            		  BufferedReader br = new BufferedReader(file); 	  
	            		  String st; 
	            		  String type=null;
	            		  FluidLibraryModel m1=null;
	            		  while ((st = br.readLine()) != null) 
	            		  {    
	            			  if(st.startsWith("/"))
	            				{type=st.substring(1);}
	            			  else{   m1= new FluidLibraryModel();
	            				  m1.setType(type);
	            				  as = st.split("[=]");			  
                                  m1.setParameter(as[0]);
	            			      m1.setValue(as[1]);
	            			      m1.setPidFL(pd1);
	            			      flr.save(m1);
	            			  }
                              }	  
	            			  br.close();
	            	} catch(Exception e) { System.out.println(e);}  } 
	             
	             public void saveForcastCruveData(List<String> param, List<String> value, Integer pid) {
	    	    	 ProjectDetails details = detailRepo.findById(pid).orElse(null);
	    	 		 List<WellForcastDeclinedCurveModel> list=new ArrayList<WellForcastDeclinedCurveModel>();
	    	 		for(int i=0;i<param.size();i++){
	    	 			WellForcastDeclinedCurveModel forcastModel=new WellForcastDeclinedCurveModel();
	    				forcastModel.setDetails(details);
	    				forcastModel.setParam(param.get(i));
	    				forcastModel.setValue(value.get(i));
	    				list.add(forcastModel);
	    			}
	    	 		curveRepo.saveAll(list);
	    	     }
	             
	             //reservoir fluid
	             public void saveReservoirFluidDefault(int pid) throws Exception
	            	{
	            	ProjectDetails pd1=	detailRepo.getOne(pid);
	            		String[] as=null;
	            		 String path=session.getServletContext().getRealPath("/");		 
	            	try {			
	            		FileReader file=new FileReader(path+"/DefaultData/reservoirFluid.txt");	 		
	            		  BufferedReader br = new BufferedReader(file); 	  
	            		  String st; 
	            		  String type=null;
	            		  ReservoirFluidModel m1=null;
	            		  while ((st = br.readLine()) != null) 
	            		  {    
	            			  if(st.startsWith("/"))
	            				{type=st.substring(1);}
	            			  else{   m1= new ReservoirFluidModel();
	            				m1.setFluidtype(type);  
	            				  as = st.split("[=]");	
	            				  if(as[0].endsWith("(Gas)")) {
	            					  m1.setWellType("Gas Well");
	            				  }
	            				  else {
	            					  m1.setWellType("Oil Well");}
                              m1.setParam(as[0]);	            				  
	            			      m1.setValue(as[1]);
	            			     // m1.setValue(as[1]);
                               m1.setDetails(pd1);
	            			      fluidRepo.save(m1);
	            			  } }
	            		  br.close();  	  
	            	} catch(Exception e) { System.out.println(e);}  }  
}
