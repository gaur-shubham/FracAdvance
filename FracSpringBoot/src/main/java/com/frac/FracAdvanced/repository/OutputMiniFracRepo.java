package com.frac.FracAdvanced.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.frac.FracAdvanced.model.OutputMiniFrac;

/**
 * @author ShubhamGaur
 *
 */
@Repository
public interface OutputMiniFracRepo extends JpaRepository<OutputMiniFrac, Integer> {

	@Query("SELECT t FROM OutputMiniFrac t where t.prodetails.id = :id order by t.id") 
    List<OutputMiniFrac> findByProId(@Param("id") Integer id);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM OutputMiniFrac t where t.prodetails.id = :id") 
    void deleteByProId(@Param("id") Integer id);
	
}
