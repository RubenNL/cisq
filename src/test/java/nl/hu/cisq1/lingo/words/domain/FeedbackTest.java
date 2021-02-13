package nl.hu.cisq1.lingo.words.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
	@Test
	@DisplayName("word is guessed if all letters are correct")
	void wordIsGuessed() {
		Feedback feedback=new Feedback("woord", List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT));
		assertTrue(feedback.isWordGuessed());
	}
	@Test
	@DisplayName("word is not guessed if not all letters are correct")
	void wordIsNotGuessed() {
		Feedback feedback=new Feedback("woord", List.of(Mark.PRESENT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT));
		assertFalse(feedback.isWordGuessed());
	}
	@Test
	@DisplayName("word is valid if all letters are valid")
	void guessIsValid() {
		Feedback feedback=new Feedback("woord", List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT));
		assertTrue(feedback.isWordValid());
	}
	@Test
	@DisplayName("word is invalid when 1 char is invalid.")
	void guessIsInvalid() {
		Feedback feedback=new Feedback("woord", List.of(Mark.INVALID,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT));
		assertFalse(feedback.isWordValid());
	}
}