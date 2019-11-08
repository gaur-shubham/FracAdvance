package com.frac.FracAdvanced.domain.security;

import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@EnableWebSecurity
public class TotalSafe extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsService userDetailsService;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { 		
 		
		auth.userDetailsService(userDetailsService);
		/*auth.inMemoryAuthentication().withUser("vishal")
 		.password("vishal")
 		.roles("USER")
 		.and().withUser("subham")
 		.password("subham")
 		.roles("USER")
 		 .and()
 		.withUser("surya")
 		.password("surya")
 		.roles("user");*/ 		
 		//auth.userDetailsService(userDetailsService)
	}
	
	protected void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests()
		.antMatchers("/").hasAnyRole("USER","ADMIN")	
		.antMatchers("/graphs").hasAnyRole("ADMIN")
		//.antMatchers("/").permitAll()
		.and().formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/login1")
		.usernameParameter("username")
		.passwordParameter("password")
		.defaultSuccessUrl("/")
		.failureUrl("/loginFail")
		.and().logout()
		.logoutUrl("/ap-logout")
		.logoutSuccessUrl("/login")
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
