package ace.titan.quest.data.player;

import ace.titan.quest.core.errors.IllegalPlayerDataException;
import ace.titan.quest.data.player.attributes.IAttributesData;
import ace.titan.quest.database.IDataContentRepository;
import ace.titan.quest.database.entities.content.IntContent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlayerDataTests {
    private static final Integer NEGATIVE_AMOUNT = -1;
    private static final Integer MINIMUM_LEVEL = 1;

    @Mock
    IDataContentRepository contentRepository;
    @Mock
    private IntContent money;
    @Mock
    private IntContent unspentSkillPoints;
    @Mock
    private IntContent playerLevel;
    @Mock
    private IAttributesData attributesData;
    @InjectMocks
    private PlayerData unitUnderTest;

    @Test
    public void testSetValidMoney() throws IllegalPlayerDataException {
        Mockito.when(money.getDataContent()).thenCallRealMethod();
        Mockito.doCallRealMethod().when(money).setDataContent(Mockito.anyInt());

        Integer moneyAmount = 1000;
        unitUnderTest.setMoney(moneyAmount);
        Assertions.assertEquals(moneyAmount, unitUnderTest.getMoney());
    }

    @Test
    public void testSetIllegalMoney() {
        Assertions.assertThrows(IllegalPlayerDataException.class, () -> unitUnderTest.setMoney(NEGATIVE_AMOUNT));
    }

    // testIncPlayerLevel & testDecPlayerLevel exists as component tests

    @Test
    public void testSetPlayerLevelNegative() {
        Assertions.assertThrows(IllegalPlayerDataException.class, () -> unitUnderTest.setPlayerLevel(NEGATIVE_AMOUNT));
    }

    @Test
    public void testSetPlayerLevelExcess() {
        Integer playerLevel = Integer.MAX_VALUE;
        Assertions.assertThrows(IllegalPlayerDataException.class, () -> unitUnderTest.setPlayerLevel(playerLevel));
    }

    @Test
    public void testSetPlayerLevelLackingSkillPoints() {
        Assertions.assertThrows(IllegalPlayerDataException.class, () -> unitUnderTest.setPlayerLevel(MINIMUM_LEVEL));
    }

    @Test
    public void testSetPlayerLevelLackingAttributePoints() {
        Assertions.assertThrows(IllegalPlayerDataException.class, () -> unitUnderTest.setPlayerLevel(MINIMUM_LEVEL));
    }

    @Test
    public void testSetValidSkillPoints() throws IllegalPlayerDataException {
        Mockito.when(unspentSkillPoints.getDataContent()).thenCallRealMethod();
        Mockito.doCallRealMethod().when(unspentSkillPoints).setDataContent(Mockito.anyInt());

        Integer skillPoints = 5;
        unitUnderTest.setUnspentSkillPoints(skillPoints);
        Assertions.assertEquals(skillPoints, unitUnderTest.getUnspentSkillPoints());
    }

    @Test
    public void testSetIllegalSkillPoints() {
        Assertions.assertThrows(IllegalPlayerDataException.class, () -> unitUnderTest.setUnspentSkillPoints(NEGATIVE_AMOUNT));
    }
}
