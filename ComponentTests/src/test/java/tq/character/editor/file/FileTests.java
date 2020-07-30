package tq.character.editor.file;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import tq.character.editor.file.handling.FileHandler;

import java.util.Objects;

@SpringBootTest
@PropertySource("classpath::application.properties")
public class FileTests {
    @Autowired
    private FileHandler fileHandler;
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
        // TODO COMPLETE (GIVEN, WHEN, THEN ...)
        String filePath = Objects.requireNonNull(env.getProperty("test.character.file"));
        fileHandler.getFileContent(filePath);
        assert (false);
    }
}
