package tq.character.editor.file.handling.bytecode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tq.character.editor.Utils;

import java.util.Properties;

@ExtendWith(MockitoExtension.class)
public class BytecodeFileHandlerTests {
    private static Properties properties;
    @InjectMocks
    private BytecodeFileHandler unitUnderTest;

    @BeforeAll
    public static void oneTimeSetup() {
        MockitoAnnotations.initMocks(BytecodeFileHandlerTests.class);
        properties = Utils.getPropertyFile();
    }

    @Test
    public void testRead() {
        unitUnderTest.readFile(properties.getProperty("test.character.file"));

        Assertions.assertNotNull(unitUnderTest.getData());
    }
}
