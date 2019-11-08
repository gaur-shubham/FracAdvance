/**
 * 
 */
package com.frac.FracAdvanced.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.WellForcastDeclinedCurveModel;
import com.frac.FracAdvanced.repository.OutputWellForcastRepo;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.WellForcastDeclinedCurveRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class WellForcastDeclinedCurverService {
	@Autowired
	private WellForcastDeclinedCurveRepo curverepo;
	@Autowired
	private ProjectDetailRepo detailRepo;
	@Autowired
	private OutputWellForcastRepo outforcastRepo;
	
	
	public List<WellForcastDeclinedCurveModel> showList1(Integer pid,String type) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<WellForcastDeclinedCurveModel> list=curverepo.findBydetails(detail);
		List<WellForcastDeclinedCurveModel> list1=new ArrayList<WellForcastDeclinedCurveModel>();
		for(int i=0;i<list.size();i++) {
			if(i<5) {
				if(list.get(i).getParam().equalsIgnoreCase("Initial Month Qo")) {
					list.get(i).setValue(outforcastRepo.findBydetails(detail).get(1).getValue());
				}
				list1.add(list.get(i));
			}
		}
		if(!type.equalsIgnoreCase("Hyberbolic")) {
			list1.remove(list1.size()-1);
		}
		return list1;
	}
	
	public List<WellForcastDeclinedCurveModel> showList2(Integer pid,String type) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<WellForcastDeclinedCurveModel> list=curverepo.findBydetails(detail);
		List<WellForcastDeclinedCurveModel> list1=new ArrayList<WellForcastDeclinedCurveModel>();
		for(int i=0;i<list.size();i++) {
			if(i<5) {
					list1.add(list.get(i));
			}
		}
		if(!type.equalsIgnoreCase("Hyberbolic")) {
			list1.remove(list1.size()-1);
		}
		return list1;
	}

	public List<WellForcastDeclinedCurveModel> showlist3(Integer pid){
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<WellForcastDeclinedCurveModel> list=curverepo.findBydetails(detail);
		List<WellForcastDeclinedCurveModel> list1=new ArrayList<>();
		for(int i=0;i<list.size();i++) {
			if(i>=5) {
				list1.add(list.get(i));
			}
		}	
		return list1;
	}
	
	public void saveField(Integer pid, String time,String type) {
		String rate=CalcQ(pid,time, type);
		String prod=CalcNp(pid, time, type, rate);
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		WellForcastDeclinedCurveModel curveModel=new WellForcastDeclinedCurveModel();
		curveModel.setRate(rate);
		curveModel.setYearlyprodunction(prod);
		curveModel.setDetails(detail);
		curveModel.setTime(time);
		curverepo.save(curveModel);
	}
	
	public String CalcQ(Integer pid,String time,String type) {
		double rate=0.0,t=12,time1=Double.parseDouble(time);
		double q0=0.0;
		double bm=0.0;
		double d=0.0;
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<WellForcastDeclinedCurveModel> list=curverepo.findBydetails(detail);
		bm=Double.parseDouble(list.get(0).getBm());
		if(list.size()<=5) {
			for(int i=0;i<list.size();i++) {
				WellForcastDeclinedCurveModel model=list.get(i);
				if(i<5) {
				if(model.getParam().equalsIgnoreCase("Initial Month Qo")) {
					q0=Double.parseDouble(model.getValue());
				}else if(model.getParam().equalsIgnoreCase("d")) {
					d=Double.parseDouble(model.getValue());
				}
			}
			}
		}else {
			q0=Double.parseDouble(curverepo.findBydetails(detail).get(list.size()-1).getRate());
			d=Double.parseDouble(curverepo.findBydetails(detail).get(4).getValue());
		}

		if(type.equalsIgnoreCase("Exponential")) {
			rate=q0*(Math.exp(-bm*t));
		}else if(type.equalsIgnoreCase("Harmonic")) {
			rate=q0/(1+(bm*t*time1));
		}else if(type.equalsIgnoreCase("Hyberbolic")) {
			rate=q0/(Math.pow((1+(d*bm*t*time1)), (1/d)));
		}
		return String.valueOf(rate);
	}
	
	public String CalcNp(Integer pid,String time,String type,String rate){
		double prod=0.0,t=12,time1=Double.parseDouble(time);
		double q0=0.0;
		double bm=0.0;
		double d=0.0;
		double q=Double.parseDouble(rate);
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<WellForcastDeclinedCurveModel> list=curverepo.findBydetails(detail);
		bm=Double.parseDouble(list.get(0).getBm());
		if(list.size()<=5) {
			for(int i=0;i<list.size();i++) {
				WellForcastDeclinedCurveModel model=list.get(i);
				if(model.getParam().equalsIgnoreCase("Initial Month Qo")) {
					q0=Double.parseDouble(model.getValue());
				}else if(model.getParam().equalsIgnoreCase("d")) {
					d=Double.parseDouble(model.getValue());
				}
			}
		}else {
			q0=Double.parseDouble(curverepo.findBydetails(detail).get(list.size()-1).getRate());
			d=Double.parseDouble(curverepo.findBydetails(detail).get(4).getValue());
		}
		
		if(type.equalsIgnoreCase("Exponential")) {
			prod=365*((q0-q)/(bm*t));
		}else if(type.equalsIgnoreCase("Harmonic")) {
			prod=365*(((q0)*((Math.log(q0))-(Math.log(q))))/(bm*t));
		}else if(type.equalsIgnoreCase("Hyberbolic")) {
			double a=1/d;
			prod=((a/(bm*t*(a-1)))*(q0-(q*(1+(bm*t/a))*time1)))*365;
		}
		return String.valueOf(prod);
	}
	
	public void save1(Integer pid,List<String> input) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<WellForcastDeclinedCurveModel> list=curverepo.findBydetails(detail);
		List<WellForcastDeclinedCurveModel> list1=new ArrayList<WellForcastDeclinedCurveModel>();
		for(int i=0;i<input.size();i++) {
			WellForcastDeclinedCurveModel curveModel=list.get(i);
			curveModel.setValue(input.get(i));
			list1.add(curveModel);
		}
		curverepo.saveAll(list1);
	}
	
	public void saveBm(Integer pid) {
		ProjectDetails detail = detailRepo.findById(pid).orElse(null);
		List<WellForcastDeclinedCurveModel> list=curverepo.findBydetails(detail);
		double q0=0.0;
		double q1=0.0;
		double t0=0.0;
		double t1=0.0;
		double bm=0.0;
		for(int i=0;i<list.size();i++) {
			WellForcastDeclinedCurveModel model=list.get(i);
			if(i<5) {
			if(model.getParam().equalsIgnoreCase("Initial Month Qo")) {
				q0=Double.parseDouble(model.getValue());
			}else if(model.getParam().equalsIgnoreCase("End Month Q")) {
				q1=Double.parseDouble(model.getValue());
			}else if(model.getParam().equalsIgnoreCase("Initial Month T1m")) {
				t1=Double.parseDouble(model.getValue());
			}else if(model.getParam().equalsIgnoreCase("End Month T0m")) {
				t0=Double.parseDouble(model.getValue());
			}
			}
		}
		bm=(1/(t1-t0))*(Math.log(q0/q1));
		WellForcastDeclinedCurveModel curveModel=null;
		if(curverepo.findBydetails(detail).size()<1) {
			curveModel=new WellForcastDeclinedCurveModel();
		}else {
			curveModel=curverepo.findBydetails(detail).get(0);
		}
		curveModel.setBm(String.valueOf(bm));
		curverepo.save(curveModel);
	}
}
