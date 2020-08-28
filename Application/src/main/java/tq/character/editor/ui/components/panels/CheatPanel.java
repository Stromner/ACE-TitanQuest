package tq.character.editor.ui.components.panels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import tq.character.editor.data.player.IPlayerData;
import tq.character.editor.data.player.attributes.IAttributesData;
import tq.character.editor.ui.components.partial.spinner.SpinnerDataPanel;
import tq.character.editor.ui.components.partial.text.field.TextFormattedDataPanel;
import tq.character.editor.ui.utils.GridBagUtil;
import tq.character.editor.ui.utils.builders.GridBagConstraintsBuilder;

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
        setLayout(new GridBagLayout());
    }

    public void renderData() throws NoSuchMethodException {
        removeAll();
        GridBagConstraints c = new GridBagConstraintsBuilder()
                .withFill(GridBagConstraints.HORIZONTAL)
                .withWeightX(1)
                .build();

        Class<?> playerClass = playerData.getClass();
        Class<?> attributesClass = attributesData.getClass();

        playerLevel = new SpinnerDataPanel("Player level", playerData.getPlayerLevel(), 0, 1);
        playerLevel.setDataGetter(playerData, playerClass.getMethod("getPlayerLevel"));
        playerLevel.createListener(playerData, playerClass.getMethod("setPlayerLevel", Integer.class));
        GridBagUtil.addComponentWithConstraints(this, playerLevel, 0, 0, c);

        money = new TextFormattedDataPanel<>("Money", playerData.getMoney());
        money.setDataGetter(playerData, playerClass.getMethod("getMoney"));
        money.createListener(playerData, playerClass.getMethod("setMoney", Integer.class));
        GridBagUtil.addComponentWithConstraints(this, money, 0, 1, c);

        unspentSkillPoints = new SpinnerDataPanel("Unspent skill points", playerData.getUnspentSkillPoints(), 0, 1);
        unspentSkillPoints.setDataGetter(playerData, playerClass.getMethod("getUnspentSkillPoints"));
        unspentSkillPoints.createListener(playerData, playerClass.getMethod("setUnspentSkillPoints", Integer.class));
        GridBagUtil.addComponentWithConstraints(this, unspentSkillPoints, 0, 2, c);

        unspentAttributePoints = new SpinnerDataPanel("Unspent attribute points", attributesData.getUnspentAttributePoints(), 0, 1);
        unspentAttributePoints.setDataGetter(attributesData, attributesClass.getMethod("getUnspentAttributePoints"));
        unspentAttributePoints.createListener(attributesData, attributesClass.getMethod("setUnspentAttributePoints", Integer.class));
        GridBagUtil.addComponentWithConstraints(this, unspentAttributePoints, 0, 3, c);
    }
}
