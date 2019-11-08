/**
 * 
 */
package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frac.FracAdvanced.model.InjectedFluid1Model;
import com.frac.FracAdvanced.model.ProjectDetails;

/**
 * @author ShubhamGaur
 *
 */
public interface InjectedFluid1Repo extends JpaRepository<InjectedFluid1Model, Integer> {
	@Query("select t from InjectedFluid1Model t where t.details=:details order by t.id")
	List<InjectedFluid1Model> findBydetails(ProjectDetails details);
}
