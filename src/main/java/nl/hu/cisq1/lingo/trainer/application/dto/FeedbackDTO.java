package nl.hu.cisq1.lingo.trainer.application.dto;

import lombok.ToString;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.Mark;

import java.util.List;
import java.util.stream.Collectors;

@ToString
public class FeedbackDTO {
	public final List<String> marks;
	public final List<String> hint;
	public final String attempt;
	public FeedbackDTO(Feedback feedback) {
		this.marks=feedback.getMarks().stream().map(Mark::toString).collect(Collectors.toList());
		this.hint=feedback.getHint();
		this.attempt=feedback.getAttempt();
	}
}
