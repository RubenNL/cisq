package nl.hu.cisq1.lingo.words.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Round {
	@Id private Integer id;
	@OneToOne private Word word;
	@ManyToOne private Game game;
	@OneToMany(mappedBy="round") private List<Feedback> feedbackList;
}
