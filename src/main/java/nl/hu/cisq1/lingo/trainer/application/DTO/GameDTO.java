package nl.hu.cisq1.lingo.trainer.application.DTO;

import lombok.ToString;
import nl.hu.cisq1.lingo.trainer.domain.Game;

import java.util.List;
import java.util.stream.Collectors;
@ToString
public class GameDTO {
	public int gameId;
	public int score;
	public List<RoundDTO> rounds;
	public GameDTO(Game game) {
		this.score=game.getScore();
		this.rounds=game.getRounds().stream().map(RoundDTO::new).collect(Collectors.toList());
		this.gameId=game.getId();
	}
}
