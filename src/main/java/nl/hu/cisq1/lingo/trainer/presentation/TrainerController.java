package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.application.WordNotExistException;
import nl.hu.cisq1.lingo.trainer.application.dto.GameDTO;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.IllegalActionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("trainer")
public class TrainerController {
	private final TrainerService service;

	public TrainerController(TrainerService service) {
		this.service = service;
	}
	private void throwIfNotExists(int gameId) {
		try {
			service.getProgress(gameId);
		} catch(GameNotFoundException exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/{gameId}")
	public GameDTO getProgress(@PathVariable int gameId) {
		throwIfNotExists(gameId);
		return service.getProgress(gameId);
	}
	@PostMapping("/create")
	public int newGame() {
		return service.newGame().getId();
	}//Geen try/catch, omdat dit geen fouten zou moeten kunnen geven.
	@PostMapping("/{gameId}/guess")
	public void newGuess(@PathVariable int gameId, @RequestParam String guess) {
		throwIfNotExists(gameId);
		try {
			service.guess(gameId, guess);
		} catch(WordNotExistException | IllegalActionException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/{gameId}/newRound")
	public void newRound(@PathVariable int gameId) {
		try {
			service.newRound(gameId);
		} catch(IllegalActionException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
}
