package com.frac.FracAdvanced.domain.security;

import java.util.Arrays;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.frac.FracAdvanced.model.userModel;

import antlr.collections.List;

public class MyUserDetails implements UserDetails {
	
	public String userName;
	public String password;
	public boolean active;
	private java.util.List<GrantedAuthority> authority;
	
	
	public  MyUserDetails(userModel user) {
this.userName= user.getUserName();
this.password=user.getPassword();
this.active= user.isActive();
this.authority= Arrays.stream(user.getRolw().split(","))
.map(SimpleGrantedAuthority::new)
.collect(Collectors.toList());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authority;
	}
	

	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return userName;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	 @Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	 @Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return active;
	}
	 

		/*public String userName;
			
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {

			return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		}
		
		@Override
		public String getPassword() {
			// TODO Auto-generated method stub
			return "pass";
		}
		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			return userName;
		}
		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}
		@Override
		public boolean isAccountNonLocked() {
			// TODO Auto-generated method stub
			return true;
		}
		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}
		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return true;
		}
		public MyUserDetails( String userName) {
	this.userName=userName;
		}

		public MyUserDetails() {
			
		}*/
		
}
