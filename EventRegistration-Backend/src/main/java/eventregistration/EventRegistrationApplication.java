package eventregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RestController
@SpringBootApplication
public class EventRegistrationApplication {
	private static Connection getConnection() throws URISyntaxException, SQLException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

		return DriverManager.getConnection(dbUrl, username, password);
	}

	public static void main(String[] args) {
		SpringApplication.run(EventRegistrationApplication.class, args);

	}

	@RequestMapping("/")
	public String greeting() {
		return "ECSE321 Event Registration Application - Backend base URL.\n"
				+ "Use the API methods to interact with the backend!";
	}
}
