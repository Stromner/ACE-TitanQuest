package tq.character.editor.ui.components.sub.panels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import tq.character.editor.data.player.IPlayerData;
import tq.character.editor.data.player.attributes.IAttributesData;
import tq.character.editor.ui.components.partial.FormattedVariableRowPanel;
import tq.character.editor.ui.components.partial.ReadOnlyVariableRowPanel;

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

    private FormattedVariableRowPanel<String> playerName;
    private ReadOnlyVariableRowPanel<Integer> playerLevel;
    private ReadOnlyVariableRowPanel<Integer> money;
    private ReadOnlyVariableRowPanel<Integer> unspentSkillPoints;
    private ReadOnlyVariableRowPanel<Integer> unspentAttributePoints;

    @PostConstruct
    private void init() {
        setLayout(new GridLayout(0, 1));
    }

    public void renderData() throws NoSuchMethodException {
        removeAll();
        Class<?> playerClass = playerData.getClass();
        Class<?> attributesClass = attributesData.getClass();

        playerName = new FormattedVariableRowPanel<>("Character name", playerData.getPlayerName());
        playerName.createListener(playerData, playerClass.getMethod("setPlayerName", String.class));
        add(playerName);

        playerLevel = new ReadOnlyVariableRowPanel<>("Player level", playerData.getPlayerLevel());
        playerLevel.createListener(playerData, playerClass.getMethod("getPlayerLevel"));
        add(playerLevel);

        money = new ReadOnlyVariableRowPanel<>("Money", playerData.getMoney());
        money.createListener(playerData, playerClass.getMethod("getMoney"));
        add(money);

        unspentSkillPoints = new ReadOnlyVariableRowPanel<>("Unspent skill points", playerData.getUnspentSkillPoints());
        unspentSkillPoints.createListener(playerData, playerClass.getMethod("getUnspentSkillPoints"));
        add(unspentSkillPoints);

        unspentAttributePoints = new ReadOnlyVariableRowPanel<>("Unspent attribute points", attributesData.getUnspentAttributePoints());
        unspentAttributePoints.createListener(attributesData, attributesClass.getMethod("getUnspentAttributePoints"));
        add(unspentAttributePoints);
    }
}
