package tq.character.editor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TqCharacterEditor {
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(TqCharacterEditor.class);
        builder.headless(false);
        builder.run(args);
    }
}
