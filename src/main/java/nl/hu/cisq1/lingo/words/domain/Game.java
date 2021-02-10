package nl.hu.cisq1.lingo.words.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Game {
	@Id private Integer id;
	@OneToMany(mappedBy="game") private List<Round> roundList;
}
