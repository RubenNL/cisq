package nl.hu.cisq1.lingo.words.domain;

import org.junit.jupiter.api.DisplayName;
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
		feedback.setHint(oldHint,wordToGuess);
		assertEquals(expected,feedback.getHint());
	}
	private static Stream<Arguments> provideMarksTest() {
		return Stream.of(
				Arguments.of("woord", "aaaaa",List.of(Mark.ABSENT,Mark.ABSENT,Mark.ABSENT,Mark.ABSENT,Mark.ABSENT),"Complete different word"),
				Arguments.of("woord", "a",List.of(Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID),"invalid length"),
				Arguments.of("woord","waare",List.of(Mark.CORRECT,Mark.ABSENT,Mark.ABSENT,Mark.CORRECT,Mark.ABSENT),"1 char correct"),
				Arguments.of("woord","waboo",List.of(Mark.CORRECT,Mark.ABSENT,Mark.ABSENT,Mark.PRESENT,Mark.PRESENT),"2 chars misplaced"),
				Arguments.of("woord","waood",List.of(Mark.CORRECT,Mark.ABSENT,Mark.CORRECT,Mark.PRESENT,Mark.CORRECT),"1/2 chars misplaced"),
				Arguments.of("woord","woood",List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.ABSENT,Mark.CORRECT),"too much of correct char"),
				Arguments.of("ababa","babab",List.of(Mark.PRESENT,Mark.PRESENT,Mark.PRESENT,Mark.PRESENT,Mark.ABSENT),"repeating pattern"),
				Arguments.of("aabb","bbaa",List.of(Mark.PRESENT,Mark.PRESENT,Mark.PRESENT,Mark.PRESENT),"reversed")
		);
	}
	@ParameterizedTest
	@DisplayName("generate marks multi-test")
	@MethodSource("provideMarksTest")
	void marksTest(String word, String guess,List<Mark> expected,String message) {
		assertEquals(expected,Feedback.generateMarks(guess,word),message);
	}
}