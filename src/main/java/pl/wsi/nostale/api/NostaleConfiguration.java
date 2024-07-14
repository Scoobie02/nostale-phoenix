package pl.wsi.nostale.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.Executors;

@Configuration
class NostaleConfiguration {

	@Value("${bot.port}")
	private String botPort;

	@Bean
	public NostaleApi nostaleApi() throws IOException {
		return new NostaleApi(Integer.parseInt(botPort));
	}

	@Bean
	NostaleTasker nostaleTasker(NostaleApi nostaleApi) {
		return new NostaleTasker(nostaleApi, Executors.newScheduledThreadPool(10));
	}

}

