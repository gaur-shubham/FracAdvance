/**
 * 
 */
package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.WellForcastModel;

/**
 * @author ShubhamGaur
 *
 */
public interface WellForcastRepo extends JpaRepository<WellForcastModel, Integer>{
	@Query("select t from WellForcastModel t where t.details=:detail order by t.id")
	List<WellForcastModel> findBydetails(ProjectDetails detail);
}
