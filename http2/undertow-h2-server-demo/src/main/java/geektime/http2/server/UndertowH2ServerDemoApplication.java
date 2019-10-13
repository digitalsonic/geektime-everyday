package geektime.http2.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class UndertowH2ServerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UndertowH2ServerDemoApplication.class, args);
	}

	@RequestMapping("/hello")
	public String hello() {
		return "Hello World!";
	}
}
