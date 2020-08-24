package tq.character.editor.data.player.attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import tq.character.editor.Utils;
import tq.character.editor.core.errors.IllegalPlayerDataException;
import tq.character.editor.core.events.AttributesInitiatedEvent;
import tq.character.editor.core.events.DatabaseInitiatedEvent;
import tq.character.editor.database.IDataContentRepository;
import tq.character.editor.database.entities.content.DataContent;
import tq.character.editor.database.entities.content.FloatContent;
import tq.character.editor.database.entities.content.IntContent;

import java.util.List;

@Service
public class AttributesData implements IAttributesData {
    private static final Logger log = LoggerFactory.getLogger(AttributesData.class);
    private static final Integer CHARACTERISTIC_GAIN = 4;
    private static final Integer FLUID_GAIN = CHARACTERISTIC_GAIN * 10;

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private IDataContentRepository contentRepository;

    @Value("${editor.player.min.characteristic}")
    private int minCharacteristic;
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

        eventPublisher.publishEvent(new AttributesInitiatedEvent(this));
        log.info("Attributes initiated");
    }

    @Override
    public Integer getCharacteristicGain() {
        return CHARACTERISTIC_GAIN;
    }

    @Override
    public Integer getFluidGain() {
        return FLUID_GAIN;
    }

    @Override
    public Integer getUnspentAttributePoints() {
        return unspentAttributePoints.getDataContent();
    }

    @Override
    public void setUnspentAttributePoints(Integer attributePoints) throws IllegalPlayerDataException {
        if (attributePoints < 0) {
            log.error("Could not set attribute points to {}, attribute points must be a positive amount", attributePoints);
            throw new IllegalPlayerDataException("Unspent attribute points can not be less than zero");
        }
        unspentAttributePoints.setDataContent(attributePoints);
        contentRepository.saveAndFlush(unspentAttributePoints);
    }

    @Override
    public void resetAllAttributes() {
        int unspentPoints = getUnspentAttributePoints();
        unspentPoints += (getStrengthAttribute() - minCharacteristic) / CHARACTERISTIC_GAIN;
        unspentPoints += (getDexterityAttribute() - minCharacteristic) / CHARACTERISTIC_GAIN;
        unspentPoints += (getIntelligenceAttribute() - minCharacteristic) / CHARACTERISTIC_GAIN;
        unspentPoints += (getHealthAttribute() - minFluid) / FLUID_GAIN;
        unspentPoints += (getManaAttribute() - minFluid) / FLUID_GAIN;

        try {
            setStrengthAttribute(minCharacteristic);
            setDexterityAttribute(minCharacteristic);
            setIntelligenceAttribute(minCharacteristic);
            setHealthAttribute(minFluid);
            setManaAttribute(minFluid);
            setUnspentAttributePoints(unspentPoints);
        } catch (IllegalPlayerDataException e) {
            log.error("Something went horrible wrong, check trace!");
            e.printStackTrace();
        }
    }

    @Override
    public Integer getStrengthAttribute() {
        return strengthAttribute.getDataContent().intValue();
    }

    @Override
    public void setStrengthAttribute(Integer attributePoints) throws IllegalPlayerDataException {
        calculateUnspentCharacteristicPoints("Strength", getStrengthAttribute(), attributePoints);
        strengthAttribute.setDataContent((float) attributePoints);
        contentRepository.saveAndFlush(strengthAttribute);
    }

    @Override
    public Integer getDexterityAttribute() {
        return dexterityAttribute.getDataContent().intValue();
    }

    @Override
    public void setDexterityAttribute(Integer attributePoints) throws IllegalPlayerDataException {
        calculateUnspentCharacteristicPoints("Dexterity", getDexterityAttribute(), attributePoints);
        dexterityAttribute.setDataContent((float) attributePoints);
        contentRepository.saveAndFlush(dexterityAttribute);
    }

    @Override
    public Integer getIntelligenceAttribute() {
        return intelligenceAttribute.getDataContent().intValue();
    }

    @Override
    public void setIntelligenceAttribute(Integer attributePoints) throws IllegalPlayerDataException {
        calculateUnspentCharacteristicPoints("Intelligence", getIntelligenceAttribute(), attributePoints);
        intelligenceAttribute.setDataContent((float) attributePoints);
        contentRepository.saveAndFlush(intelligenceAttribute);
    }

    @Override
    public Integer getHealthAttribute() {
        return healthAttribute.getDataContent().intValue();
    }

    @Override
    public void setHealthAttribute(Integer attributePoints) throws IllegalPlayerDataException {
        calculateUnspentFluidPoints("Health", getHealthAttribute(), attributePoints);
        healthAttribute.setDataContent((float) attributePoints);
        contentRepository.saveAndFlush(healthAttribute);
    }

    @Override
    public Integer getManaAttribute() {
        return manaAttribute.getDataContent().intValue();
    }

    @Override
    public void setManaAttribute(Integer attributePoints) throws IllegalPlayerDataException {
        calculateUnspentFluidPoints("Mana", getManaAttribute(), attributePoints);
        manaAttribute.setDataContent((float) attributePoints);
        contentRepository.saveAndFlush(manaAttribute);
    }

    private void calculateUnspentCharacteristicPoints(String attributeName, int currentValue, int newValue) throws IllegalPlayerDataException {
        if (newValue < minCharacteristic) {
            log.error("Could not set {} to {}, attribute must be greater than {}", attributeName, newValue, minCharacteristic);
            throw new IllegalPlayerDataException(attributeName + " can not be less than " + minCharacteristic);
        }
        int points = (newValue - currentValue) / CHARACTERISTIC_GAIN;
        setUnspentAttributePoints(getUnspentAttributePoints() - points);
    }

    private void calculateUnspentFluidPoints(String attributeName, int currentValue, int newValue) throws IllegalPlayerDataException {
        if (newValue < minFluid) {
            log.error("Could not set {} to {}, attribute must be greater than {}", attributeName, newValue, minFluid);
            throw new IllegalPlayerDataException(attributeName + " can not be less than " + minFluid);
        }
        int points = (newValue - currentValue) / FLUID_GAIN;
        setUnspentAttributePoints(getUnspentAttributePoints() - points);
    }
}
