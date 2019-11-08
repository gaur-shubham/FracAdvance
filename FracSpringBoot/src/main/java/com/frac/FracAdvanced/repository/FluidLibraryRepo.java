package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.frac.FracAdvanced.model.FluidLibraryModel;
import com.frac.FracAdvanced.model.ProjectDetails;


public interface FluidLibraryRepo extends JpaRepository<FluidLibraryModel, Integer>{

	@Query("SELECT t FROM FluidLibraryModel t where t.pidFL.id = :id order by t.id") 
    List<FluidLibraryModel> findByProId(@Param("id") Integer id);
	@Query("SELECT t FROM FluidLibraryModel t where t.pidFL = :Project_ID and t.type=:type order by t.id")
	List<FluidLibraryModel> findByPidFLAndType(ProjectDetails Project_ID, String type);
	
	//List<ReservoirFluidModel> findByParamAndDetails(String param,ProjectDetails details);		
	

}
