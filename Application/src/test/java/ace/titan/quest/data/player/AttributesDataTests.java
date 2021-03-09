package ace.titan.quest.data.player;

import ace.titan.quest.TestUtils;
import ace.titan.quest.core.errors.IllegalPlayerDataException;
import ace.titan.quest.data.player.attributes.AttributesData;
import ace.titan.quest.database.IDataContentRepository;
import ace.titan.quest.database.entities.Variable;
import ace.titan.quest.database.entities.VariableType;
import ace.titan.quest.database.entities.content.FloatContent;
import ace.titan.quest.database.entities.content.IntContent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AttributesDataTests {
    private static final int NEGATIVE_AMOUNT = -1;
    private final int minimumCharacteristic = Integer.parseInt(TestUtils.getPropertyFile().getProperty("editor.player.min.characteristic"));
    private final int minimumFluid = Integer.parseInt(TestUtils.getPropertyFile().getProperty("editor.player.min.fluid"));

    @Mock
    IDataContentRepository contentRepository;
    @Spy
    private final IntContent unspentAttributePoints = new IntContent(new Variable("modifierPoints", VariableType.INT), 0);
    @Spy
    private final FloatContent strengthAttribute = new FloatContent(new Variable("temp", VariableType.FLOAT), 50f);
    @Spy
    private final FloatContent dexterityAttribute = new FloatContent(new Variable("temp", VariableType.FLOAT), 50f);
    @Spy
    private final FloatContent intelligenceAttribute = new FloatContent(new Variable("temp", VariableType.FLOAT), 50f);
    @Spy
    private final FloatContent healthAttribute = new FloatContent(new Variable("temp", VariableType.FLOAT), 300f);
    @Spy
    private final FloatContent manaAttribute = new FloatContent(new Variable("temp", VariableType.FLOAT), 300f);
    @InjectMocks
    private AttributesData unitUnderTest;

    @Test
    public void testSetValidAttributePoints() throws IllegalPlayerDataException {
        unitUnderTest.setUnspentAttributePoints(10);
        Assertions.assertEquals(10, unitUnderTest.getUnspentAttributePoints());
    }

    @Test
    public void testSetIllegalAttributePoints() {
        Assertions.assertThrows(IllegalPlayerDataException.class, () -> unitUnderTest.setUnspentAttributePoints(NEGATIVE_AMOUNT));
    }

    @Test
    public void testSetValidStrength() throws IllegalPlayerDataException {
        unitUnderTest.setStrengthAttribute(minimumCharacteristic + 1);
        Assertions.assertEquals(minimumCharacteristic + 1, unitUnderTest.getStrengthAttribute());
    }

    @Test
    public void testSetInvalidStrength() {
        Assertions.assertThrows(IllegalPlayerDataException.class, () -> unitUnderTest.setStrengthAttribute(NEGATIVE_AMOUNT));
    }

    @Test
    public void testSetValidDexterity() throws IllegalPlayerDataException {
        unitUnderTest.setDexterityAttribute(minimumCharacteristic + 1);
        Assertions.assertEquals(minimumCharacteristic + 1, unitUnderTest.getDexterityAttribute());
    }

    @Test
    public void testSetInvalidDexterity() {
        Assertions.assertThrows(IllegalPlayerDataException.class, () -> unitUnderTest.setDexterityAttribute(NEGATIVE_AMOUNT));
    }

    @Test
    public void testSetValidIntelligence() throws IllegalPlayerDataException {
        unitUnderTest.setIntelligenceAttribute(minimumCharacteristic + 1);
        Assertions.assertEquals(minimumCharacteristic + 1, unitUnderTest.getIntelligenceAttribute());
    }

    @Test
    public void testSetInvalidIntelligence() {
        Assertions.assertThrows(IllegalPlayerDataException.class, () -> unitUnderTest.setIntelligenceAttribute(NEGATIVE_AMOUNT));
    }

    @Test
    public void testSetValidHealth() throws IllegalPlayerDataException {
        unitUnderTest.setHealthAttribute(minimumFluid + 1);
        Assertions.assertEquals(minimumFluid + 1, unitUnderTest.getHealthAttribute());
    }

    @Test
    public void testSetInvalidHealth() {
        Assertions.assertThrows(IllegalPlayerDataException.class, () -> unitUnderTest.setHealthAttribute(NEGATIVE_AMOUNT));
    }

    @Test
    public void testSetValidMana() throws IllegalPlayerDataException {
        unitUnderTest.setManaAttribute(minimumFluid + 1);
        Assertions.assertEquals(minimumFluid + 1, unitUnderTest.getManaAttribute());
    }

    @Test
    public void testSetInvalidMana() {
        Assertions.assertThrows(IllegalPlayerDataException.class, () -> unitUnderTest.setManaAttribute(NEGATIVE_AMOUNT));
    }
}
