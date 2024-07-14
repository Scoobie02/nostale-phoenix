package pl.wsi.nostale.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommandSenderController.class)
class CommandSenderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NostaleApi nostaleApi;

	@Test
	void nasnsna() throws Exception {
		shouldPass_whenGoodCallPerformed(1,2);
	}

	void shouldPass_whenGoodCallPerformed(int x, int y) throws Exception {
		this.mockMvc.perform(get("/commands/walk/" + x + "/" + y))
				.andExpect(status().isOk())
				.andExpect(content().string("false"));

		verify(nostaleApi).playerWalk(x,y);
	}



}