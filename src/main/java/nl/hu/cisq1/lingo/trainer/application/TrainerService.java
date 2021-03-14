package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.application.DTO.GameDTO;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TrainerService {
	private final SpringGameRepository repository;
	private final WordService wordService;
	public TrainerService(SpringGameRepository repository, WordService wordService) {
		this.repository = repository;
		this.wordService = wordService;
	}
	Game getGame(int gameId) {
		return repository.findById(gameId).orElseThrow(GameNotFoundException::new);
	}
	public GameDTO getProgress(int gameId) {
		return new GameDTO(getGame(gameId));
	}
	public Game newGame() {
		return repository.save(new Game());
	}
	public void guess(int gameId, String guess) {
		if(!wordService.doesWordExist(guess)) throw new WordNotExistException(guess);
		Game game=getGame(gameId);
		game.getLastRound().addFeedback(guess);
		repository.save(game);
	}
	public void newRound(int gameId) {
		Game game=getGame(gameId);
		String wordString=wordService.provideRandomWord(6);
		Word word=new Word(wordString);
		game.startNewRound(word);
		repository.save(game);
	}
}
