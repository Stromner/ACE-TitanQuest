package tq.character.editor.data.player.attributes;

import tq.character.editor.core.errors.IllegalPlayerDataException;

public interface IAttributesData {
    int getUnspentAttributePoints();

    void setUnspentAttributePoints(int attributePoints) throws IllegalPlayerDataException;

    void resetAllAttributes();

    int getStrengthAttribute();

    void setStrengthAttribute(int attributePoints) throws IllegalPlayerDataException;

    int getDexterityAttribute();

    void setDexterityAttribute(int attributePoints) throws IllegalPlayerDataException;

    int getIntelligenceAttribute();

    void setIntelligenceAttribute(int attributePoints) throws IllegalPlayerDataException;

    int getHealthAttribute();

    void setHealthAttribute(int attributePoints) throws IllegalPlayerDataException;

    int getManaAttribute();

    void setManaAttribute(int attributePoints) throws IllegalPlayerDataException;
}
