package tq.character.editor.data.player.attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tq.character.editor.Utils;
import tq.character.editor.core.errors.IllegalPlayerDataException;
import tq.character.editor.core.events.DatabaseInitiatedEvent;
import tq.character.editor.database.IDataContentRepository;
import tq.character.editor.database.entities.content.DataContent;
import tq.character.editor.database.entities.content.FloatContent;
import tq.character.editor.database.entities.content.IntContent;

import java.util.List;

@Service
@Transactional // TODO This might not be needed
public class AttributesData implements IAttributesData {
    private static final Logger log = LoggerFactory.getLogger(AttributesData.class);
    private static final Integer ATTRIBUTE_GAIN = 4;
    private static final Integer FLUID_GAIN = ATTRIBUTE_GAIN * 10;

    @Autowired
    private IDataContentRepository contentRepository;

    @Value("${editor.player.min.attribute}")
    private int minAttribute;
    @Value("${editor.player.min.fluid}")
    private int minFluid;

    private IntContent unspentAttributePoints;
    private FloatContent strengthAttribute;
    private FloatContent dexterityAttribute;
    private FloatContent intelligenceAttribute;
    private FloatContent healthAttribute;
    private FloatContent manaAttribute;

    @EventListener
    public void onDatabaseInitiatedEvent(DatabaseInitiatedEvent event) {
        unspentAttributePoints = contentRepository.findByVariableName("modifierPoints");
        List<DataContent<?>> list = Utils.listFindSequence(contentRepository.findAllByVariableName("temp"), 5);
        strengthAttribute = (FloatContent) list.get(0);
        dexterityAttribute = (FloatContent) list.get(1);
        intelligenceAttribute = (FloatContent) list.get(2);
        healthAttribute = (FloatContent) list.get(3);
        manaAttribute = (FloatContent) list.get(4);

        log.info("Attributes initiated");
    }

    @Override
    public int getUnspentAttributePoints() {
        return unspentAttributePoints.getDataContent();
    }

    @Override
    public void setUnspentAttributePoints(int attributePoints) throws IllegalPlayerDataException {
        if (attributePoints < 0) {
            log.error("Could not set attribute points to {}, attribute points must be a positive amount", attributePoints);
            throw new IllegalPlayerDataException("Attribute points can not be a negative amount");
        }
        unspentAttributePoints.setDataContent(attributePoints);
    }

    @Override
    public void resetAllAttributes() {
        int unspentPoints = 0;
        unspentPoints += (getStrengthAttribute() - minAttribute) / ATTRIBUTE_GAIN;
        unspentPoints += (getDexterityAttribute() - minAttribute) / ATTRIBUTE_GAIN;
        unspentPoints += (getIntelligenceAttribute() - minAttribute) / ATTRIBUTE_GAIN;
        unspentPoints += (getHealthAttribute() - minFluid) / FLUID_GAIN;
        unspentPoints += (getManaAttribute() - minFluid) / FLUID_GAIN;

        try {
            setUnspentAttributePoints(unspentPoints);
        } catch (IllegalPlayerDataException e) {
            log.error("Something went horrible wrong, check trace!");
            e.printStackTrace();
        }
    }

    @Override
    public int getStrengthAttribute() {
        return strengthAttribute.getDataContent().intValue();
    }

    @Override
    public void setStrengthAttribute(int attributePoints) throws IllegalPlayerDataException {
        calculateUnspentAttributePoints("Strength", getStrengthAttribute(), attributePoints);
        strengthAttribute.setDataContent((float) attributePoints);
    }

    @Override
    public int getDexterityAttribute() {
        return dexterityAttribute.getDataContent().intValue();
    }

    @Override
    public void setDexterityAttribute(int attributePoints) throws IllegalPlayerDataException {
        calculateUnspentAttributePoints("Dexterity", getDexterityAttribute(), attributePoints);
        dexterityAttribute.setDataContent((float) attributePoints);
    }

    @Override
    public int getIntelligenceAttribute() {
        return intelligenceAttribute.getDataContent().intValue();
    }

    @Override
    public void setIntelligenceAttribute(int attributePoints) throws IllegalPlayerDataException {
        calculateUnspentAttributePoints("Intelligence", getIntelligenceAttribute(), attributePoints);
        intelligenceAttribute.setDataContent((float) attributePoints);
    }

    @Override
    public int getHealthAttribute() {
        return healthAttribute.getDataContent().intValue();
    }

    @Override
    public void setHealthAttribute(int attributePoints) throws IllegalPlayerDataException {
        calculateUnspentFluidPoints("Health", getHealthAttribute(), attributePoints);
        healthAttribute.setDataContent((float) attributePoints);
    }

    @Override
    public int getManaAttribute() {
        return manaAttribute.getDataContent().intValue();
    }

    @Override
    public void setManaAttribute(int attributePoints) throws IllegalPlayerDataException {
        calculateUnspentFluidPoints("Mana", getManaAttribute(), attributePoints);
        manaAttribute.setDataContent((float) attributePoints);
    }

    private void calculateUnspentAttributePoints(String attributeName, int currentValue, int newValue) throws IllegalPlayerDataException {
        if (newValue < minAttribute) {
            log.error("Could not set {} to {}, attribute must be greater than {}", attributeName, newValue, minAttribute);
            throw new IllegalPlayerDataException(attributeName + " can not be less than " + minAttribute);
        }
        float attributeDiff = newValue - currentValue;
        int points = (int) attributeDiff / ATTRIBUTE_GAIN;
        points += getUnspentAttributePoints();
        setUnspentAttributePoints(points);
    }

    private void calculateUnspentFluidPoints(String attributeName, int currentValue, int newValue) throws IllegalPlayerDataException {
        if (newValue < minFluid) {
            log.error("Could not set {} to {}, attribute must be greater than {}", attributeName, newValue, minFluid);
            throw new IllegalPlayerDataException(attributeName + " can not be less than " + minFluid);
        }
        float attributeDiff = newValue - currentValue;
        int points = (int) attributeDiff / FLUID_GAIN;
        points += getUnspentAttributePoints();
        setUnspentAttributePoints(points);
    }
}
