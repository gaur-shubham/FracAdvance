package com.frac.FracAdvanced.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.frac.FracAdvanced.model.MiniFracModel;
import com.frac.FracAdvanced.model.ProjectDetails;

/**
 * @author ShubhamGaur
 *
 */
@Repository
public interface MiniFracRepo extends JpaRepository<MiniFracModel, Integer> {

	@Query("SELECT t FROM MiniFracModel t where t.details.id = :id order by t.id") 
    List<MiniFracModel> findByProId(@Param("id") Integer id);
	@Query("select t from MiniFracModel t where t.details=:details order by t.id")
	List<MiniFracModel> findBydetails(ProjectDetails details);
	@Query("SELECT t FROM MiniFracModel t where t.id = :id order by t.id")
	List<MiniFracModel> getById(Integer id);
}
