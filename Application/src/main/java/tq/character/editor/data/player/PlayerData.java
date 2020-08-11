package tq.character.editor.data.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tq.character.editor.core.events.DatabaseInitiatedEvent;
import tq.character.editor.database.IDataContentRepository;
import tq.character.editor.database.entities.content.IntContent;
import tq.character.editor.database.entities.content.UTF16Content;


@Service
@Transactional // TODO This might not be needed
public class PlayerData implements IPlayerData {
    private static final Logger log = LoggerFactory.getLogger(PlayerData.class);
    @Autowired
    private IDataContentRepository contentRepository;
    private UTF16Content playerName;
    private IntContent money;
    private IntContent skillPoints;
    private IntContent attributePoints;
    private IntContent playerLevel;

    @EventListener
    public void onDatabaseInitiatedEvent(DatabaseInitiatedEvent event) {
        playerName = contentRepository.findByVariableName("myPlayerName");
        money = contentRepository.findByVariableName("money");
        skillPoints = contentRepository.findByVariableName("skillPoints");
        attributePoints = contentRepository.findByVariableName("modifierPoints");
        playerLevel = contentRepository.findByVariableName("currentStats.charLevel");
    }

    @Override
    public String getPlayerName() {
        return playerName.getDataContent();
    }

    @Override
    public void setPlayerName(String playerName) {
        this.playerName.setDataContent(playerName);
    }

    @Override
    public Integer getMoney() {
        return money.getDataContent();
    }

    @Override
    public void setMoney(Integer money) {
        // TODO Deny negative amount and escalate an error message back to the UI
        this.money.setDataContent(money);
    }

    @Override
    public Integer getPlayerLevel() {
        return playerLevel.getDataContent();
    }

    @Override
    public void setPlayerLevel(Integer playerLevel) {
        // TODO This should modify setSkillPoints and setAttributePoints accordingly
        // TODO If player level > max escalate an error message back to the UI
        // TODO If not enough free skill or attribute points to lower level escalate an error message back to the UI
        this.playerLevel.setDataContent(playerLevel);
    }

    @Override
    public Integer getSkillPoints() {
        return skillPoints.getDataContent();
    }

    @Override
    public void setSkillPoints(Integer skillPoints) {
        // TODO Deny negative amount and escalate an error message back to the UI
        this.skillPoints.setDataContent(skillPoints);
    }

    @Override
    public Integer getAttributePoints() {
        return attributePoints.getDataContent();
    }

    @Override
    public void setAttributePoints(Integer attributePoints) {
        // TODO Deny negative amount and escalate an error message back to the UI
        this.attributePoints.setDataContent(attributePoints);
    }
}
