package nl.hu.cisq1.lingo.words.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.hu.cisq1.lingo.words.domain.exception.IllegalActionException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
		if(feedbackList.size()==MAXROUNDS) throw new IllegalActionException("al 5 keer geraden!");
		if(wordGuessed()) throw new IllegalActionException("word al geraden!");
		Feedback feedback=new Feedback(attempt,word.getValue());
		feedbackList.add(feedback);
		return feedback;
	}
	public boolean wordGuessed() {
		if(feedbackList.size()==0) return false;
		return feedbackList.get(feedbackList.size()-1).isWordGuessed();
	}
	public int getScore() {
		if(!wordGuessed()) return 0;
		return (MAXROUNDS*(MAXROUNDS-feedbackList.size())+MAXROUNDS);
	}
}
