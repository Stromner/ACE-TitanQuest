package tq.character.editor.ui.components.panels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import tq.character.editor.data.player.IPlayerData;
import tq.character.editor.data.player.attributes.IAttributesData;
import tq.character.editor.ui.components.partial.spinner.SpinnerDataPanel;
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
    private SpinnerDataPanel playerLevel;
    private TextFormattedDataPanel<Integer> money;
    private SpinnerDataPanel unspentSkillPoints;
    private SpinnerDataPanel unspentAttributePoints;

    @PostConstruct
    private void init() {
        setLayout(new GridLayout(0, 1));
    }

    public void renderData() throws NoSuchMethodException {
        removeAll();
        Class<?> playerClass = playerData.getClass();
        Class<?> attributesClass = attributesData.getClass();

        playerLevel = new SpinnerDataPanel("Player level", playerData.getPlayerLevel(), 0, 1);
        playerLevel.setDataGetter(playerData, playerClass.getMethod("getPlayerLevel"));
        playerLevel.createListener(playerData, playerClass.getMethod("setPlayerLevel", Integer.class));
        add(playerLevel);

        money = new TextFormattedDataPanel<>("Money", playerData.getMoney());
        money.setDataGetter(playerData, playerClass.getMethod("getMoney"));
        money.createListener(playerData, playerClass.getMethod("setMoney", Integer.class));
        add(money);

        unspentSkillPoints = new SpinnerDataPanel("Unspent skill points", playerData.getUnspentSkillPoints(), 0, 1);
        unspentSkillPoints.setDataGetter(playerData, playerClass.getMethod("getUnspentSkillPoints"));
        unspentSkillPoints.createListener(playerData, playerClass.getMethod("setUnspentSkillPoints", Integer.class));
        add(unspentSkillPoints);

        unspentAttributePoints = new SpinnerDataPanel("Unspent attribute points", attributesData.getUnspentAttributePoints(), 0, 1);
        unspentAttributePoints.setDataGetter(attributesData, attributesClass.getMethod("getUnspentAttributePoints"));
        unspentAttributePoints.createListener(attributesData, attributesClass.getMethod("setUnspentAttributePoints", Integer.class));
        add(unspentAttributePoints);
    }
}
