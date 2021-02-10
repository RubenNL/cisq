package nl.hu.cisq1.lingo.words.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
@NoArgsConstructor
public class Guess {
	@Id private Integer id;
	@ManyToOne private Round round;
	private String word;
	public Guess(String word) {
		this.word=word;
	}
}
