package eu.daxiongmao.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <h2>Description</h2>
 * <p>Personal application to use as quick-start, reminder and best practices and example.
 * This is based on my personal experience, training and work as a developer since 2007.</p>
 *
 * <h2>Technical note</h2>
 * <p>This is a Spring-boot application. Database connections and transactions is delegated to Spring/Hibernate as well</p>
 * @author Guillaume Diaz
 * @version 1.0
 * @since 2019/11
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"eu.daxiongmao.core.dao"})
@EnableTransactionManagement
public class DaxiongmaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaxiongmaoApplication.class, args);
	}

}
