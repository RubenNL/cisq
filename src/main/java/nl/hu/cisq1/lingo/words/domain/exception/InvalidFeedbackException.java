package nl.hu.cisq1.lingo.words.domain.exception;

public class InvalidFeedbackException extends RuntimeException {
    public InvalidFeedbackException(Integer markLength,Integer attemptLength) {
        super("Feedback marks of size "+markLength+" Is invalid with attemptlength "+attemptLength+"!");
    }
}