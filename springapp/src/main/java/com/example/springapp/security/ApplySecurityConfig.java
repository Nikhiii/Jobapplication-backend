package com.example.springapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springapp.service.UserService;
import com.example.springapp.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class ApplySecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtAuthenticationFilter jwtFilter;

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and().authorizeRequests()
			.antMatchers("/auth/login").permitAll()
            .antMatchers("/auth/register").permitAll()
            .and().authorizeRequests().antMatchers("/api/Job/freeJobs","/api/Job/premiumJobs").hasAnyRole("ADMIN", "APPLICANT")
			.and().authorizeRequests().antMatchers("/api/Job","/api/Job/*","/api/Job/applicants","/api/Applicant/ChangeStatus/*").hasRole("ADMIN")
			.and().authorizeRequests().antMatchers("/api/Applicant","/api/Applicant/*","/api/Applicant/getAlljob").hasRole("APPLICANT")
			.anyRequest().authenticated()
			.and().formLogin()
			.and().httpBasic()
			.and()
			.logout((logout) -> logout.permitAll())
            .sessionManagement() .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().exceptionHandling().authenticationEntryPoint(entryPoint);
            
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        }

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
            auth.userDetailsService(userService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Return BCryptPasswordEncoder
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

}