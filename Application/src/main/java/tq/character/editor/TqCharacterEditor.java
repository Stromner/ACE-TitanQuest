package tq.character.editor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TqCharacterEditor {
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(TqCharacterEditor.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }
}
