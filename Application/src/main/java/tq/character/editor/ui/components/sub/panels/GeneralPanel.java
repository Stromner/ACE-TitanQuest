package tq.character.editor.ui.components.sub.panels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import tq.character.editor.data.player.IPlayerData;
import tq.character.editor.data.player.attributes.IAttributesData;
import tq.character.editor.ui.components.partial.TextFormattedDataPanel;
import tq.character.editor.ui.components.partial.TextReadOnlyDataPanel;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
@ConditionalOnProperty(name = "editor.live.boot")
public class GeneralPanel extends JPanel {
    @Autowired
    private IPlayerData playerData;
    @Autowired
    private IAttributesData attributesData;

    private TextFormattedDataPanel<String> playerName;
    private TextReadOnlyDataPanel<Integer> playerLevel;
    private TextReadOnlyDataPanel<Integer> money;
    private TextReadOnlyDataPanel<Integer> unspentSkillPoints;
    private TextReadOnlyDataPanel<Integer> unspentAttributePoints;

    @PostConstruct
    private void init() {
        setLayout(new GridLayout(0, 1));
    }

    public void renderData() throws NoSuchMethodException {
        removeAll();
        Class<?> playerClass = playerData.getClass();
        Class<?> attributesClass = attributesData.getClass();

        playerName = new TextFormattedDataPanel<>("Character name", playerData.getPlayerName());
        playerName.setDataGetter(playerData, playerClass.getMethod("getPlayerName"));
        playerName.createListener(playerData, playerClass.getMethod("setPlayerName", String.class));
        add(playerName);

        playerLevel = new TextReadOnlyDataPanel<>("Player level", playerData.getPlayerLevel());
        playerLevel.setDataGetter(playerData, playerClass.getMethod("getPlayerLevel"));
        add(playerLevel);

        money = new TextReadOnlyDataPanel<>("Money", playerData.getMoney());
        money.setDataGetter(playerData, playerClass.getMethod("getMoney"));
        add(money);

        unspentSkillPoints = new TextReadOnlyDataPanel<>("Unspent skill points", playerData.getUnspentSkillPoints());
        unspentSkillPoints.setDataGetter(playerData, playerClass.getMethod("getUnspentSkillPoints"));
        add(unspentSkillPoints);

        unspentAttributePoints = new TextReadOnlyDataPanel<>("Unspent attribute points", attributesData.getUnspentAttributePoints());
        unspentAttributePoints.setDataGetter(attributesData, attributesClass.getMethod("getUnspentAttributePoints"));
        add(unspentAttributePoints);
    }
}
