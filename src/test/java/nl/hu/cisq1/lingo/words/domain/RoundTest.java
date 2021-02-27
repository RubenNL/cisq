package nl.hu.cisq1.lingo.words.domain;

import nl.hu.cisq1.lingo.words.domain.exception.IllegalActionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
	//Veel van deze tests zouden beter met ParameterizedTest gedaan kunnen worden, maar ik kreeg dat niet netjes.
	@Test
	@DisplayName("addFeedback simple test")
	void addFeedback() {
		Round round=new Round(new Word("abcdef"));
		Feedback feedback=round.addFeedback("testab");
		Feedback expected=new Feedback("testab",Feedback.generateMarks("testab","abcdef"));
		assertEquals(expected,feedback,"Feedback is correct gereturned");
		assertEquals(List.of(expected),round.getFeedbackList(),"List is ingevuld");
	}
	@Test
	@DisplayName("addFeedback max test")
	void addFeedbackMaxTest() {
		Round round=new Round(new Word("abcdef"));
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
		Round round=new Round(new Word("abcdef"));
		round.addFeedback("abcdef");
		assertThrows(IllegalActionException.class,()->round.addFeedback("abcdef"));
	}
	@Test
	@DisplayName("wordGuessed empty test")
	void wordGuessedEmptyTest() {
		Round round=new Round(new Word("abcdef"));
		assertFalse(round.wordGuessed(),"word should not be guessed when empty");
	}
	@Test
	@DisplayName("wordGuessed incorrect 1 test")
	void wordGuessedInvalidTest() {
		Round round=new Round(new Word("abcdef"));
		round.addFeedback("abcdeg");
		assertFalse(round.wordGuessed(),"word should not be guessed with 1 incorrect guess");
	}
	@Test
	@DisplayName("wordGuessed correct 1 test")
	void wordGuessedCorrectTest() {
		Round round=new Round(new Word("abcdef"));
		round.addFeedback("abcdef");
		assertTrue(round.wordGuessed(),"word should be guessed with 1 correct guess");
	}
	@Test
	@DisplayName("score empty test")
	void scoreEmptyTest() {
		Round round=new Round(new Word("abcdef"));
		assertEquals(0,round.getScore());
	}
	@Test
	@DisplayName("score 1 failed test")
	void score1failedTest() {
		Round round=new Round(new Word("abcdef"));
		round.addFeedback("abcdeg");
		assertEquals(0,round.getScore());
	}
	@Test
	@DisplayName("score 1 correct test")
	void score1correctTest() {
		Round round=new Round(new Word("abcdef"));
		round.addFeedback("abcdef");
		assertEquals(25,round.getScore());
	}
	@Test
	@DisplayName("score 1 failed 1 correct test")
	void score1failed1correctTest() {
		Round round=new Round(new Word("abcdef"));
		round.addFeedback("abcdeg");
		round.addFeedback("abcdef");
		assertEquals(20,round.getScore());
	}
	@Test
	@DisplayName("score 5 failed test")
	void score5failedTest() {
		Round round=new Round(new Word("abcdef"));
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
		Round round=new Round(new Word("abcdef"));
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
		Round round=new Round(new Word("abcdef"));
		assertEquals(List.of("a",".",".",".",".","."),round.giveHint());
	}
	@Test
	@DisplayName("single feedback geen correct test")
	void singleFeedbackNoCorrectTest() {
		Round round=new Round(new Word("abcdef"));
		round.addFeedback("azzzzz");
		assertEquals(List.of("a",".",".",".",".","."),round.giveHint());
	}
	@Test
	@DisplayName("single feedback invalid")
	void singleFeedbackInvalidTest() {
		Round round=new Round(new Word("abcdef"));
		round.addFeedback("a");
		assertEquals(List.of("a",".",".",".",".","."),round.giveHint());
	}
	@Test
	@DisplayName("correctFeedback test")
	void feedbackCorrectTest() {
		Round round=new Round(new Word("abcdef"));
		round.addFeedback("afcdbe");
		assertEquals(List.of("a",".","c","d",".","."),round.giveHint());
	}
	@Test
	@DisplayName("1xinvalid 1xvalid test")
	void feedbackvalidinvalid() {
		Round round=new Round(new Word("abcdef"));
		round.addFeedback("a");
		round.addFeedback("acbedf");
		assertEquals(List.of("a",".",".",".",".","f"),round.giveHint());
	}

}