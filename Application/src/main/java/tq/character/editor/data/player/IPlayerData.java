package tq.character.editor.data.player;

/**
 * Defines all the methods to get and modify a player character
 */
public interface IPlayerData {
    String getPlayerName();

    void setPlayerName(String playerName);

    Integer getMoney();

    void setMoney(Integer money);

    Integer getPlayerLevel();

    void setPlayerLevel(Integer playerLevel);

    Integer getSkillPoints();

    void setSkillPoints(Integer skillPoints);

    Integer getAttributePoints();

    void setAttributePoints(Integer attributePoints);
}
