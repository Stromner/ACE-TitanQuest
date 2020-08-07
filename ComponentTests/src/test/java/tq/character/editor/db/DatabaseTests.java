package tq.character.editor.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import tq.character.editor.database.IDataContentRepository;
import tq.character.editor.database.entities.Variable;
import tq.character.editor.database.entities.VariableType;
import tq.character.editor.database.entities.content.IntContent;
import tq.character.editor.database.entities.content.UTF8Content;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@PropertySource("classpath::application.properties")
public class DatabaseTests {
    private static final Logger log = LoggerFactory.getLogger(DatabaseTests.class);
    @Autowired
    private IDataContentRepository contentRepository;
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
        insertUTF8Row("stringRow", VariableType.UTF8, "testString");
        insertIntRow("intRow", VariableType.INT, 587);

        for (var entry : contentRepository.findAll()) {
            log.info(entry.toString());
        }
    }

    private void insertIntRow(String variableName, VariableType type, int data) {
        Variable variable = new Variable(variableName, type);
        IntContent content = new IntContent(variable, data);
        contentRepository.saveAndFlush(content);
    }

    private void insertUTF8Row(String variableName, VariableType type, String data) {
        Variable variable = new Variable(variableName, type);
        UTF8Content content = new UTF8Content(variable, data);
        contentRepository.saveAndFlush(content);
    }
}
