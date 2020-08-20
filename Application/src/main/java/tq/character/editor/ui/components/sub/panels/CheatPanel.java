package tq.character.editor.ui.components.sub.panels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import tq.character.editor.data.player.IPlayerData;
import tq.character.editor.data.player.attributes.IAttributesData;
import tq.character.editor.ui.components.partial.FormattedVariableRowPanel;

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
    private FormattedVariableRowPanel<Integer> playerLevel;
    private FormattedVariableRowPanel<Integer> money;
    private FormattedVariableRowPanel<Integer> unspentSkillPoints;
    private FormattedVariableRowPanel<Integer> unspentAttributePoints;

    @PostConstruct
    private void init() {
        setLayout(new GridLayout(0, 1));
    }

    public void renderData() throws NoSuchMethodException {
        removeAll();
        Class<?> playerClass = playerData.getClass();
        Class<?> attributesClass = attributesData.getClass();

        playerLevel = new FormattedVariableRowPanel<>("Player level", playerData.getPlayerLevel());
        playerLevel.createListener(playerData, playerClass.getMethod("setPlayerLevel", Integer.class));
        add(playerLevel);

        money = new FormattedVariableRowPanel<>("Money", playerData.getMoney());
        money.createListener(playerData, playerClass.getMethod("setMoney", Integer.class));
        add(money);

        unspentSkillPoints = new FormattedVariableRowPanel<>("Unspent skill points", playerData.getUnspentSkillPoints());
        unspentSkillPoints.createListener(playerData, playerClass.getMethod("setUnspentSkillPoints", Integer.class));
        add(unspentSkillPoints);

        unspentAttributePoints = new FormattedVariableRowPanel<>("Unspent attribute points", attributesData.getUnspentAttributePoints());
        unspentAttributePoints.createListener(attributesData, attributesClass.getMethod("setUnspentAttributePoints", Integer.class));
        add(unspentAttributePoints);
    }
}
