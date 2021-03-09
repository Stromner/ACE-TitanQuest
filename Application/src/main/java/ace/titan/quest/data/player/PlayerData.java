package ace.titan.quest.data.player;

import ace.titan.quest.core.errors.IllegalPlayerDataException;
import ace.titan.quest.core.events.AttributesInitiatedEvent;
import ace.titan.quest.core.events.DataLayerInitiatedEvent;
import ace.titan.quest.data.player.attributes.IAttributesData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ace.titan.quest.database.IDataContentRepository;
import ace.titan.quest.database.entities.content.IntContent;
import ace.titan.quest.database.entities.content.UTF16Content;


@Service
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
    @Value("${editor.player.skills.per.level}")
    private int skillsPerLevel;
    @Value("${editor.player.attributes.per.level}")
    private int attributesPerLevel;

    private UTF16Content playerName;
    private IntContent money;
    private IntContent unspentSkillPoints;
    private IntContent playerLevel;

    @EventListener
    public void onDatabaseInitiatedEvent(AttributesInitiatedEvent event) {
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
        contentRepository.saveAndFlush(this.playerName);
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
        contentRepository.saveAndFlush(this.money);
    }

    @Override
    public Integer getPlayerLevel() {
        return playerLevel.getDataContent();
    }

    @Override
    public void setPlayerLevel(Integer newLevel) throws IllegalPlayerDataException {
        int levelDiff = newLevel - getPlayerLevel();
        int newSkillPoints = getUnspentSkillPoints() + levelDiff * skillsPerLevel;
        int newAttributePoints = attributeData.getUnspentAttributePoints() + levelDiff * attributesPerLevel;

        if (newLevel < minLevel || newLevel > maxLevel) {
            log.error("Could not set player level to {}, player level must be between {} and {}"
                    , newLevel, minLevel, maxLevel);
            throw new IllegalPlayerDataException("Illegal player level");
        }
        if (newSkillPoints < 0 || newAttributePoints < 0) {
            log.error("Could not set player level to {}", newLevel);
            throw new IllegalPlayerDataException("Invalid modification of player level");
        }

        playerLevel.setDataContent(newLevel);
        setUnspentSkillPoints(newSkillPoints);
        attributeData.setUnspentAttributePoints(newAttributePoints);
        contentRepository.saveAndFlush(this.playerLevel);
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
        contentRepository.saveAndFlush(unspentSkillPoints);
    }
}
