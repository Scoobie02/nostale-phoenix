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

	@GetMapping("/send/{packet}")
	public Boolean send(@PathVariable("packet") String packet) throws IOException {
		return nostaleApi.sendPacket(packet);
	}

}
