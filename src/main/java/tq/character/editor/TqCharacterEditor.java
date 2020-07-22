package tq.character.editor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TqCharacterEditor {
    private static final Logger log = LoggerFactory.getLogger(TqCharacterEditor.class);

    public static void main(String[] args) {
        SpringApplication.run(TqCharacterEditor.class, args);

    }
}
