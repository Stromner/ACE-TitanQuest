package tq.character.editor.data.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tq.character.editor.core.errors.IllegalPlayerDataException;
import tq.character.editor.core.events.DataLayerInitiatedEvent;
import tq.character.editor.core.events.DatabaseInitiatedEvent;
import tq.character.editor.data.player.attributes.IAttributesData;
import tq.character.editor.database.IDataContentRepository;
import tq.character.editor.database.entities.content.IntContent;
import tq.character.editor.database.entities.content.UTF16Content;


@Service
@Transactional // TODO This might not be needed
public class PlayerData implements IPlayerData {
    private static final Logger log = LoggerFactory.getLogger(PlayerData.class);

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private IDataContentRepository contentRepository;
    @Autowired
    IAttributesData attributeData;

    @Value("${editor.player.min.level}")
    private int minLevel;
    @Value("${editor.player.max.level}")
    private int maxLevel;
    @Value("${editor.player.skills.gain}")
    private int skillsGain;
    @Value("${editor.player.attributes.gain}")
    private int attributesGain;

    private UTF16Content playerName;
    private IntContent money;
    private IntContent unspentSkillPoints;
    private IntContent playerLevel;

    @EventListener
    public void onDatabaseInitiatedEvent(DatabaseInitiatedEvent event) {
        playerName = contentRepository.findByVariableName("myPlayerName");
        money = contentRepository.findByVariableName("money");
        unspentSkillPoints = contentRepository.findByVariableName("skillPoints");
        playerLevel = contentRepository.findByVariableName("currentStats.charLevel");

        eventPublisher.publishEvent(new DataLayerInitiatedEvent(this));
        log.info("Data layer initiated");
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
    public void setPlayerLevel(Integer newLevel) throws IllegalPlayerDataException {
        if (newLevel < minLevel || newLevel > maxLevel) {
            log.error("Could not set player level to {}, player level must be between {} and {}"
                    , playerLevel, minLevel, maxLevel);
            throw new IllegalPlayerDataException("Illegal player level");
        }
        if (newLevel < getPlayerLevel()) {
            if (getUnspentSkillPoints() < skillsGain) {
                log.error("Could not lower player level to {}, not enough skill points {}"
                        , playerLevel, getUnspentSkillPoints());
                throw new IllegalPlayerDataException("Not enough free skill points");
            } else if (newLevel < getPlayerLevel() && attributeData.getUnspentAttributePoints() < attributesGain) {
                log.error("Could not lower player level to {}, not enough attribute points {}"
                        , playerLevel, attributeData.getUnspentAttributePoints());
                throw new IllegalPlayerDataException("Not enough free attribute points");
            }
        }

        int levelDiff = newLevel - getPlayerLevel();
        playerLevel.setDataContent(newLevel);
        setUnspentSkillPoints(getUnspentSkillPoints() + levelDiff * skillsGain);
        attributeData.setUnspentAttributePoints(attributeData.getUnspentAttributePoints() + levelDiff * attributesGain);
    }

    @Override
    public Integer getUnspentSkillPoints() {
        return unspentSkillPoints.getDataContent();
    }

    @Override
    public void setUnspentSkillPoints(Integer skillPoints) throws IllegalPlayerDataException {
        if (skillPoints < 0) {
            log.error("Could not set skill points to {}, skill points must be a positive amount", skillPoints);
            throw new IllegalPlayerDataException("Skill points can not be a negative amount");
        }
        unspentSkillPoints.setDataContent(skillPoints);
    }
}
