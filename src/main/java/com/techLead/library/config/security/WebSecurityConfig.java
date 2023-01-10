package com.techLead.library.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { 
		http.httpBasic().and().authorizeHttpRequests()
				.antMatchers(HttpMethod.POST, "/library/create-user").permitAll()				
				.antMatchers(HttpMethod.GET, "/books/all-books").permitAll()
				.antMatchers(HttpMethod.GET, "/books/{id}").permitAll()
				.antMatchers(HttpMethod.POST, "/books/register-book").permitAll()
				.antMatchers(HttpMethod.PUT, "/books/update/{idBook}").permitAll()
				.antMatchers(HttpMethod.DELETE, "/books/delete/{idBook}").hasAnyRole("ROLE_USER", "ROLE_ADMIN")
				.anyRequest()
				.authenticated().and()
				.formLogin().permitAll()
				.defaultSuccessUrl("/books/all-books")
				.failureUrl("/login?error=true")
				.and()				
				.logout()	
				.logoutSuccessUrl("/login")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
				.cors().and().csrf().disable();
		return http.build();
	} 
	
	

}
