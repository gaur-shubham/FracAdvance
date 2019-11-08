package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.frac.FracAdvanced.model.WellDataModel;

/**
 * @author ShubhamGaur
 *
 */
public interface WellDataRepo extends JpaRepository<WellDataModel, Integer> {

	@Query("SELECT t FROM WellDataModel t where t.pid.id = :id order by t.id") 
    List<WellDataModel> findByProId(@Param("id") Integer id);
}
