package geektime.http2.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class JettyH2ServerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JettyH2ServerDemoApplication.class, args);
	}

	@RequestMapping("/hello")
	public String hello() {
		return "Hello World!";
	}
}
