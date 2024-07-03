package pl.wsi.nostale.api;

import lombok.AllArgsConstructor;
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

	@GetMapping("/walk/{x}/{y}")
	public Boolean send(@PathVariable("x") Integer x, @PathVariable("y") Integer y) throws IOException {
		return nostaleApi.playerWalk(x, y);
	}

	@GetMapping("/querryPlayerInfo")
	public Boolean querryPlayerInfo() throws IOException {
		return nostaleApi.queryPlayerInformation();
	}

}
