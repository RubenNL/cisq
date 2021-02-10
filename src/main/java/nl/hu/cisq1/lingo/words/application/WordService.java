package nl.hu.cisq1.lingo.words.application;

import nl.hu.cisq1.lingo.words.domain.exception.WordLengthNotSupportedException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.List;

@Service
@Transactional
public class WordService {
    public String provideRandomWord(Integer length) {
        List<String> items;
        try {
            items=Files.readAllLines(Paths.get(getClass().getClassLoader().getResource(length + ".txt").toURI()));
        } catch(IOException | URISyntaxException | NullPointerException e) {
            throw new WordLengthNotSupportedException(length);
        }
        return items.get(new SecureRandom().nextInt(items.size()));
    }
}
