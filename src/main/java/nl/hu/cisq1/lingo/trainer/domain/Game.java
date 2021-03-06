package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.IllegalActionException;
import nl.hu.cisq1.lingo.words.domain.Word;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Game {
	@Id @GeneratedValue Integer id;
	@OneToMany(cascade=CascadeType.ALL) private List<Round> rounds=new ArrayList<>();
	private boolean canStartNewRound() {
		if(getLastRound()==null) return true;
		return getLastRound().getState()!=State.ACTIVE;
	}
	public void startNewRound(Word word) {
		if(!canStartNewRound()) throw new IllegalActionException("Kan geen nieuwe ronde starten!");
		this.rounds.add(new Round(word.getValue()));
	}
	public Integer getScore() {
		return rounds.stream().mapToInt(Round::getScore).sum();
	}
	public Round getLastRound() {
		if(this.rounds.isEmpty()) return null;
		return this.rounds.get(this.rounds.size() - 1);
	}
	public Integer getNextSize() {
		return rounds.size()%3+5;
	}
}
