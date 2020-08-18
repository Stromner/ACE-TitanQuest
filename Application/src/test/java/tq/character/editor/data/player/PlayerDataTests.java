package tq.character.editor.data.player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tq.character.editor.core.errors.IllegalPlayerDataException;
import tq.character.editor.database.entities.content.IntContent;

@ExtendWith(MockitoExtension.class)
public class PlayerDataTests {
    private static final Integer NEGATIVE_AMOUNT = -1;
    private static final Integer TYPICAL_LEVEL = 10;
    private static final Integer MINIMUM_LEVEL = 1;

    @Mock
    private IntContent money;
    @Mock
    private IntContent skillPoints;
    @Mock
    private IntContent attributePoints;
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
        Mockito.when(skillPoints.getDataContent()).thenCallRealMethod();
        Mockito.doCallRealMethod().when(skillPoints).setDataContent(Mockito.anyInt());

        Integer skillPoints = 5;
        unitUnderTest.setUnspentSkillPoints(skillPoints);
        Assertions.assertEquals(skillPoints, unitUnderTest.getUnspentSkillPoints());
    }

    @Test
    public void testSetIllegalSkillPoints() {
        Assertions.assertThrows(IllegalPlayerDataException.class, () -> unitUnderTest.setUnspentSkillPoints(NEGATIVE_AMOUNT));
    }

    @Test
    public void testSetValidAttributePoints() throws IllegalPlayerDataException {
        Mockito.when(attributePoints.getDataContent()).thenCallRealMethod();
        Mockito.doCallRealMethod().when(attributePoints).setDataContent(Mockito.anyInt());

        Integer attributePoints = 10;
        unitUnderTest.setUnspentAttributePoints(attributePoints);
        Assertions.assertEquals(attributePoints, unitUnderTest.getUnspentAttributePoints());
    }

    @Test
    public void testSetIllegalAttributePoints() {
        Assertions.assertThrows(IllegalPlayerDataException.class, () -> unitUnderTest.setUnspentAttributePoints(NEGATIVE_AMOUNT));
    }
}
