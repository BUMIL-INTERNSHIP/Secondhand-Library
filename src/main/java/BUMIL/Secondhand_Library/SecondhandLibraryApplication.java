package BUMIL.Secondhand_Library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SecondhandLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondhandLibraryApplication.class, args);
	}

}
