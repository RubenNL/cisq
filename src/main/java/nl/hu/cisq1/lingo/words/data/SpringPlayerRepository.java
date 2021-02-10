package nl.hu.cisq1.lingo.words.data;

import nl.hu.cisq1.lingo.words.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringPlayerRepository extends JpaRepository<Player, Integer> {}
