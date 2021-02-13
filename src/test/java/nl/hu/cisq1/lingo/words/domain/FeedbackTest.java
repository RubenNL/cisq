package nl.hu.cisq1.lingo.words.domain;

import nl.hu.cisq1.lingo.words.domain.exception.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
	private static Stream<Arguments> provideGuessedTests() {
		return Stream.of(
				Arguments.of("woord", List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT),true),
				Arguments.of("woord", List.of(Mark.PRESENT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT),false)
		);
	}
	@ParameterizedTest
	@DisplayName("word is guessed multi-test")
	@MethodSource("provideGuessedTests")
	void giveHintTest(String attempt, List marks,Boolean expected) {
		Feedback feedback=new Feedback(attempt,marks);
		assertEquals(expected,feedback.isWordGuessed());
	}
	private static Stream<Arguments> provideValidTests() {
		return Stream.of(
				Arguments.of("woord", List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT),true),
				Arguments.of("woord", List.of(Mark.INVALID,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT),false)
		);
	}
	@ParameterizedTest
	@DisplayName("word is valid multi-test")
	@MethodSource("provideValidTests")
	void validTests(String attempt, List marks,Boolean expected) {
		Feedback feedback=new Feedback(attempt,marks);
		assertEquals(expected,feedback.isWordValid());
	}
	@Test
	@DisplayName("invalid mark size test")
	void markSizeIncorrect() {
		assertThrows(InvalidFeedbackException.class,() -> new Feedback("woord", List.of(Mark.CORRECT)));
	}
}