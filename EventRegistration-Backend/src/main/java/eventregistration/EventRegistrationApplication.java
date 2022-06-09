package eventregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class EventRegistrationApplication {
	public static void main(String[] args) {
		SpringApplication.run(EventRegistrationApplication.class, args);
	}
}
