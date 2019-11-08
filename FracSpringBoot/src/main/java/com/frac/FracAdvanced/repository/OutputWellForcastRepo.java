/**
 * 
 */
package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frac.FracAdvanced.model.OutputWellforcastModel;
import com.frac.FracAdvanced.model.ProjectDetails;

/**
 * @author ShubhamGaur
 *
 */
public interface OutputWellForcastRepo extends JpaRepository<OutputWellforcastModel, Integer>{
	@Query("select t from OutputWellforcastModel t where t.details=:detail order by t.id")
	List<OutputWellforcastModel> findBydetails(ProjectDetails detail);
}
