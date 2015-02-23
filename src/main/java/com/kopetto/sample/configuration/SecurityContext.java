package com.kopetto.sample.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;

import com.kopetto.sample.filter.CsrfHeaderFilter;

@Configuration
@EnableWebSecurity
@Order(5) 
public class SecurityContext extends WebSecurityConfigurerAdapter {
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("ADMIN");
    }
	
    @Override
    //TODO-TBD
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/oauth/uncache_approvals", "/oauth/cache_approvals");
    }
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.headers()
			//need this to handle token refresh in iframe
        	.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsMode.SAMEORIGIN))
        .and ()
        .formLogin()
        .and()
        .authorizeRequests()
          .antMatchers("/oauth/token").permitAll()
          .antMatchers("/css/**").permitAll()
          .antMatchers("/js/**").permitAll()
          .antMatchers("/images/**").permitAll()
          .antMatchers("/favicon.ico").permitAll()
          .antMatchers("/module/**").permitAll()
          .anyRequest().authenticated()
          .and()
      .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
      .csrf().csrfTokenRepository(csrfTokenRepository())
      ; 
		
	}
	
	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}	
	
	@Override
	@Bean
	//need in oauth config
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}	
	
}