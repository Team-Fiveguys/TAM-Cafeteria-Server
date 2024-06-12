package fiveguys.Tom.Cafeteria.Server;

import fiveguys.Tom.Cafeteria.Server.auth.feignClient.config.FeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableFeignClients(defaultConfiguration = FeignConfig.class)
@EnableJpaAuditing
@EnableScheduling
// 수동으로 FeignAutoConfiguration 가져오기
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class TomCafeteriaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TomCafeteriaServerApplication.class, args);
	}

}
