package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.frac.FracAdvanced.model.PhasingParamModel;
import com.frac.FracAdvanced.model.ProjectDetails;

/**
 * @author ShubhamGaur
 *
 */
public interface PhasingParamRepo extends CrudRepository<PhasingParamModel, Integer>{

	@Query("select t from PhasingParamModel t where t.details=:details order by t.id")
	List<PhasingParamModel> findBydetails(ProjectDetails details);
}
