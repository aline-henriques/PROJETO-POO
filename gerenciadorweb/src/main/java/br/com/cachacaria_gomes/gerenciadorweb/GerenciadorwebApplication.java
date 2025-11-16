package br.com.cachacaria_gomes.gerenciadorweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GerenciadorwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorwebApplication.class, args);
	}

}
