package tq.character.editor.data.player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tq.character.editor.core.errors.IllegalPlayerDataException;
import tq.character.editor.data.player.attributes.AttributesData;
import tq.character.editor.database.entities.content.IntContent;

@ExtendWith(MockitoExtension.class)
public class AttributesDataTests {
    private static final Integer NEGATIVE_AMOUNT = -1;

    @Mock
    private IntContent unspentAttributePoints;
    @InjectMocks
    private AttributesData unitUnderTest;

    @Test
    public void testSetValidAttributePoints() throws IllegalPlayerDataException {
        Mockito.when(unspentAttributePoints.getDataContent()).thenCallRealMethod();
        Mockito.doCallRealMethod().when(unspentAttributePoints).setDataContent(Mockito.anyInt());

        Integer attributePoints = 10;
        unitUnderTest.setUnspentAttributePoints(attributePoints);
        Assertions.assertEquals(attributePoints, unitUnderTest.getUnspentAttributePoints());
    }

    @Test
    public void testSetIllegalAttributePoints() {
        Assertions.assertThrows(IllegalPlayerDataException.class, () -> unitUnderTest.setUnspentAttributePoints(NEGATIVE_AMOUNT));
    }
}
