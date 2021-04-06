package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
	/**
	 * Alle andere trainer tests staan in {@link nl.hu.cisq1.lingo.trainer.application.TrainerServiceTest}
	 */
}
