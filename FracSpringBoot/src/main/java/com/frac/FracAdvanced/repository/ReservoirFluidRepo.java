/**
 * 
 */
package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ReservoirFluidModel;

/**
 * @author ShubhamGaur
 *
 */
public interface ReservoirFluidRepo extends JpaRepository<ReservoirFluidModel, Integer> {

	@Query("select t from ReservoirFluidModel t where t.details=:details order by t.id")
	List<ReservoirFluidModel> findBydetails(ProjectDetails details);
	@Query("select t from ReservoirFluidModel t where t.details=:details and t.fluidtype=:fluidtype order by t.id")
	List<ReservoirFluidModel> findByFluidtypeAndDetails(String fluidtype,ProjectDetails details);
	@Query("select t from ReservoirFluidModel t where t.details=:details and t.param=:param order by t.id")
	List<ReservoirFluidModel> findByParamAndDetails(String param,ProjectDetails details);
	@Query("select t from ReservoirFluidModel t where t.details=:details and t.fluidtype=:fluidtype and t.wellType=:wellType order by t.id")
	List<ReservoirFluidModel> findByFluidtypeAndWellTypeAndDetails(String wellType,String fluidtype,ProjectDetails details);
		
}
