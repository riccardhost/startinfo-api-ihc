package br.com.ifpe.startinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StartinfoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartinfoApiApplication.class, args);
	}

}
