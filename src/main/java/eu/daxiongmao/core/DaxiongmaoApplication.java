package eu.daxiongmao.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// Spring boot application
@SpringBootApplication
// Enable JPA and DB transactions support
@EnableJpaRepositories(basePackages = {"eu.daxiongmao.core.dao"})
@EnableTransactionManagement
public class DaxiongmaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaxiongmaoApplication.class, args);
	}

}
