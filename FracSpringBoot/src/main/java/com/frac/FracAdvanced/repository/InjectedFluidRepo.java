package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frac.FracAdvanced.model.InjectedFluidModel;
import com.frac.FracAdvanced.model.ProjectDetails;

/**
 * @author ShubhamGaur
 *
 */
public interface InjectedFluidRepo extends JpaRepository<InjectedFluidModel, Integer> {
	@Query("select t from InjectedFluidModel t where t.details=:details order by t.id")
		List<InjectedFluidModel> findBydetails(ProjectDetails details);
}
