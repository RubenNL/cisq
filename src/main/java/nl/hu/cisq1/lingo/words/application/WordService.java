package nl.hu.cisq1.lingo.words.application;

import nl.hu.cisq1.lingo.words.domain.exception.WordLengthNotSupportedException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class WordService {
    public String provideRandomWord(Integer length) {
        List<String> items;
        try {
            URL url=getClass().getClassLoader().getResource(length + ".txt");
            URI uri=url.toURI();
            System.out.println(uri);
            Path path=Paths.get(uri);
            System.out.println(path);
            items=Files.readAllLines(path);
            System.out.println(items);
        } catch(IOException | URISyntaxException | NullPointerException e) {
            System.out.println(e);
            throw new WordLengthNotSupportedException(length);
        }
        return items.get(new Random().nextInt(items.size()));
    }
}
