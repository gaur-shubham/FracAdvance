package com.frac.FracAdvanced.domain.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@EnableWebSecurity
public class TotalSafe extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsService userDetailsService;
	
	
	/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { 		
 		
		auth.userDetailsService(userDetailsService);
		auth.inMemoryAuthentication().withUser("vishal")
 		.password("vishal")
 		.roles("USER")
 		.and().withUser("subham")
 		.password("subham")
 		.roles("USER")
 		 .and()
 		.withUser("surya")
 		.password("surya")
 		.roles("user");		
 		auth.userDetailsService(userDetailsService)
	}*/ 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
			.antMatchers("/").hasAnyRole("USER","ADMIN")	
			.antMatchers("/graphs").hasAnyRole("ADMIN")
			//.antMatchers("/").permitAll()
			.and()
		.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/login1")
			/*.usernameParameter("username")
			.passwordParameter("password")*/
			.defaultSuccessUrl("/")
			.failureUrl("/loginFail")
			.and()
		.logout()
			.invalidateHttpSession(true)
			.logoutUrl("/ap-logout")
			.logoutSuccessUrl("/login")
			.permitAll()
			.deleteCookies("JSESSIONID")
			.and()
		.exceptionHandling()
		.accessDeniedPage("/accessNotAllowed")
		;
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
