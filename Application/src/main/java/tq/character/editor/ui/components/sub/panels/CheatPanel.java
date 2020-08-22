package tq.character.editor.ui.components.sub.panels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import tq.character.editor.data.player.IPlayerData;
import tq.character.editor.data.player.attributes.IAttributesData;
import tq.character.editor.ui.components.partial.text.field.TextFormattedDataPanel;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
@ConditionalOnProperty(name = "editor.live.boot")
public class CheatPanel extends JPanel {
    @Autowired
    private IPlayerData playerData;
    @Autowired
    private IAttributesData attributesData;
    private TextFormattedDataPanel<Integer> playerLevel;
    private TextFormattedDataPanel<Integer> money;
    private TextFormattedDataPanel<Integer> unspentSkillPoints;
    private TextFormattedDataPanel<Integer> unspentAttributePoints;

    @PostConstruct
    private void init() {
        setLayout(new GridLayout(0, 1));
    }

    public void renderData() throws NoSuchMethodException {
        removeAll();
        Class<?> playerClass = playerData.getClass();
        Class<?> attributesClass = attributesData.getClass();

        playerLevel = new TextFormattedDataPanel<>("Player level", playerData.getPlayerLevel());
        playerLevel.setDataGetter(playerData, playerClass.getMethod("getPlayerLevel"));
        playerLevel.createListener(playerData, playerClass.getMethod("setPlayerLevel", Integer.class));
        add(playerLevel);

        money = new TextFormattedDataPanel<>("Money", playerData.getMoney());
        money.setDataGetter(playerData, playerClass.getMethod("getMoney"));
        money.createListener(playerData, playerClass.getMethod("setMoney", Integer.class));
        add(money);

        unspentSkillPoints = new TextFormattedDataPanel<>("Unspent skill points", playerData.getUnspentSkillPoints());
        unspentSkillPoints.setDataGetter(playerData, playerClass.getMethod("getUnspentSkillPoints"));
        unspentSkillPoints.createListener(playerData, playerClass.getMethod("setUnspentSkillPoints", Integer.class));
        add(unspentSkillPoints);

        unspentAttributePoints = new TextFormattedDataPanel<>("Unspent attribute points", attributesData.getUnspentAttributePoints());
        unspentAttributePoints.setDataGetter(attributesData, attributesClass.getMethod("getUnspentAttributePoints"));
        unspentAttributePoints.createListener(attributesData, attributesClass.getMethod("setUnspentAttributePoints", Integer.class));
        add(unspentAttributePoints);
    }
}
