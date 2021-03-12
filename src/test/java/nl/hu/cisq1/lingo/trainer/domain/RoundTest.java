package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exceptions.IllegalActionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
	Round round;
	@BeforeEach
	void beforeEach() {
		round=new Round("abcdef");
	}
	//Veel van deze tests zouden beter met ParameterizedTest gedaan kunnen worden, maar ik kreeg dat niet netjes.
	@Test
	@DisplayName("addFeedback simple test")
	void addFeedback() {
		Feedback feedback=round.addFeedback("testab");
		Feedback expected=new Feedback("testab",List.of("a",".",".",".",".","."),"abcdef");
		assertEquals(expected,feedback,"Feedback is correct gereturned");
		assertEquals(List.of(expected),round.getFeedbackList(),"List is ingevuld");
	}
	@Test
	@DisplayName("addFeedback max test")
	void addFeedbackMaxTest() {
		for(int i=0;i<Round.MAXROUNDS;i++) {
			assertDoesNotThrow(() -> round.addFeedback("testab"), "first to max does not throw");
		}
		assertThrows(IllegalActionException.class,()->round.addFeedback("testag"),"sixth does throw");
		assertThrows(IllegalActionException.class,()->round.addFeedback("testah"),"seventh does also throw");
		assertEquals(5,round.getFeedbackList().size(),"only 5 feedbacks are added");
	}
	@Test
	@DisplayName("prevent adding feedback when correct before")
	void addFeedbackAfterCorrect() {
		round.addFeedback("abcdef");
		assertThrows(IllegalActionException.class,()->round.addFeedback("abcdef"));
	}
	@Test
	@DisplayName("wordGuessed empty test")
	void wordGuessedEmptyTest() {
		assertFalse(round.wordGuessed(),"word should not be guessed when empty");
	}
	@Test
	@DisplayName("wordGuessed incorrect 1 test")
	void wordGuessedInvalidTest() {
		round.addFeedback("abcdeg");
		assertFalse(round.wordGuessed(),"word should not be guessed with 1 incorrect guess");
	}
	@Test
	@DisplayName("wordGuessed correct 1 test")
	void wordGuessedCorrectTest() {
		round.addFeedback("abcdef");
		assertTrue(round.wordGuessed(),"word should be guessed with 1 correct guess");
	}
	@Test
	@DisplayName("score empty test")
	void scoreEmptyTest() {
		assertEquals(0,round.getScore());
	}
	@Test
	@DisplayName("score 1 failed test")
	void score1failedTest() {
		round.addFeedback("abcdeg");
		assertEquals(0,round.getScore());
	}
	@Test
	@DisplayName("score 1 correct test")
	void score1correctTest() {
		round.addFeedback("abcdef");
		assertEquals(25,round.getScore());
	}
	@Test
	@DisplayName("score 1 failed 1 correct test")
	void score1failed1correctTest() {
		round.addFeedback("abcdeg");
		round.addFeedback("abcdef");
		assertEquals(20,round.getScore());
	}
	@Test
	@DisplayName("score 5 failed test")
	void score5failedTest() {
		round.addFeedback("abcdea");
		round.addFeedback("abcdeb");
		round.addFeedback("abcdec");
		round.addFeedback("abcded");
		round.addFeedback("abcdee");
		assertEquals(0,round.getScore());
	}
	@Test
	@DisplayName("score 4 failed 1 correct test")
	void score4failed1correctTest() {
		round.addFeedback("abcdea");
		round.addFeedback("abcdeb");
		round.addFeedback("abcdec");
		round.addFeedback("abcded");
		round.addFeedback("abcdef");
		assertEquals(5,round.getScore());
	}
	@Test
	@DisplayName("empty feedback test")
	void emptyFeedbackTest() {
		assertEquals(List.of("a",".",".",".",".","."),round.giveHint());
	}
	@Test
	@DisplayName("single feedback geen correct test")
	void singleFeedbackNoCorrectTest() {
		round.addFeedback("azzzzz");
		assertEquals(List.of("a",".",".",".",".","."),round.giveHint());
	}
	@Test
	@DisplayName("single feedback invalid")
	void singleFeedbackInvalidTest() {
		round.addFeedback("a");
		assertEquals(List.of("a",".",".",".",".","."),round.giveHint());
	}
	@Test
	@DisplayName("correctFeedback test")
	void feedbackCorrectTest() {
		round.addFeedback("afcdbe");
		assertEquals(List.of("a",".","c","d",".","."),round.giveHint());
	}
	@Test
	@DisplayName("1xinvalid 1xvalid test")
	void feedbackvalidinvalid() {
		round.addFeedback("a");
		round.addFeedback("acbedf");
		assertEquals(List.of("a",".",".",".",".","f"),round.giveHint());
	}
	@Test
	@DisplayName("round get State Empty")
	void getStateEmptyTest() {
		assertEquals(State.ACTIVE,round.getState());
	}
	@Test
	@DisplayName("round get State win")
	void getStateWonTest() {
		round.addFeedback("abcdef");
		assertEquals(State.WON,round.getState());
	}
	@Test
	@DisplayName("round get State lost")
	void getStateLostTest() {
		round.addFeedback("abcdea");
		round.addFeedback("abcdeb");
		round.addFeedback("abcdec");
		round.addFeedback("abcded");
		round.addFeedback("abcdee");
		assertEquals(State.LOST,round.getState());
	}
}