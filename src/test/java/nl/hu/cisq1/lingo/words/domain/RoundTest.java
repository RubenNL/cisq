package nl.hu.cisq1.lingo.words.domain;

import nl.hu.cisq1.lingo.words.domain.exception.IllegalActionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.SecondaryTable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

	@Test
	@DisplayName("addFeedback simple test")
	void addFeedback() {
		Round round=new Round(new Word("abcdef"));
		Feedback feedback=round.addFeedback("testab");
		Feedback expected=new Feedback("testab","abcdef");
		assertEquals(expected,feedback,"Feedback is correct gereturned");
		assertEquals(List.of(expected),round.getFeedbackList(),"List is ingevuld");
	}
	@Test
	@DisplayName("addFeedback max test")
	void addFeedbackMaxTest() {
		Round round=new Round(new Word("abcdef"));
		assertDoesNotThrow(()->round.addFeedback("testab"),"first does not throw");
		assertDoesNotThrow(()->round.addFeedback("testac"),"second does not throw");
		assertDoesNotThrow(()->round.addFeedback("testad"),"third does not throw");
		assertDoesNotThrow(()->round.addFeedback("testae"),"fourth does not throw");
		assertDoesNotThrow(()->round.addFeedback("testaf"),"fifth does not throw");
		assertThrows(IllegalActionException.class,()->round.addFeedback("testag"),"sixth does throw");
		assertThrows(IllegalActionException.class,()->round.addFeedback("testah"),"seventh does also throw");
		assertEquals(5,round.getFeedbackList().size(),"only 5 feedbacks are added");
	}
}