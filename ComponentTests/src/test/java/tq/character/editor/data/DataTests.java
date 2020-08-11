package tq.character.editor.data;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import tq.character.editor.Utils;
import tq.character.editor.data.file.handling.IFileHandler;
import tq.character.editor.data.player.IPlayerData;
import tq.character.editor.database.IDataContentRepository;

import java.nio.ByteBuffer;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@PropertySource("classpath::application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class DataTests {
    private static final String ORG_PLAYER_NAME = Utils.createUTF16String("TestChar");
    private static final String MOD_PLAYER_NAME = Utils.createUTF16String("PlayerName");
    @Autowired
    IPlayerData playerData;
    @Autowired
    IFileHandler<ByteBuffer> fileHandler;
    @Autowired
    private IDataContentRepository contentRepository;
    @Autowired
    private Environment env;

    @BeforeEach
    public void setUp() {
        // Prepare file
        String filePath = Objects.requireNonNull(env.getProperty("test.character.file"));
        fileHandler.loadFile(filePath);
    }

    @Test
    // TODO This should probably be moved to a unit test
    public void testVerifyData() {
        // GIVEN (File has been loaded)

        // THEN (Verify validity of the loaded data)
        Assert.assertEquals(ORG_PLAYER_NAME, playerData.getPlayerName());
    }

    @Test
    // TODO This should probably be moved to a unit test
    public void testModifyData() {
        // GIVEN (File has been loaded)

        // WHEN (Data is modified)
        playerData.setPlayerName(MOD_PLAYER_NAME);

        // THEN (Modified data is stored in database)
        Assert.assertEquals(MOD_PLAYER_NAME, contentRepository.findByVariableName("myPlayerName").getDataContent());
    }
}
