package tq.character.editor.data;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import tq.character.editor.TestUtils;
import tq.character.editor.core.errors.IllegalPlayerDataException;
import tq.character.editor.data.file.handling.IFileHandler;
import tq.character.editor.data.player.IPlayerData;
import tq.character.editor.data.player.attributes.IAttributesData;
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
    IAttributesData attributeData;
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
    public void testVerifyData() {
        String playerName = TestUtils.createUTF16String("TestChar");
        int money = 500;
        int skillPoints = 0;
        int attributePoints = 0;
        int playerLevel = 1;
        // GIVEN (File has been loaded)

        // THEN (Verify validity of the loaded data)
        Assert.assertEquals(playerName, playerData.getPlayerName());
        Assert.assertEquals(money, playerData.getMoney());
        Assert.assertEquals(skillPoints, playerData.getUnspentSkillPoints());
        Assert.assertEquals(attributePoints, attributeData.getUnspentAttributePoints());
        Assert.assertEquals(playerLevel, playerData.getPlayerLevel());
    }

    @Test
    @Transactional
    public void testModifyData() throws IllegalPlayerDataException {
        String moddedPlayerName = TestUtils.createUTF16String("PlayerName");
        Integer moddedMoney = 1000;
        Integer moddedSkillPoints = 5;
        Integer moddedAttributePoints = 10;
        // GIVEN (File has been loaded)

        // WHEN (Data is modified)
        playerData.setPlayerName(moddedPlayerName);
        playerData.setMoney(moddedMoney);
        playerData.setUnspentSkillPoints(moddedSkillPoints);
        attributeData.setUnspentAttributePoints(moddedAttributePoints);

        // THEN (Verify modified data is stored in database)
        Assert.assertEquals(moddedPlayerName, contentRepository.findByVariableName("myPlayerName").getDataContent());
        Assert.assertEquals(moddedMoney, contentRepository.findByVariableName("money").getDataContent());
        Assert.assertEquals(moddedSkillPoints, contentRepository.findByVariableName("skillPoints").getDataContent());
        Assert.assertEquals(moddedAttributePoints, contentRepository.findByVariableName("modifierPoints").getDataContent());
    }

    @Test
    public void testIncreaseLevel() throws IllegalPlayerDataException {
        levelChange(playerData.getPlayerLevel() + 10);
    }

    @Test
    public void testDecreaseLevel() throws IllegalPlayerDataException {
        levelChange(playerData.getPlayerLevel() + 10);
        levelChange(playerData.getPlayerLevel() - 1);
    }

    @Test
    public void testDatabaseClearedMultipleReads() {
        String filePath = Objects.requireNonNull(env.getProperty("test.character.file"));
        fileHandler.loadFile(filePath);
    }

    @Transactional
    private void levelChange(Integer newLevel) throws IllegalPlayerDataException {
        int levelDiff = newLevel - playerData.getPlayerLevel();
        int curSkillPoints = playerData.getUnspentSkillPoints();
        int curAttributePoints = attributeData.getUnspentAttributePoints();

        playerData.setPlayerLevel(newLevel);
        Assertions.assertEquals(newLevel, playerData.getPlayerLevel());
        Assertions.assertEquals(curSkillPoints + 3 * levelDiff, playerData.getUnspentSkillPoints());
        Assertions.assertEquals(curAttributePoints + 2 * levelDiff, attributeData.getUnspentAttributePoints());
    }
}
