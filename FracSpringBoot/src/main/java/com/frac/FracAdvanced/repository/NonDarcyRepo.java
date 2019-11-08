/**
 * 
 */
package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frac.FracAdvanced.model.InjectedFluid1Model;
import com.frac.FracAdvanced.model.NonDarcyModel;
import com.frac.FracAdvanced.model.ProjectDetails;

/**
 * @author ShubhamGaur
 *
 */
public interface NonDarcyRepo extends JpaRepository<NonDarcyModel, Integer>{
	@Query("select t from NonDarcyModel t where t.details=:details order by t.id")
	List<NonDarcyModel> findBydetails(ProjectDetails details);
}
