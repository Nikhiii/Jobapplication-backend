package com.example.springapp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CrosConfig implements WebMvcConfigurer {

	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**") // URL patterns to apply CORS configuration
	            .allowedOrigins("http://localhost:8081","http://localhost:9876") // Allowed origin URLs
	            .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS") // Allowed HTTP methods
	            .allowedHeaders("*")   // Allowed headers
            .allowCredentials(true);
	    }
	 
}
