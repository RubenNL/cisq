package nl.hu.cisq1.lingo.words.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.hu.cisq1.lingo.words.domain.exception.IllegalActionException;

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
	@Id private Integer id;
	@OneToOne private Word word;
	@ManyToOne private Game game;
	@OneToMany(mappedBy="round") private List<Feedback> feedbackList=new ArrayList<>();
	public Round(Word word) {
		this.word=word;
	}
	public Feedback addFeedback(String attempt) {
		if(getState()!=State.ACTIVE) throw new IllegalActionException("ronde is gewonnen/verloren!");
		Feedback feedback=new Feedback(attempt,Feedback.generateMarks(attempt,word.getValue()));
		feedback.setHint(this.giveHint(),this.word.getValue());
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
	public List<List<String>> allHints() {
		//TODO refactor giveHint
		List<List<String>> response=new ArrayList<>();
		List<String> lastHint= new ArrayList<>(List.of(word.getValue().split("")[0]));
		lastHint.addAll(Collections.nCopies(word.getLength()-1, "."));
		response.add(lastHint);
		response.addAll(feedbackList.stream().map(Feedback::getHint).collect(Collectors.toList()));
		return response;
	}
	public List<String> giveHint() {//Rare structuur om de Feedback.giveHint op deze manier te laten werken...
		List<List<String>> allHints=this.allHints();
		return allHints.get(allHints.size()-1);
	}
}
