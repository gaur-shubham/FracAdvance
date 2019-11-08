/**
 * 
 */
package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ProppantModel;

/**
 * @author ShubhamGaur
 *
 */
public interface ProppantRepo extends JpaRepository<ProppantModel, Integer>{
	@Query("select t from ProppantModel t where t.details=:detail order by t.id")
		List<ProppantModel> findBydetails(ProjectDetails detail);
		
		@Transactional
		void deleteBydetails(ProjectDetails detail);
		@Query("select t from ProppantModel t where t.param=:param and t.details=:details order by t.id")
		List<ProppantModel> findByParamAndDetails(String param,ProjectDetails details);		
		

}
