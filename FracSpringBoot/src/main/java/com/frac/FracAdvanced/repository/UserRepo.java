package com.frac.FracAdvanced.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frac.FracAdvanced.model.userModel;

public interface UserRepo extends JpaRepository<userModel, Integer> {
 Optional<userModel> findByUserName(String userName);
}
