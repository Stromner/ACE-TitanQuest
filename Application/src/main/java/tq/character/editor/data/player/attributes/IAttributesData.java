package tq.character.editor.data.player.attributes;

import tq.character.editor.core.errors.IllegalPlayerDataException;

public interface IAttributesData {
    int getUnspentAttributePoints();

    void setUnspentAttributePoints(Integer attributePoints) throws IllegalPlayerDataException;
}
