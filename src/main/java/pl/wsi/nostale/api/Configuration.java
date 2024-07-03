package pl.wsi.nostale.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@org.springframework.context.annotation.Configuration
class Configuration {

	@Value("${bot.port}")
	private String botPort;

	@Bean
	public NostaleApi nostaleApi() throws IOException {
		return new NostaleApi(Integer.parseInt(botPort));
	}

}

