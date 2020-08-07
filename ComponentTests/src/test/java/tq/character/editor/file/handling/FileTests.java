package tq.character.editor.file.handling;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import tq.character.editor.data.file.handling.ICodec;
import tq.character.editor.data.file.handling.IFileHandler;
import tq.character.editor.data.file.handling.PlayerData;

import java.nio.ByteBuffer;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@PropertySource("classpath::application.properties")
public class FileTests {
    @Autowired
    IFileHandler<ByteBuffer> fileHandler;
    @Autowired
    ICodec<PlayerData, ByteBuffer> codec;
    @Autowired
    private Environment env;

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testReadInFile() {
        // GIVEN (file to parse)
        String filePath = Objects.requireNonNull(env.getProperty("test.character.file"));

        // WHEN (File is read in)
        fileHandler.readFile(filePath);
        Assert.assertNotNull(fileHandler.getRawData());

        // AND (Converted into internal object)
        codec.decode(fileHandler.getRawData());

        // THEN (Data is ready to be used by the application)
        Assert.assertNotNull(codec.getData());
    }
}
