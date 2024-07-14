package pl.wsi.nostale.api;

import java.util.Collection;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class NostaleTasker {

	private final NostaleApi nostaleApi;
	private final ScheduledExecutorService executorService;

	NostaleTasker(NostaleApi nostaleApi, ScheduledExecutorService executorService) {
		this.nostaleApi = nostaleApi;
		this.executorService = executorService;
	}

	public void executeTask(Collection<String> steps, Long timeInterval) {
		long i=0;
		for (String step : steps) {
			executorService.schedule(
					() -> nostaleApi.sendPacket(step), i*timeInterval, TimeUnit.SECONDS);
					//() -> System.out.println(step), i*timeInterval, TimeUnit.SECONDS);
			i++;
		}
	}
}
