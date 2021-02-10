package nl.hu.cisq1.lingo.words.data;

import nl.hu.cisq1.lingo.words.domain.Round;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringRoundRepository extends JpaRepository<Round, Integer> {}
