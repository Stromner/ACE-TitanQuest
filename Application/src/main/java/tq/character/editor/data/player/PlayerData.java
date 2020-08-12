package tq.character.editor.data.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tq.character.editor.core.errors.IllegalPlayerDataException;
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
    public void setMoney(Integer money) throws IllegalPlayerDataException {
        if (money < 0) {
            log.error("Could not set money to {}, money must be a positive amount", money);
            throw new IllegalPlayerDataException("Money amount can not be a negative amount");
        }
        this.money.setDataContent(money);
    }

    @Override
    public Integer getPlayerLevel() {
        return playerLevel.getDataContent();
    }

    @Override
    public void setPlayerLevel(Integer playerLevel) throws IllegalPlayerDataException {
        int minLevel = 1;
        int maxLevel = 75;
        int skillPointsPerLevel = 3;
        int attributePointsPerLevel = 2;
        // TODO Create a rules class to get rid of the hardcoded values
        //  Properties would be an alternative but feels a bit too loose
        //  Ideally it would function like this:
        //   RuleSet ruleSet = new Ruleset... // Set it up somehow
        //   ruleSet.doTask(playerLevel.setDataContent, playerLevel); // Throws a detailed error if something goes wrong
        if (playerLevel < minLevel || playerLevel > maxLevel) {
            log.error("Could not set player level to {}, player level must be between {} and {}"
                    , playerLevel, minLevel, maxLevel);
            throw new IllegalPlayerDataException("Illegal player level");
        }
        if (playerLevel < getPlayerLevel()) {
            if (getSkillPoints() < skillPointsPerLevel) {
                log.error("Could not lower player level to {}, not enough skill points {}"
                        , playerLevel, getSkillPoints());
                throw new IllegalPlayerDataException("Not enough free skill points");
            } else if (playerLevel < getPlayerLevel() && getAttributePoints() < attributePointsPerLevel) {
                log.error("Could not lower player level to {}, not enough attribute points {}"
                        , playerLevel, getAttributePoints());
                throw new IllegalPlayerDataException("Not enough free attribute points");
            }

            this.playerLevel.setDataContent(playerLevel);
            this.skillPoints.setDataContent(getSkillPoints() - skillPointsPerLevel);
            this.attributePoints.setDataContent(getAttributePoints() - attributePointsPerLevel);
        } else if (playerLevel > getPlayerLevel()) {
            this.playerLevel.setDataContent(playerLevel);
            this.skillPoints.setDataContent(getSkillPoints() + skillPointsPerLevel);
            this.attributePoints.setDataContent(getAttributePoints() + attributePointsPerLevel);
        }
    }

    @Override
    public Integer getSkillPoints() {
        return skillPoints.getDataContent();
    }

    @Override
    public void setSkillPoints(Integer skillPoints) throws IllegalPlayerDataException {
        if (skillPoints < 0) {
            log.error("Could not set skill points to {}, skill points must be a positive amount", skillPoints);
            throw new IllegalPlayerDataException("Skill points can not be a negative amount");
        }
        this.skillPoints.setDataContent(skillPoints);
    }

    @Override
    public Integer getAttributePoints() {
        return attributePoints.getDataContent();
    }

    @Override
    public void setAttributePoints(Integer attributePoints) throws IllegalPlayerDataException {
        if (attributePoints < 0) {
            log.error("Could not set attribute points to {}, attribute points must be a positive amount", attributePoints);
            throw new IllegalPlayerDataException("Attribute points can not be a negative amount");
        }
        this.attributePoints.setDataContent(attributePoints);
    }
}
