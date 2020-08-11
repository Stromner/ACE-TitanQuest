package tq.character.editor.file.handling;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import tq.character.editor.data.IDataAccess;
import tq.character.editor.database.IDataContentRepository;

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
    IDataAccess<ByteBuffer> dataAccess;
    @Autowired
    private IDataContentRepository contentRepository;
    @Autowired
    private Environment env;

    @Test
    public void testLoadFile() {
        // GIVEN (file to parse)
        String filePath = Objects.requireNonNull(env.getProperty("test.character.file"));

        // WHEN (File is loaded)
        dataAccess.loadFile(filePath);

        // THEN (Data is ready to be used by the application)
        Assert.assertNotNull(dataAccess.getRawData());
        Assert.assertTrue(contentRepository.findAll().size() > 1);
    }

    @Test
    public void testSaveFile() throws IOException {
        // GIVEN (File has been loaded)
        String filePath = Objects.requireNonNull(env.getProperty("test.character.file"));
        String createFilePath = "./target";
        dataAccess.loadFile(filePath);

        // WHEN (File is saved)
        dataAccess.saveFile(createFilePath);

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
