package ace.titan.quest.data;

import ace.titan.quest.TestUtils;
import ace.titan.quest.core.errors.IllegalPlayerDataException;
import ace.titan.quest.data.file.handling.IFileHandler;
import ace.titan.quest.data.player.IPlayerData;
import ace.titan.quest.data.player.attributes.IAttributesData;
import ace.titan.quest.database.IDataContentRepository;
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
        Assert.assertEquals(money, playerData.getMoney(), 0);
        Assert.assertEquals(skillPoints, playerData.getUnspentSkillPoints(), 0);
        Assert.assertEquals(attributePoints, attributeData.getUnspentAttributePoints(), 0);
        Assert.assertEquals(playerLevel, playerData.getPlayerLevel(), 0);
    }

    @Test
    @Transactional
    public void testModifyData() throws IllegalPlayerDataException {
        String moddedPlayerName = TestUtils.createUTF16String("PlayerName");
        int moddedMoney = 1000;
        int moddedSkillPoints = 5;
        int moddedAttributePoints = 10;
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
        playerData.setUnspentSkillPoints(40);
        attributeData.setUnspentAttributePoints(40);
        levelChange(playerData.getPlayerLevel() + 10);
    }

    @Test
    public void testDecreaseLevel() throws IllegalPlayerDataException {
        playerData.setUnspentSkillPoints(40);
        attributeData.setUnspentAttributePoints(40);
        levelChange(playerData.getPlayerLevel() + 10);
        levelChange(playerData.getPlayerLevel() - 1);
    }

    @Test
    @Transactional
    public void testResetAttributes() throws IllegalPlayerDataException {
        // GIVEN (File has been loaded)

        // WHEN (Attributes are set)
        attributeData.setUnspentAttributePoints(5);
        attributeData.setStrengthAttribute(54);
        attributeData.setDexterityAttribute(54);
        attributeData.setIntelligenceAttribute(54);
        attributeData.setHealthAttribute(340);
        attributeData.setManaAttribute(340);

        // AND (Attributes are reset)
        attributeData.resetAllAttributes();

        // THEN (Verify we have the correct amount of points)
        Assert.assertEquals(5, attributeData.getUnspentAttributePoints(), 0);
        Assert.assertEquals(50, attributeData.getStrengthAttribute(), 0);
        Assert.assertEquals(50, attributeData.getDexterityAttribute(), 0);
        Assert.assertEquals(50, attributeData.getIntelligenceAttribute(), 0);
        Assert.assertEquals(300, attributeData.getHealthAttribute(), 0);
        Assert.assertEquals(300, attributeData.getManaAttribute(), 0);
    }

    @Test
    public void testReadMultipleFiles() {
        String filePath = Objects.requireNonNull(env.getProperty("test.character.file"));
        fileHandler.loadFile(filePath);
    }

    @Transactional
    private void levelChange(int newLevel) throws IllegalPlayerDataException {
        int levelDiff = newLevel - playerData.getPlayerLevel();
        int curSkillPoints = playerData.getUnspentSkillPoints();
        int curAttributePoints = attributeData.getUnspentAttributePoints();

        playerData.setPlayerLevel(newLevel);
        Assertions.assertEquals(newLevel, playerData.getPlayerLevel());
        Assertions.assertEquals(curSkillPoints + 3 * levelDiff, playerData.getUnspentSkillPoints());
        Assertions.assertEquals(curAttributePoints + 2 * levelDiff, attributeData.getUnspentAttributePoints());
    }
}
