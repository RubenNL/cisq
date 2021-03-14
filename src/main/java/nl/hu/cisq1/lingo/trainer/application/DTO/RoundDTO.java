package nl.hu.cisq1.lingo.trainer.application.DTO;

import lombok.ToString;
import nl.hu.cisq1.lingo.trainer.domain.Round;

import java.util.List;
import java.util.stream.Collectors;
@ToString
public class RoundDTO {
	public List<FeedbackDTO> feedbackList;
	public RoundDTO(Round round) {
		feedbackList=round.getFeedbackList().stream().map(FeedbackDTO::new).collect(Collectors.toList());
	}
}
