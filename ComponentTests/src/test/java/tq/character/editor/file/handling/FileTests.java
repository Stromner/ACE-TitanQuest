package tq.character.editor.file.handling;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import tq.character.editor.data.file.handling.IFileHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@PropertySource("classpath::application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FileTests {
    @Autowired
    IFileHandler<ByteBuffer> fileHandler;
    @Autowired
    private Environment env;

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testLoadFile() {
        // GIVEN (file to parse)
        String filePath = Objects.requireNonNull(env.getProperty("test.character.file"));

        // WHEN (File is loaded)
        fileHandler.loadFile(filePath);
        Assert.assertNotNull(fileHandler.getRawData());

        // THEN (Data is ready to be used by the application)
        // TODO Verify
    }

    @Test
    public void testSaveFile() throws IOException {
        // GIVEN (File has been loaded)
        String filePath = Objects.requireNonNull(env.getProperty("test.character.file"));
        String createFilePath = "./target";
        fileHandler.loadFile(filePath);

        // WHEN (File is saved)
        fileHandler.saveFile(createFilePath);

        // THEN (Verify the new file is equal to the original file)
        InputStream originalStream = new FileInputStream(filePath);
        InputStream createdFileStream = new FileInputStream(createFilePath + '/' + "Player.chr");

        int originalByte;
        int createdFileByte;
        do {
            originalByte = originalStream.read();
            createdFileByte = createdFileStream.read();
            Assert.assertEquals(originalByte, createdFileByte);
        } while ((originalByte != -1) && (createdFileByte != -1));
    }
}
