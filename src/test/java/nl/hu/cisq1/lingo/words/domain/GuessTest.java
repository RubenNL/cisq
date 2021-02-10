package nl.hu.cisq1.lingo.words.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GuessTest {
    @Test
    @DisplayName("Guess constructor works")
    void GuessIsSet() {
        Guess guess = new Guess("woord");
        String word = guess.getWord();
        assertEquals("woord", word);
    }
}
