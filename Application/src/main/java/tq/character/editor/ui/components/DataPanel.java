package tq.character.editor.ui.components;

import tq.character.editor.data.player.IPlayerData;
import tq.character.editor.ui.components.partial.TwoTextFieldsPanel;

import javax.swing.*;
import java.awt.*;

public class DataPanel extends JPanel {
    private final IPlayerData playerData;
    private TwoTextFieldsPanel playerName;
    private TwoTextFieldsPanel playerLevel;
    private TwoTextFieldsPanel money;
    private TwoTextFieldsPanel freeSkillPoints;
    private TwoTextFieldsPanel freeAttributePoints;

    public DataPanel(IPlayerData playerData) {
        super();
        this.playerData = playerData;

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
        Class c = playerData.getClass();

        playerName = new TwoTextFieldsPanel("Character name", playerData.getPlayerName());
        playerName.createListener(playerData, c.getMethod("setPlayerName", String.class));
        add(playerName);

        playerLevel = new TwoTextFieldsPanel("Player level", playerData.getPlayerLevel());
        playerLevel.createListener(playerData, c.getMethod("setPlayerLevel", Integer.class));
        add(playerLevel);

        money = new TwoTextFieldsPanel("Money", playerData.getMoney());
        money.createListener(playerData, c.getMethod("setMoney", Integer.class));
        add(money);

        freeSkillPoints = new TwoTextFieldsPanel("Free skill points", playerData.getSkillPoints());
        freeSkillPoints.createListener(playerData, c.getMethod("setSkillPoints", Integer.class));
        add(freeSkillPoints);

        freeAttributePoints = new TwoTextFieldsPanel("Free attribute points", playerData.getAttributePoints());
        freeAttributePoints.createListener(playerData, c.getMethod("setAttributePoints", Integer.class));
        add(freeAttributePoints);
    }
}
