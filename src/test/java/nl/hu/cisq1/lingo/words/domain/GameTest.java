package nl.hu.cisq1.lingo.words.domain;

import nl.hu.cisq1.lingo.words.domain.exception.IllegalActionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
	@Test
	@DisplayName("startNewRound empty Test")
	void startNewRoundEmpty() {
		Game game=new Game();
		assertDoesNotThrow(()->game.startNewRound(new Word("test")));
	}
	@Test
	@DisplayName("startNewRound playing Test")
	void startNewRoundStartedRound() {
		Game game=new Game();
		game.startNewRound(new Word("test"));
		assertThrows(IllegalActionException.class,()->game.startNewRound(new Word("test")));
	}
	@Test
	@DisplayName("startNewRound win before test")
	void startNewRoundWinTest() {
		Game game=new Game();
		game.startNewRound(new Word("test"));
		Round round=game.getLastRound();
		round.addFeedback("test");
		assertDoesNotThrow(()->game.startNewRound(new Word("test")));
	}
	@Test
	@DisplayName("startNewRound lost before test")
	void startNewRoundLostTest() {
		Game game = new Game();
		game.startNewRound(new Word("test"));
		Round round = game.getLastRound();
		round.addFeedback("abcd");
		round.addFeedback("abcd");
		round.addFeedback("abcd");
		round.addFeedback("abcd");
		round.addFeedback("abcd");
		assertThrows(IllegalActionException.class, () -> game.startNewRound(new Word("test")));
	}
	@Test
	@DisplayName("emptyScore test")
	void emptyScoreTest() {
		Game game = new Game();
		assertEquals(0,game.getScore());
	}
	@Test
	@DisplayName("single empty round score test")
	void emptyRoundScoreTest() {
		Game game = new Game();
		game.startNewRound(new Word("abcd"));
		assertEquals(0,game.getScore());
	}
	@Test
	@DisplayName("single round score test")
	void singleRoundScoreTest() {
		Game game = new Game();
		game.startNewRound(new Word("abcd"));
		Round round=game.getLastRound();
		round.addFeedback("abcd");
		assertEquals(25,game.getScore());
	}
	@Test
	@DisplayName("two rounds score test")
	void twoRoundsScoreTest() {
		Game game = new Game();
		game.startNewRound(new Word("abcd"));
		Round round = game.getLastRound();
		round.addFeedback("abcd");
		game.startNewRound(new Word("abcd"));
		round = game.getLastRound();
		round.addFeedback("adcb");
		round.addFeedback("abcd");
		assertEquals(45, game.getScore());
	}
}
