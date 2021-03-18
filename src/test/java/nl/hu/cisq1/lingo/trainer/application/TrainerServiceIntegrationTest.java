package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TrainerServiceIntegrationTest {
	@Autowired
	TrainerService service;
	@Autowired
	SpringGameRepository repository;
	@Test
	@DisplayName("new Round is saved")
	void newRoundIsSavedTest() {
		Game game=service.newGame();
		service.newRound(game.getId());
		game=service.getGame(game.getId());
		assertEquals(1,game.getRounds().size());
	}
	@Test
	@DisplayName("game not found")
	void gameNotFound() {
		assertThrows(GameNotFoundException.class,()->service.getGame(0));
	}
	@Test
	@DisplayName("guessTest correct")
	void guessTest() {
		WordService wordService= Mockito.mock(WordService.class);
		Mockito.when(wordService.provideRandomWord(Mockito.anyInt())).thenReturn("testab");
		Mockito.when(wordService.doesWordExist(Mockito.anyString())).thenReturn(true);
		service=new TrainerService(repository,wordService);
		int gameId=service.newGame().getId();
		service.newRound(gameId);
		service.guess(gameId,"testab");
		assertEquals(State.WON,service.getGame(gameId).getLastRound().getState());
	}
	@Test
	@DisplayName("Word not exist error test")
	void wordNotExistsTest() {
		WordService wordService= Mockito.mock(WordService.class);
		Mockito.when(wordService.doesWordExist(Mockito.anyString())).thenReturn(false);
		Mockito.when(wordService.provideRandomWord(Mockito.anyInt())).thenReturn("testab");
		service=new TrainerService(repository,wordService);
		int gameId=service.newGame().getId();
		service.newRound(gameId);
		assertThrows(WordNotExistException.class,()->service.guess(gameId,"testab"));
		assertTrue(service.getGame(gameId).getLastRound().getFeedbackList().isEmpty());
	}
	@Test
	@DisplayName("word does exist no error test")
	void wordExistsTest() {
		WordService wordService= Mockito.mock(WordService.class);
		Mockito.when(wordService.doesWordExist(Mockito.anyString())).thenReturn(true);
		Mockito.when(wordService.provideRandomWord(Mockito.anyInt())).thenReturn("testab");
		service=new TrainerService(repository,wordService);
		int gameId=service.newGame().getId();
		service.newRound(gameId);
		assertDoesNotThrow(()->service.guess(gameId,"testab"));
		assertFalse(service.getGame(gameId).getLastRound().getFeedbackList().isEmpty());
	}
	@Test
	@DisplayName("getProgressTest")
	//overgenomen van guessTest
	void getProgressTest() {
		WordService wordService= Mockito.mock(WordService.class);
		Mockito.when(wordService.provideRandomWord(Mockito.anyInt())).thenReturn("testab");
		Mockito.when(wordService.doesWordExist(Mockito.anyString())).thenReturn(true);
		service=new TrainerService(repository,wordService);
		int gameId=service.newGame().getId();
		service.newRound(gameId);
		service.guess(gameId,"testab");
		Game game=service.getGame(gameId);
		repository= Mockito.mock(SpringGameRepository.class);
		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(game));
		//FIXME dit is natuurlijk niet netjes. moet mischien herschreven gaan worden.
		assertEquals("GameDTO(gameId="+gameId+", score=25, rounds=[RoundDTO(feedbackList=[FeedbackDTO(marks=[CORRECT, CORRECT, CORRECT, CORRECT, CORRECT, CORRECT], hint=[t, e, s, t, a, b], attempt=testab)], currentHint=[t, e, s, t, a, b])])",service.getProgress(gameId).toString());
	}
}
