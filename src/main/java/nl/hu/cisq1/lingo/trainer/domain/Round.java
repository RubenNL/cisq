package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.hu.cisq1.lingo.trainer.domain.exceptions.IllegalActionException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
public class Round {
	public static final Integer MAXROUNDS=5;
	@Id @GeneratedValue private Integer id;
	private String word;
	@ManyToOne private Game game;
	@OneToMany(mappedBy="round",fetch=FetchType.EAGER,cascade=CascadeType.ALL) private List<Feedback> feedbackList=new ArrayList<>();
	public Round(String word) {
		this.word=word;
	}
	public Feedback addFeedback(String attempt) {
		if(getState()!=State.ACTIVE) throw new IllegalActionException("ronde is gewonnen/verloren!");
		Feedback feedback=new Feedback(attempt,giveHint(),word);
		feedbackList.add(feedback);
		return feedback;
	}
	public boolean wordGuessed() {
		if(feedbackList.isEmpty()) return false;
		return feedbackList.get(feedbackList.size()-1).isWordGuessed();
	}
	public int getScore() {
		if(!wordGuessed()) return 0;
		return (MAXROUNDS*(MAXROUNDS-feedbackList.size())+MAXROUNDS);
	}
	public State getState() {
		if(wordGuessed()) return State.WON;
		if(feedbackList.size()==MAXROUNDS) return State.LOST;
		return State.ACTIVE;
	}
	public static List<String> firstHint(String word) {
		List<String> hint= new ArrayList<>(List.of(word.split("")[0]));
		hint.addAll(Collections.nCopies(word.length()-1, "."));
		return hint;
	}
	public List<List<String>> allHints() {
		List<List<String>> response=new ArrayList<>();
		response.add(Round.firstHint(word));
		response.addAll(feedbackList.stream().map(Feedback::getHint).collect(Collectors.toList()));
		return response;
	}
	public List<String> giveHint() {//Rare structuur om de Feedback.giveHint op deze manier te laten werken...
		List<List<String>> allHints=this.allHints();
		return allHints.get(allHints.size()-1);
	}
}
