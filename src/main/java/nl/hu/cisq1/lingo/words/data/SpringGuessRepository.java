package nl.hu.cisq1.lingo.words.data;

import nl.hu.cisq1.lingo.words.domain.Game;
import nl.hu.cisq1.lingo.words.domain.Guess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringGuessRepository extends JpaRepository<Guess, Integer> {}
