package nl.hu.cisq1.lingo.words.domain;

import nl.hu.cisq1.lingo.words.domain.exception.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
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
	void giveHintTest(String attempt, List<Mark> marks,Boolean expected) {
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
	void validTests(String attempt, List<Mark> marks,Boolean expected) {
		Feedback feedback=new Feedback(attempt,marks);
		assertEquals(expected,feedback.isWordValid());
	}
	@Test
	@DisplayName("invalid mark size test")
	void markSizeIncorrect() {
		assertThrows(InvalidFeedbackException.class,() -> new Feedback("woord", List.of(Mark.CORRECT)));
	}
	private static Stream<Arguments> provideHintTests() {
		return Stream.of(
				Arguments.of("woord", List.of(Mark.CORRECT,Mark.ABSENT,Mark.CORRECT,Mark.ABSENT,Mark.ABSENT), "w...d","w.o.d"),
				Arguments.of("woord", List.of(Mark.CORRECT,Mark.PRESENT,Mark.PRESENT,Mark.CORRECT,Mark.ABSENT), "w...d","w..rd"),
				Arguments.of("woord", List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT), "w...d","woord")
		);
	}
	@ParameterizedTest
	@DisplayName("hint multi-test")
	@MethodSource("provideHintTests")
	void hintTests(String wordToGuess, List<Mark> marks, String oldHintString,String expectedString) {
		Feedback feedback=new Feedback("woord",marks);
		List<String> oldHint=Arrays.asList(oldHintString.split("")); //Tests written with strings, to make it readable.
		List<String> expected=Arrays.asList(expectedString.split(""));
		assertEquals(expected,feedback.giveHint(oldHint,wordToGuess));
	}

}