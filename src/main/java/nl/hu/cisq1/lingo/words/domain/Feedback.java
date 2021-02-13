package nl.hu.cisq1.lingo.words.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Feedback {
	@Id private Integer id;
	@ManyToOne private Round round;
	private String attempt;
	@Enumerated
	@ElementCollection(targetClass = Mark.class)
	private List<Mark> marks;
	public Feedback(String attempt,List<Mark> marks) {
		this.attempt=attempt;
		this.marks=marks;
	}
	public boolean isWordGuessed() {
		return marks.stream().allMatch(mark->mark==Mark.CORRECT);
	}
	public boolean isWordValid() {
		return marks.stream().noneMatch(mark->mark==Mark.INVALID);
	}
}
