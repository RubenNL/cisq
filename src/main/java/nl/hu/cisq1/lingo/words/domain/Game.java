package nl.hu.cisq1.lingo.words.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.hu.cisq1.lingo.words.domain.exception.IllegalActionException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Game {
	@Id @GeneratedValue Integer id;
	@OneToMany(mappedBy="game",fetch=FetchType.EAGER,cascade=CascadeType.ALL) private List<Round> rounds=new ArrayList<>();
	private boolean canStartNewRound() {
		if(getLastRound()==null) return true;
		return getLastRound().wordGuessed();
	}
	public void startNewRound(Word word) {
		if(!canStartNewRound()) throw new IllegalActionException("Kan geen nieuwe ronde starten!");
		this.rounds.add(new Round(word));
	}
	public Integer getScore() {
		return rounds.stream().mapToInt(Round::getScore).sum();
	}
	public Round getLastRound() {
		if(this.rounds.isEmpty()) return null;
		return this.rounds.get(this.rounds.size() - 1);
	}
}
