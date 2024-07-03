package pl.wsi.nostale.api;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/commands/")
@AllArgsConstructor
class CommandSenderController {

	private final NostaleApi nostaleApi;

	@GetMapping(value = "/walk/{x}/{y}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean send(@PathVariable("x") Integer x, @PathVariable("y") Integer y) throws IOException {
		return nostaleApi.playerWalk(x, y);
	}

	@GetMapping(value = "/querryPlayerInfo", produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean querryPlayerInfo() throws IOException {
		return nostaleApi.queryPlayerInformation();
	}

	@GetMapping(value = "/stopBot", produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean stopBot() throws IOException {
		return nostaleApi.stopBot();
	}

	@GetMapping(value = "/startBot", produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean startBot() throws IOException {
		return nostaleApi.startBot();
	}
}
