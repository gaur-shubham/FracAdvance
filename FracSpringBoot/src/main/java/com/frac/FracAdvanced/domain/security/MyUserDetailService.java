package com.frac.FracAdvanced.domain.security;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.userModel;
import com.frac.FracAdvanced.repository.UserRepo;
@Service
public class MyUserDetailService implements UserDetailsService {
	@Autowired
 UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
 Optional<userModel> user=userRepo.findByUserName(userName);
 user.orElseThrow(()-> new UsernameNotFoundException("Not found"+ user));
	return user.map(MyUserDetails::new).get();
	}
	
/*@Override
public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

return new MyUserDetails(userName);} 
*/}
