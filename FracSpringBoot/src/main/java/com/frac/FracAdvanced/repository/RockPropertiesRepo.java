/**
 * 
 */
package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.RockPropertiesModel;

/**
 * @author ShubhamGaur
 *
 */
public interface RockPropertiesRepo extends JpaRepository<RockPropertiesModel, Integer>{
	@Query("select t from RockPropertiesModel t where t.details=:details order by t.id")
	List<RockPropertiesModel> findBydetails(ProjectDetails details);
	@Query("select t from RockPropertiesModel t where t.stage=:stage and t.details=:details order by t.id")
	List<RockPropertiesModel> findByStageAndDetails(String stage,ProjectDetails details);
	
	Long countBydetails(ProjectDetails details);
}
