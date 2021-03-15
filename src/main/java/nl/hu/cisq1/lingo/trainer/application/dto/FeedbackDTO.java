package nl.hu.cisq1.lingo.trainer.application.dto;

import lombok.ToString;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.Mark;

import java.util.List;
@ToString
public class FeedbackDTO {
	public final List<Mark> marks;
	public final List<String> hint;
	public FeedbackDTO(Feedback feedback) {
		this.marks=feedback.getMarks();
		this.hint=feedback.getHint();
	}
}
