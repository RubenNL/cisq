package nl.hu.cisq1.lingo.words.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
//TODO fix words
public class Word implements Serializable {
    @Id private String value;
    private Integer length;

    public Word(String word) {
        this.value = word;
        this.length = word.length();
    }
}
