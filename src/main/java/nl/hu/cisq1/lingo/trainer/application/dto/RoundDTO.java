package nl.hu.cisq1.lingo.trainer.application.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;
import nl.hu.cisq1.lingo.trainer.domain.Round;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
@ToString
@AllArgsConstructor
public class RoundDTO implements Serializable {
	public final List<FeedbackDTO> feedbackList;
	public final List<String> currentHint;
	public RoundDTO(Round round) {
		feedbackList=round.getFeedbackList().stream().map(FeedbackDTO::new).collect(Collectors.toList());
		currentHint=round.giveHint();
	}
}
