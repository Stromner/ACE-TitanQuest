package tq.character.editor.db;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;
import tq.character.editor.database.IDataContentRepository;
import tq.character.editor.database.entities.Variable;
import tq.character.editor.database.entities.VariableType;
import tq.character.editor.database.entities.content.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@PropertySource("classpath::application.properties")
@Transactional
public class DatabaseTests {
    private static final Logger log = LoggerFactory.getLogger(DatabaseTests.class);
    @Autowired
    private IDataContentRepository contentRepository;

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testDataStructure() {
        insertRow("idRow", VariableType.ID, new byte[]{0x00});
        insertRow("intRow", VariableType.INT, 12);
        insertRow("utf8Row", VariableType.UTF8, "utf8Data");
        insertRow("utf16Row", VariableType.UTF16, "utf16Data");
        insertRow("streamRow", VariableType.STREAM, new byte[]{0x00});

        Assert.assertEquals(5, contentRepository.count());
    }

    private <E> void insertRow(String variableName, VariableType type, E data) {
        Variable variable = new Variable(variableName, type);
        DataContent content;
        switch (type) {
            case ID:
                content = new IdContent(variable, (byte[]) data);
                break;
            case INT:
                content = new IntContent(variable, (int) data);
                break;
            case UTF8:
                content = new UTF8Content(variable, (String) data);
                break;
            case UTF16:
                content = new UTF16Content(variable, (String) data);
                break;
            case STREAM:
                content = new StreamContent(variable, (byte[]) data);
                break;
            default:
                log.error("Invalid VariableType '{}'", type);
                throw new IllegalArgumentException("Illegal VariableType");
        }
        contentRepository.saveAndFlush(content);
    }
}
