package tq.character.editor.file.handling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tq.character.editor.Utils;
import tq.character.editor.file.handling.codec.CodecInterpreter;

import java.util.Properties;

@ExtendWith(MockitoExtension.class)
public class ByteFileHandlerTests {
    private static Properties properties;
    @Mock
    private CodecInterpreter codecInterpreter;
    @InjectMocks
    private ByteFileHandler unitUnderTest;

    @BeforeAll
    public static void oneTimeSetup() {
        MockitoAnnotations.initMocks(ByteFileHandlerTests.class);
        properties = Utils.getPropertyFile();
    }

    @Test
    public void testRead() {
        unitUnderTest.getFileContent(properties.getProperty("test.character.file"));

        Assertions.assertNotNull(unitUnderTest.getByteBuffer());
    }
}
