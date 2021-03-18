package nl.hu.cisq1.lingo.trainer.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PresentationControllerIntegrationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private TrainerService service;
	@Autowired
	private ObjectMapper objectMapper;
	private int gameId;
	@BeforeEach
	void beforeAll() {
		gameId=service.newGame().getId();
	}
	@Test
	@DisplayName("gameNotExists 404")
	void getInvalidGame() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.get("/trainer/99999");
		mockMvc.perform(request)
				.andExpect(status().isNotFound());
	}
	@Test
	@DisplayName("invalid word 400")
	void invalidWordException() throws Exception {
		service.newRound(gameId);
		RequestBuilder request = MockMvcRequestBuilders
				.post("/trainer/"+gameId+"/guess").param("guess","abcdef");
		mockMvc.perform(request)
				.andExpect(status().isBadRequest());
		assertEquals(0,service.getProgress(gameId).rounds.get(0).feedbackList.size());
	}
	@Test
	@DisplayName("Valid guess")
	void validGuessTest() throws Exception {
		service.newRound(gameId);
		RequestBuilder request = MockMvcRequestBuilders
				.post("/trainer/"+gameId+"/guess").param("guess","testen");
		mockMvc.perform(request)
				.andExpect(status().isOk());
		assertEquals(1,service.getProgress(gameId).rounds.get(0).feedbackList.size());
	}
	@Test
	@DisplayName("game not exists guess")
	void gameNotExistGuess() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/trainer/99999/guess").param("guess","testen");
		mockMvc.perform(request)
				.andExpect(status().isNotFound());
	}
	@Test
	@DisplayName("new game returns gameId")
	void newGame() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/trainer/create");
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().string(""+(gameId+1)));
	}
	@Test
	@DisplayName("new round valid 200")
	void newRoundValid() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/trainer/"+gameId+"/newRound");
		mockMvc.perform(request)
				.andExpect(status().isOk());
	}
	@Test
	@DisplayName("new round already active 400")
	void newRoundAlreadyActive() throws Exception {
		service.newRound(gameId);
		RequestBuilder request = MockMvcRequestBuilders
				.post("/trainer/"+gameId+"/newRound");
		mockMvc.perform(request)
				.andExpect(status().isBadRequest());
	}

	private boolean isJSONValid(String test) {
		try {
			new JSONObject(test);
		} catch (JSONException ex) {
			// edited, to include @Arthur's comment
			// e.g. in case JSONArray is valid as well...
			try {
				new JSONArray(test);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}
	@Test
	@DisplayName("getProgress test")
	void getProgressTest() throws Exception {
		service.newRound(gameId);
		service.guess(gameId,"testen");
		RequestBuilder request = MockMvcRequestBuilders
				.get("/trainer/"+gameId);
		String json=mockMvc.perform(request)
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertTrue(isJSONValid(json));
	}
}
