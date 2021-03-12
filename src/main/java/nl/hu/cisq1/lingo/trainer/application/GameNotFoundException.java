package nl.hu.cisq1.lingo.trainer.application;

public class GameNotFoundException extends RuntimeException {
	public GameNotFoundException() {
		super("Game niet gevonden!");
	}
}
