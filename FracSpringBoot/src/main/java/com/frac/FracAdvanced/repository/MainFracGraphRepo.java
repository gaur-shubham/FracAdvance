package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.frac.FracAdvanced.model.MainFracGraphModel;
import com.frac.FracAdvanced.model.ProjectDetails;

/**
 * @author ShubhamGaur
 *
 */
public interface MainFracGraphRepo extends CrudRepository<MainFracGraphModel, Integer>{
	@Query("select t from MainFracGraphModel t where t.details=:details order by t.id")
	List<MainFracGraphModel> findBydetails(ProjectDetails details);
}
