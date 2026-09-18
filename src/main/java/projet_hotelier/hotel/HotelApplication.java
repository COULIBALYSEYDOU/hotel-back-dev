package projet_hotelier.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EntityScan(basePackages = {
    "projet_hotelier.hotel.core",
    "projet_hotelier.hotel.module",
    "projet_hotelier.hotel.shared"
})
@EnableJpaRepositories(basePackages = {
    "projet_hotelier.hotel.repository",
    "projet_hotelier.hotel.module"
})
@ComponentScan(basePackages = "projet_hotelier.hotel")
public class HotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}

}
