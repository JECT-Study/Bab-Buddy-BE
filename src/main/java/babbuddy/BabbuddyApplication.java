package babbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BabbuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BabbuddyApplication.class, args);
	}

}
