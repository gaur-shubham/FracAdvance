/**
 * 
 */
package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.WellForcastDeclinedCurveModel;

/**
 * @author ShubhamGaur
 *
 */
public interface WellForcastDeclinedCurveRepo extends JpaRepository<WellForcastDeclinedCurveModel, Integer> {
	@Query("select t from WellForcastDeclinedCurveModel t where t.details=:details order by t.id")
	List<WellForcastDeclinedCurveModel> findBydetails(ProjectDetails details);
}
