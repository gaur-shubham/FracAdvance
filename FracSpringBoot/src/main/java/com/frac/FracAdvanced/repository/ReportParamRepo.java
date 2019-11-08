package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ReportParamModel;

/**
 * @author ShubhamGaur
 *
 */
public interface ReportParamRepo extends CrudRepository<ReportParamModel, Integer>{
	@Query("select t from ReportParamModel t where t.details=:details order by t.id")
	List<ReportParamModel> findBydetails(ProjectDetails details);
}
