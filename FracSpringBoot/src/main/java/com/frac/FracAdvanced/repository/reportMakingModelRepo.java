package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ReportMakingModel;

public interface reportMakingModelRepo extends JpaRepository<ReportMakingModel, Integer>{
 @Query("select t from ReportMakingModel t where pid=:pid order by t.id")
	List<ReportMakingModel> findBypid(ProjectDetails pid);
	@Transactional
	List<ReportMakingModel> deleteBypid(ProjectDetails pid);
	
	
}
