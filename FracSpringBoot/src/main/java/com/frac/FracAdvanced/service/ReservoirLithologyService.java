/**
 * 
 */
package com.frac.FracAdvanced.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ReservoirLithologyModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.ReservoirLithologyRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class ReservoirLithologyService {
	
	@Autowired
	private ReservoirLithologyRepo lithologyRepo;
	@Autowired
	private ProjectDetailRepo detailRepo;
	
	public List<Integer> showRows(Integer number){
		List<Integer> list=new ArrayList<Integer>();
		for(int i=1;i<=number;i++) {
			list.add(i);
		}
		return list;
	}
	
	public List<ReservoirLithologyModel> showList(Integer pid){
		ProjectDetails details=detailRepo.findById(pid).orElse(null);
		List<ReservoirLithologyModel> list=lithologyRepo.findBydetails(details);
		return list;
	}
	
	public void saveLithology(Integer pid,List<String> input) {
		ProjectDetails details=detailRepo.findById(pid).orElse(null);
		List<ReservoirLithologyModel> list=new ArrayList<>();
		ReservoirLithologyModel lithologyModel=new ReservoirLithologyModel();
		int k=0;
		for(int i=0;i<input.size()/9;i++) {
				lithologyModel=new ReservoirLithologyModel();
				lithologyModel.setDetails(details);
				lithologyModel.setFracstage(input.get(k));k++;
				lithologyModel.setTvd(input.get(k));k++;
				lithologyModel.setMd(input.get(k));k++;
				lithologyModel.setLayer(input.get(k));k++;
				lithologyModel.setRock(input.get(k));k++;
				lithologyModel.setPerm(input.get(k));k++;
				lithologyModel.setPoro(input.get(k));k++;
				lithologyModel.setLeakoff(input.get(k));k++;
				lithologyModel.setYoungs(input.get(k));k++;
				list.add(lithologyModel);
		}
		lithologyRepo.saveAll(list);
	}
	
	public void saveEdit(Integer pid, List<String> input) {
		ProjectDetails details=detailRepo.findById(pid).orElse(null);
		List<ReservoirLithologyModel> list=lithologyRepo.findBydetails(details);
		List<ReservoirLithologyModel> list1=new ArrayList<>();
		int k=0;
		for(int i=0;i<list.size();i++) {
			ReservoirLithologyModel lithologyModel=list.get(i);
			lithologyModel.setTvd(input.get(k));k++;
			lithologyModel.setMd(input.get(k));k++;
			lithologyModel.setLayer(input.get(k));k++;
			lithologyModel.setRock(input.get(k));k++;
			lithologyModel.setPerm(input.get(k));k++;
			lithologyModel.setPoro(input.get(k));k++;
			lithologyModel.setLeakoff(input.get(k));k++;
			lithologyModel.setYoungs(input.get(k));k++;
			list1.add(lithologyModel);
	}
		lithologyRepo.saveAll(list1);
	}
}
