package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frac.FracAdvanced.model.ProjectDetails;


/**
 * @author ShubhamGaur
 *
 */
public interface ProjectDetailRepo extends JpaRepository<ProjectDetails, Integer> {

	@Query("select t from ProjectDetails t order by t.id")
	List<ProjectDetails> findAll();
}
