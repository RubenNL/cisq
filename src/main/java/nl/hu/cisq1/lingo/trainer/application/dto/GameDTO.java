package nl.hu.cisq1.lingo.trainer.application.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;
import nl.hu.cisq1.lingo.trainer.domain.Game;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
@ToString
@AllArgsConstructor
public class GameDTO implements Serializable {
	public final int gameId;
	public final int score;
	public final List<RoundDTO> rounds;
	public GameDTO(Game game) {
		this.score=game.getScore();
		this.rounds=game.getRounds().stream().map(RoundDTO::new).collect(Collectors.toList());
		this.gameId=game.getId();
	}
}
