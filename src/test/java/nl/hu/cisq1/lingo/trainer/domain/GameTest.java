package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exceptions.IllegalActionException;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
	Game game;
	@BeforeEach
	void beforeEach() {
		game=new Game();
	}
	@Test
	@DisplayName("startNewRound empty Test")
	void startNewRoundEmpty() {
		assertDoesNotThrow(()->game.startNewRound(new Word("test")));
	}
	@Test
	@DisplayName("startNewRound playing Test")
	void startNewRoundStartedRound() {
		game.startNewRound(new Word("test"));
		Word word=new Word("test");
		assertThrows(IllegalActionException.class, () -> game.startNewRound(word));
	}
	@Test
	@DisplayName("startNewRound win before test")
	void startNewRoundWinTest() {
		game.startNewRound(new Word("test"));
		Round round=game.getLastRound();
		round.addFeedback("test");
		assertDoesNotThrow(()->game.startNewRound(new Word("test")));
	}
	@Test
	@DisplayName("startNewRound lost before test")
	void startNewRoundLostTest() {
		game.startNewRound(new Word("test"));
		Round round = game.getLastRound();
		round.addFeedback("abcd");
		round.addFeedback("abcd");
		round.addFeedback("abcd");
		round.addFeedback("abcd");
		round.addFeedback("abcd");
		Word word=new Word("test");
		assertDoesNotThrow(() -> game.startNewRound(word));
	}
	@Test
	@DisplayName("emptyScore test")
	void emptyScoreTest() {
		assertEquals(0,game.getScore());
	}
	@Test
	@DisplayName("single empty round score test")
	void emptyRoundScoreTest() {
		game.startNewRound(new Word("abcd"));
		assertEquals(0,game.getScore());
	}
	@Test
	@DisplayName("single round score test")
	void singleRoundScoreTest() {
		game.startNewRound(new Word("abcd"));
		Round round=game.getLastRound();
		round.addFeedback("abcd");
		assertEquals(25,game.getScore());
	}
	@Test
	@DisplayName("two rounds score test")
	void twoRoundsScoreTest() {
		game.startNewRound(new Word("abcd"));
		Round round = game.getLastRound();
		round.addFeedback("abcd");
		game.startNewRound(new Word("abcd"));
		round = game.getLastRound();
		round.addFeedback("adcb");
		round.addFeedback("abcd");
		assertEquals(45, game.getScore());
	}
	private static Stream<Arguments> provideNextSizeTests() {
		return Stream.of(
				Arguments.of(0,5),
				Arguments.of(1,6),
				Arguments.of(2,7),
				Arguments.of(3,5),
				Arguments.of(4,6),
				Arguments.of(5,7),
				Arguments.of(6,5)
		);
	}
	@ParameterizedTest
	@DisplayName("NextSize test")
	@MethodSource("provideNextSizeTests")
	void nextSizeTest(int roundAmounts,int expected) {
		Round round= Mockito.spy(Round.class);
		Mockito.when(round.getState()).thenReturn(State.WON);
		for(int i=0;i<roundAmounts;i++) game.getRounds().add(round);
		assertEquals(expected,game.getNextSize());
	}
}
