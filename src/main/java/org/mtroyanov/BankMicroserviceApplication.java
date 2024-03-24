package org.mtroyanov;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankMicroserviceApplication.class, args);
	}

	@Bean
	public OpenAPI openAPI(){
		Info info = new Info().description("Тестовое задание Троянов М.А.").title("Bank Microservice").version("V1");
		return new OpenAPI().info(info);
	}


}
