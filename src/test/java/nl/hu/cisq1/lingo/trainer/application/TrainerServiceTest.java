package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TrainerServiceTest {
	TrainerService service;
	@MockBean
	SpringGameRepository repository;
	@MockBean
	WordService wordService;
	Game game;
	@BeforeEach
	void init() {
		Mockito.when(wordService.provideRandomWord(Mockito.anyInt())).thenReturn("testab");
		Mockito.when(wordService.doesWordExist(Mockito.anyString())).thenReturn(true);
		game=new Game();
		Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(game));
		Answer<Game> answer = invocation -> {
			game = invocation.getArgument(0, Game.class);
			game.setId(1);
			return game;
		};
		Mockito.when(repository.save(Mockito.any(Game.class))).thenAnswer(answer);
		service=new TrainerService(repository,wordService);
	}
	@Test
	@DisplayName("guessTest correct")
	void guessTest() {
		int gameId=service.newGame().getId();
		service.newRound(gameId);
		service.guess(gameId,"testab");
		assertEquals(State.WON,service.getGame(gameId).getLastRound().getState());
	}
	@Test
	@DisplayName("Word not exist error test")
	void wordNotExistsTest() {
		Mockito.when(wordService.doesWordExist(Mockito.anyString())).thenReturn(false);
		service=new TrainerService(repository,wordService);
		int gameId=service.newGame().getId();
		service.newRound(gameId);
		assertThrows(WordNotExistException.class,()->service.guess(gameId,"testab"));
		assertTrue(service.getGame(gameId).getLastRound().getFeedbackList().isEmpty());
	}
	@Test
	@DisplayName("word does exist no error test")
	void wordExistsTest() {
		int gameId=service.newGame().getId();
		service.newRound(gameId);
		assertDoesNotThrow(()->service.guess(gameId,"testab"));
		assertFalse(service.getGame(gameId).getLastRound().getFeedbackList().isEmpty());
	}
	@Test
	@DisplayName("getProgressTest")
	//overgenomen van guessTest
	void getProgressTest() {
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
