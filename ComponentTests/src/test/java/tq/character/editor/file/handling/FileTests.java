package tq.character.editor.file.handling;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import tq.character.editor.data.IDataHandler;
import tq.character.editor.data.file.handling.PlayerData;

import java.util.Objects;

@SpringBootTest
@PropertySource("classpath::application.properties")
public class FileTests {
    @Autowired
    private IDataHandler<PlayerData> dataHandler;
    @Autowired
    private Environment env;

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testRead() {
        // GIVEN (File to read in)
        String filePath = Objects.requireNonNull(env.getProperty("test.character.file"));

        // WHEN (A file is read a structured representation of the data is returned)
        dataHandler.processFile(filePath);

        // TODO Verify structure
    }
}
