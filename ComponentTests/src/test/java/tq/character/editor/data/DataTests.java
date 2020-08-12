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
import tq.character.editor.core.errors.IllegalPlayerDataException;
import tq.character.editor.data.file.handling.IFileHandler;
import tq.character.editor.data.player.IPlayerData;
import tq.character.editor.database.IDataContentRepository;

import java.nio.ByteBuffer;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@PropertySource("classpath::application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DataTests {
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
        String playerName = Utils.createUTF16String("TestChar");
        Integer money = 500;
        Integer skillPoints = 0;
        Integer attributePoints = 0;
        Integer playerLevel = 1;
        // GIVEN (File has been loaded)

        // THEN (Verify validity of the loaded data)
        Assert.assertEquals(playerName, playerData.getPlayerName());
        Assert.assertEquals(money, playerData.getMoney());
        Assert.assertEquals(skillPoints, playerData.getSkillPoints());
        Assert.assertEquals(attributePoints, playerData.getAttributePoints());
        Assert.assertEquals(playerLevel, playerData.getPlayerLevel());
    }

    @Test
    @Transactional
    // TODO This should probably be moved to a unit test
    public void testModifyData() throws IllegalPlayerDataException {
        String moddedPlayerName = Utils.createUTF16String("PlayerName");
        Integer moddedMoney = 1000;
        Integer moddedSkillPoints = 5;
        Integer moddedAttributePoints = 10;
        Integer moddedPlayerLevel = 10;
        // GIVEN (File has been loaded)

        // WHEN (Data is modified)
        playerData.setPlayerName(moddedPlayerName);
        playerData.setMoney(moddedMoney);
        playerData.setSkillPoints(moddedSkillPoints);
        playerData.setAttributePoints(moddedPlayerLevel);
        playerData.setPlayerLevel(moddedPlayerLevel);

        // THEN (Verify modified data is stored in database)
        Assert.assertEquals(moddedPlayerName, contentRepository.findByVariableName("myPlayerName").getDataContent());
        Assert.assertEquals(moddedMoney, contentRepository.findByVariableName("money").getDataContent());
        Assert.assertEquals(moddedSkillPoints, contentRepository.findByVariableName("skillPoints").getDataContent());
        Assert.assertEquals(moddedAttributePoints, contentRepository.findByVariableName("modifierPoints").getDataContent());
        Assert.assertEquals(moddedPlayerLevel, contentRepository.findByVariableName("currentStats.charLevel").getDataContent());
    }
}
