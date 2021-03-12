package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Feedback {
	@Id @GeneratedValue private Integer id;
	@ManyToOne private Round round;
	private String attempt;
	@Enumerated
	@ElementCollection(targetClass = Mark.class)
	private List<Mark> marks;
	@ElementCollection
	private List<String> hint;
	public Feedback(String attempt, List<String> lastHint,String wordToGuess) {
		this.attempt=attempt;
		this.marks=generateMarks(wordToGuess);
		this.hint=this.generateHint(lastHint,wordToGuess);
	}
	@SuppressWarnings("java:S5413") //wordListInvalid.remove gave a warning that is not applicable in this situation.
	public List<Mark> generateMarks(String word) {
		List<Mark> response=new ArrayList<>();
		if(attempt.length()!=word.length()) return Collections.nCopies(word.length(), Mark.INVALID);
		char[] wordArray=word.toCharArray();
		char[] guessArray=attempt.toCharArray();
		List<Character> wordListAbsent=new ArrayList<>();
		for(int i=0;i<wordArray.length;i++) {
			if(wordArray[i]==guessArray[i]) response.add(Mark.CORRECT);
			else {
				response.add(Mark.ABSENT);
				wordListAbsent.add(wordArray[i]);
			}
		}
		for(int i=0;i<wordArray.length;i++) {
			if(wordListAbsent.contains(guessArray[i]) && response.get(i)==Mark.ABSENT) {
				//noinspection RedundantCollectionOperation
				wordListAbsent.remove(wordListAbsent.indexOf(guessArray[i]));
				response.set(i,Mark.PRESENT);
			}
		}
		return response;
	}
	public boolean isWordGuessed() {
		return this.marks.stream().allMatch(mark->mark==Mark.CORRECT);
	}
	public boolean isWordValid() {
		return marks.stream().noneMatch(mark->mark==Mark.INVALID);
	}
	private List<String> generateHint(List<String> lastHint, String wordToGuess) {
		String[] chars=wordToGuess.split("");
		List<String> response=new ArrayList<>();
		for(int i=0;i<chars.length;i++) {
			if(this.marks.get(i)==Mark.CORRECT) response.add(chars[i]);
			else if(!lastHint.get(i).equals(".")) response.add(chars[i]);
			else response.add(".");
		}
		return response;
	}
}