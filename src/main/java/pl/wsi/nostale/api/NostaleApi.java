package pl.wsi.nostale.api;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NostaleApi {
	public static enum Type {
		packet_send,
		packet_recv,
		attack,
		player_skill,
		player_walk,
		pet_skill,
		partner_skill,
		pets_walk,
		pick_up,
		collect,
		start_bot,
		stop_bot,
		continue_bot,
		load_settings,
		start_minigame_bot,
		stop_minigame_bot,
		query_player_info,
		query_inventory,
		query_skills_info,
		query_map_entities
	}

	private static final String HOST = "127.0.0.1";

	private final Socket socket;
	private boolean doWork;
	private final Queue<String> messages;
	Thread worker = new Thread(this::work);

	public NostaleApi(int port) throws IOException {
		socket = new Socket(HOST, port);
		doWork = true;
		messages = new ConcurrentLinkedQueue<>();

		worker.start();
	}

	private int sendData(String data) throws IOException {
		String buffer = data + "\u0001";
		byte[] bytes = buffer.getBytes();
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(bytes);
		outputStream.flush();
		return bytes.length;
	}

	private void work() {
		int bufferSize = 4096;
		byte[] buffer = new byte[bufferSize];
		StringBuilder data = new StringBuilder();

		try (
				InputStream inputStream = socket.getInputStream()) {
			while (doWork) {
				int bytesRead = inputStream.read(buffer);

				if (bytesRead <= 0) {
					break;
				}

				data.append(new String(buffer, 0, bytesRead));
				int delimPos = data.indexOf("\u0001");

				while (delimPos != -1) {
					String msg = data.substring(0, delimPos);
					data.delete(0, delimPos + 1);
					messages.add(msg);
					delimPos = data.indexOf("\u0001");
				}
			}
		} catch (IOException e) {
			// Handle any exceptions here
			e.printStackTrace();
		}
	}

	public boolean isWorking() {
		return doWork;
	}

	public void close() {
		if (isWorking()) {
			doWork = false;
		}
		try {
			worker.join();
			socket.close();
		} catch (Exception e) {
			// Handle any exceptions here
			e.printStackTrace();
		}
	}

	public String getMessage() {
		if (messages.isEmpty()) {
			return "";
		}
		return messages.poll();
	}

	public boolean isEmpty() {
		return messages.isEmpty();
	}

	public boolean sendPacket(String packet) throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.packet_send.ordinal());
		data.put("packet", packet);

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean recvPacket(String packet) throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.packet_recv.ordinal());
		data.put("packet", packet);

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean attackMonster(int monsterId) throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.attack.ordinal());
		data.put("monster_id", monsterId);

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean usePlayerSkill(int monsterId, int skillId) throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.player_skill.ordinal());
		data.put("monster_id", monsterId);
		data.put("skill_id", skillId);

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean playerWalk(int x, int y) throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.player_walk.ordinal());
		data.put("x", x);
		data.put("y", y);

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean usePetSkill(int monsterId, int skillId) throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.pet_skill.ordinal());
		data.put("monster_id", monsterId);
		data.put("skill_id", skillId);

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean usePartnerSkill(int monsterId, int skillId) throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.partner_skill.ordinal());
		data.put("monster_id", monsterId);
		data.put("skill_id", skillId);

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean petsWalk(int x, int y) throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.pets_walk.ordinal());
		data.put("x", x);
		data.put("y", y);

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean pickUp(int itemId) throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.pick_up.ordinal());
		data.put("item_id", itemId);

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean collect(int npcId) throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.collect.ordinal());
		data.put("npc_id", npcId);

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean startBot() throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.start_bot.ordinal());

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean stopBot() throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.stop_bot.ordinal());

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean continueBot() throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.continue_bot.ordinal());

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean loadSettings(String settingsPath) throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.load_settings.ordinal());
		data.put("path", settingsPath);

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean startMinigameBot() throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.start_minigame_bot.ordinal());

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean stopMinigameBot() throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.stop_minigame_bot.ordinal());

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean queryPlayerInformation() throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.query_player_info.ordinal());

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean queryInventory() throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.query_inventory.ordinal());

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean querySkillsInfo() throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.query_skills_info.ordinal());

		String json_data = new Gson().toJson(data);

		return sendData(json_data) == json_data.length() + 1;
	}

	public boolean queryMapEntities() throws IOException {
		Map<String, Object> data = new HashMap<>();
		data.put("type", Type.query_map_entities.ordinal());

		String json_data = new Gson().toJson(data);
		return sendData(json_data) == json_data.length() + 1;
	}
}
