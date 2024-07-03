package pl.wsi.nostale;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.wsi.nostale.api.NostaleApi;

import java.io.IOException;

@SpringBootApplication
public class NostaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(NostaleApplication.class, args);

		// Put the port from the bot title, it should look something like
		// [Lv 99.(+80) CharacterName] - Phoenix Bot: 123123
		int port = 61258;

		try {
			NostaleApi api = new NostaleApi(port);
			while (api.isWorking()) {
				if (!api.isEmpty()) {
					String msg = api.getMessage();

					// This is an example of receiving messages.
					JsonObject jsonMsg = new Gson().fromJson(msg, JsonObject.class);
					int messageType = jsonMsg.get("type").getAsInt();
					if (messageType == NostaleApi.Type.packet_send.ordinal()) {
						System.out.println("[SEND]: " + jsonMsg.get("packet").getAsString());
					} else if (messageType == NostaleApi.Type.packet_recv.ordinal()) {
						System.out.println("[RECV]: " + jsonMsg.get("packet").getAsString());
					}
				} else {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// Handle InterruptedException if needed
						e.printStackTrace();
					}
				}
			}
			api.close();
		} catch (IOException e) {
			// Handle IOException if needed
			e.printStackTrace();
		}
	}

}
