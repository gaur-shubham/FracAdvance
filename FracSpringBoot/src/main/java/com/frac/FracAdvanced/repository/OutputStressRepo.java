/**
 * 
 */
package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frac.FracAdvanced.model.OutputStressModel;
import com.frac.FracAdvanced.model.ProjectDetails;

/**
 * @author ShubhamGaur
 *
 */
public interface OutputStressRepo extends JpaRepository<OutputStressModel, Integer> {
	@Query("select t from OutputStressModel t where t.details=:details order by t.id")
	List<OutputStressModel> findBydetails(ProjectDetails details);
}
