package com.octodemo.octocatsupply.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Value("${cors.allowed-origins}")
	private String allowedOriginsString;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		List<String> origins = Arrays.asList(allowedOriginsString.split(","));
		
		List<String> allPatterns = new java.util.ArrayList<>(origins.stream()
				.map(String::trim)
				.toList());
		allPatterns.add("https://*.app.github.dev");
		allPatterns.add("https://*.azurecontainerapps.io");
		// Allow private network IPs for local/LAN development (restrict to IP-like hosts)
		allPatterns.add("http://192.168.*.*");
		allPatterns.add("http://10.*.*.*");
		for (int i = 16; i <= 31; i++) {
			allPatterns.add("http://172." + i + ".*.*");
		}

		registry.addMapping("/**")
				.allowedOriginPatterns(allPatterns.toArray(new String[0]))
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
				.allowedHeaders("*")
				.exposedHeaders("*")
				.allowCredentials(true)
				.maxAge(3600);
	}
}
