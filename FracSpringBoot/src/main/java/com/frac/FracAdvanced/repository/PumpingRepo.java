/**
 * 
 */
package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.PumpingModel;

/**
 * @author ShubhamGaur
 *
 */
public interface PumpingRepo extends JpaRepository<PumpingModel, Integer> {
	@Query("select t from PumpingModel t where t.details=:details order by t.id")
	List<PumpingModel> findBydetails(ProjectDetails details);
	
	
}
