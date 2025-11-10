package pet.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class PetStoreApplication {

	public static void main(String[] args) {
		// This boots the embedded Tomcat and starts the Spring context
        SpringApplication.run(PetStoreApplication.class, args);

	}

}
