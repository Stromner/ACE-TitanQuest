package tq.character.editor;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import tq.character.editor.copied.code.save.player.HeaderInfo;
import tq.character.editor.copied.code.save.player.PlayerParser;
import tq.character.editor.copied.code.util.Constants;

import java.io.File;
import java.util.Objects;

@SpringBootTest
@PropertySource("classpath::application.properties")
class TqCharacterEditorTests {
    private static final Logger log = LoggerFactory.getLogger(TqCharacterEditorTests.class);
    @Autowired
    private Environment env;
    private PlayerParser playerParser;

    @BeforeEach
    public void setUp() {
        File file = new File(Objects.requireNonNull(env.getProperty("test.character.file"))).getAbsoluteFile();
        playerParser = new PlayerParser(file, "DummyBounceCha");
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testRead() {
        try {
            playerParser.parse();

            playerParser.fillBuffer();
            playerParser.buildBlocksTable();
            playerParser.prepareForParse();
            playerParser.prepareBufferForRead();
        } catch (Exception e) {
            log.error(Constants.ERROR_MSG_EXCEPTION, e);
        }

        HeaderInfo headerInfo = playerParser.parseHeader();
        Assert.assertNotNull(headerInfo);
        Assert.assertTrue(headerInfo.getHeaderVersion() > 0);
        Assert.assertTrue(headerInfo.getPlayerVersion() > 0);
        Assert.assertTrue(headerInfo.getPlayerLevel() > 0);
    }
}
