package tq.character.editor.data.player;

import tq.character.editor.core.errors.IllegalPlayerDataException;

/**
 * Defines all the methods to get and modify a player character
 */
public interface IPlayerData {
    String getPlayerName();

    void setPlayerName(String playerName);

    int getMoney();

    void setMoney(Integer money) throws IllegalPlayerDataException;

    int getPlayerLevel();

    void setPlayerLevel(Integer newLevel) throws IllegalPlayerDataException;

    int getUnspentSkillPoints();

    void setUnspentSkillPoints(Integer skillPoints) throws IllegalPlayerDataException;
}
