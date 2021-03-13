package nl.hu.cisq1.lingo.trainer.application;

public class WordNotExistException extends RuntimeException {
	WordNotExistException(String word) {
		super("Word "+word+ " does not exist!");
	}
}
