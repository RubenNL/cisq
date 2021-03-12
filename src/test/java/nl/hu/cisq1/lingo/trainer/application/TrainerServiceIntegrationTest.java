package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class TrainerServiceIntegrationTest {
	@Autowired
	TrainerService service;
	@Autowired
	SpringGameRepository repository;
	@Test
	@DisplayName("new Round is saved")
	void newRoundIsSavedTest() {
		Game game=service.newGame();
		service.newRound(game.getId());
		game=service.getGame(game.getId());
		assertEquals(1,game.getRounds().size());
	}
	@Test
	@DisplayName("game not found")
	void gameNotFound() {
		assertThrows(GameNotFoundException.class,()->service.getGame(0));
	}
	@Test
	@DisplayName("guessTest correct")
	void guessTest() {
		WordService wordService= Mockito.mock(WordService.class);
		Mockito.when(wordService.provideRandomWord(Mockito.anyInt())).thenReturn("testab");
		service=new TrainerService(repository,wordService);
		int gameId=service.newGame().getId();
		service.newRound(gameId);
		service.guess(gameId,"testab");
		assertEquals(State.WON,service.getGame(gameId).getLastRound().getState());
	}
}
