package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.frac.FracAdvanced.model.InjectionPlanModel;
import com.frac.FracAdvanced.model.ProjectDetails;

/**
 * @author ShubhamGaur
 *
 */
public interface InjectionPlanRepo extends JpaRepository<InjectionPlanModel, Integer> {
	@Query("select t from InjectionPlanModel t where t.details=:details order by t.id")
		List<InjectionPlanModel> findBydetails(ProjectDetails details);
}
