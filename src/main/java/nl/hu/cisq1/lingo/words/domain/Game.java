package nl.hu.cisq1.lingo.words.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
@NoArgsConstructor
public class Game {
	@Id private Integer id;
	@OneToOne Word word;
}
