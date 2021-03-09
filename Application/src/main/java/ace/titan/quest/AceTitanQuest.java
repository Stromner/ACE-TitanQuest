package ace.titan.quest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AceTitanQuest {
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(AceTitanQuest.class);
        builder.headless(false);
        builder.run(args);
    }
}
