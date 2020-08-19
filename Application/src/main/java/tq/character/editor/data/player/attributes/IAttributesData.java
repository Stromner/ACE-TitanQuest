package tq.character.editor.data.player.attributes;

import tq.character.editor.core.errors.IllegalPlayerDataException;

public interface IAttributesData {
    Integer getUnspentAttributePoints();

    void setUnspentAttributePoints(Integer attributePoints) throws IllegalPlayerDataException;

    void resetAllAttributes();

    Integer getStrengthAttribute();

    void setStrengthAttribute(Integer attributePoints) throws IllegalPlayerDataException;

    Integer getDexterityAttribute();

    void setDexterityAttribute(Integer attributePoints) throws IllegalPlayerDataException;

    Integer getIntelligenceAttribute();

    void setIntelligenceAttribute(Integer attributePoints) throws IllegalPlayerDataException;

    Integer getHealthAttribute();

    void setHealthAttribute(Integer attributePoints) throws IllegalPlayerDataException;

    Integer getManaAttribute();

    void setManaAttribute(Integer attributePoints) throws IllegalPlayerDataException;
}
