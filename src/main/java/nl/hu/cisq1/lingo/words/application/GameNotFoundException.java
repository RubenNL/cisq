package nl.hu.cisq1.lingo.words.application;

public class GameNotFoundException extends RuntimeException {
	public GameNotFoundException() {
		super("Game niet gevonden!");
	}
}
