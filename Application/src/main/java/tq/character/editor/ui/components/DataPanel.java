package tq.character.editor.ui.components;

import tq.character.editor.data.player.IPlayerData;
import tq.character.editor.data.player.attributes.IAttributesData;
import tq.character.editor.ui.components.partial.TwoTextFieldsPanel;

import javax.swing.*;
import java.awt.*;

public class DataPanel extends JPanel {
    private final IPlayerData playerData;
    private final IAttributesData attributeData;
    private TwoTextFieldsPanel<String> playerName;
    private TwoTextFieldsPanel<Integer> playerLevel;
    private TwoTextFieldsPanel<Integer> money;
    private TwoTextFieldsPanel<Integer> unspentSkillPoints;
    private TwoTextFieldsPanel<Integer> unspentAttributePoints;

    public DataPanel(IPlayerData playerData, IAttributesData attributeData) {
        super();
        this.playerData = playerData;
        this.attributeData = attributeData;

        init();
    }

    private void init() {
        setLayout(new GridLayout(0, 1));

        try {
            renderData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void renderData() throws NoSuchMethodException {
        Class<?> c = playerData.getClass();

        playerName = new TwoTextFieldsPanel<>("Character name", playerData.getPlayerName());
        playerName.createListener(playerData, c.getMethod("setPlayerName", String.class));
        add(playerName);

        playerLevel = new TwoTextFieldsPanel<>("Player level", playerData.getPlayerLevel());
        playerLevel.createListener(playerData, c.getMethod("setPlayerLevel", Integer.class));
        add(playerLevel);

        money = new TwoTextFieldsPanel<>("Money", playerData.getMoney());
        money.createListener(playerData, c.getMethod("setMoney", Integer.class));
        add(money);

        unspentSkillPoints = new TwoTextFieldsPanel<>("Unspent skill points", playerData.getUnspentSkillPoints());
        unspentSkillPoints.createListener(playerData, c.getMethod("setUnspentSkillPoints", Integer.class));
        add(unspentSkillPoints);

        unspentAttributePoints = new TwoTextFieldsPanel<>("Unspent attribute points", attributeData.getUnspentAttributePoints());
        unspentAttributePoints.createListener(playerData, c.getMethod("setUnspentAttributePoints", Integer.class));
        add(unspentAttributePoints);
    }
}
