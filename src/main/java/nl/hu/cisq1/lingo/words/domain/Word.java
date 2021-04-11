package nl.hu.cisq1.lingo.words.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
public class Word {
    @Id private String value;
    private Integer length;

    public Word(String word) {
        this.value = word;
        this.length = word.length();
    }
}
