package tq.character.editor.data.player;

import tq.character.editor.core.errors.IllegalPlayerDataException;

/**
 * Defines all the methods to get and modify a player character
 */
public interface IPlayerData {
    String getPlayerName();

    void setPlayerName(String playerName);

    Integer getMoney();

    void setMoney(Integer money) throws IllegalPlayerDataException;

    Integer getPlayerLevel();

    void setPlayerLevel(Integer newLevel) throws IllegalPlayerDataException;

    Integer getSkillPoints();

    void setSkillPoints(Integer skillPoints) throws IllegalPlayerDataException;

    Integer getAttributePoints();

    void setAttributePoints(Integer attributePoints) throws IllegalPlayerDataException;
}
