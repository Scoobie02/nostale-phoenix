package pl.wsi.nostale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class NostaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(NostaleApplication.class, args);
	}

	@RequestMapping("/")
	@RestController
	static class HelloController {

		@GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
		public String hello() {
			return "Hello madafaka";
		}
	}
}
