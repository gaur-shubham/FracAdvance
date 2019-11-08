/**
 * 
 */
package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ReservoirLithologyModel;

/**
 * @author ShubhamGaur
 *
 */
public interface ReservoirLithologyRepo extends JpaRepository<ReservoirLithologyModel, Integer> {
	@Query("select t from ReservoirLithologyModel t where t.details=:details order by t.id")
	List<ReservoirLithologyModel> findBydetails(ProjectDetails details);
}
