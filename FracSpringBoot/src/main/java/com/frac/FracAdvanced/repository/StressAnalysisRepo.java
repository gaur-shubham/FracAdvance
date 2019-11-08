/**
 * 
 */
package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.StressAnalysisModel;

/**
 * @author ShubhamGaur
 *
 */
public interface StressAnalysisRepo extends JpaRepository<StressAnalysisModel, Integer> {
	@Query("select t from StressAnalysisModel t where t.details=:detail order by t.id")
	List<StressAnalysisModel> findBydetails(ProjectDetails detail);
}
