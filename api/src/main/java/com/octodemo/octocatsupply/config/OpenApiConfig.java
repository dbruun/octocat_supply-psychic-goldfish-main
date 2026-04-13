package com.octodemo.octocatsupply.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

	@Value("${server.port}")
	private String serverPort;

	@Bean
	public OpenAPI octocatSupplyOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("OctoCAT Supply Chain Management API")
						.description("Java/Spring Boot implementation of the OctoCAT Supply Chain Management API")
						.version("1.0.0"))
				.servers(List.of(
						new Server().url("http://localhost:" + serverPort).description("Development server (HTTP)"),
						new Server().url("https://localhost:" + serverPort).description("Development server (HTTPS)")
				));
	}
}
