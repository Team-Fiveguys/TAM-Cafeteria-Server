package fiveguys.Tom.Cafeteria.Server;

import fiveguys.Tom.Cafeteria.Server.auth.feignClient.config.FeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients(defaultConfiguration = FeignConfig.class)
@EnableJpaAuditing
@EnableScheduling
public class TomCafeteriaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TomCafeteriaServerApplication.class, args);
	}

}
