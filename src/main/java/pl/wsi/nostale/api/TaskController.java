package pl.wsi.nostale.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/")
@RestController
class TaskController {

	private final NostaleTasker nostaleTasker;

	TaskController(NostaleTasker nostaleTasker) {
		this.nostaleTasker = nostaleTasker;
	}

	@PostMapping(value = "/executeTask", produces = MediaType.APPLICATION_JSON_VALUE)
	public String executeTask(@RequestParam("steps") String steps, @RequestParam("interval") Long interval) {

		final List<String> listOfSteps = Arrays.stream(steps.split("\r\n")).toList();

		nostaleTasker.executeTask(listOfSteps, interval);

		return "{" + String.join(",\r\n", listOfSteps) + "}";
	}
}
