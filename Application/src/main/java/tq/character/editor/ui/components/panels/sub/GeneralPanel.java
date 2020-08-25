package tq.character.editor.ui.components.panels.sub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import tq.character.editor.data.player.IPlayerData;
import tq.character.editor.ui.components.partial.text.field.TextFormattedDataPanel;
import tq.character.editor.ui.components.partial.text.field.TextReadOnlyDataPanel;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
@ConditionalOnProperty(name = "editor.live.boot")
public class GeneralPanel extends JPanel {
    @Autowired
    private IPlayerData playerData;

    private TextFormattedDataPanel<String> playerName;
    private TextReadOnlyDataPanel<Integer> playerLevel;
    private TextReadOnlyDataPanel<Integer> money;
    private TextReadOnlyDataPanel<Integer> unspentSkillPoints;

    @PostConstruct
    private void init() {
        setLayout(new GridLayout(0, 1));
    }

    public void renderData() throws NoSuchMethodException {
        removeAll();
        createGeneralPanel();
    }

    private void createGeneralPanel() throws NoSuchMethodException {
        Class<?> clazz = playerData.getClass();

        playerName = new TextFormattedDataPanel<>("Character name", playerData.getPlayerName());
        playerName.setDataGetter(playerData, clazz.getMethod("getPlayerName"));
        playerName.createListener(playerData, clazz.getMethod("setPlayerName", String.class));
        add(playerName);

        playerLevel = new TextReadOnlyDataPanel<>("Player level", playerData.getPlayerLevel());
        playerLevel.setDataGetter(playerData, clazz.getMethod("getPlayerLevel"));
        add(playerLevel);

        money = new TextReadOnlyDataPanel<>("Money", playerData.getMoney());
        money.setDataGetter(playerData, clazz.getMethod("getMoney"));
        add(money);

        unspentSkillPoints = new TextReadOnlyDataPanel<>("Unspent skill points", playerData.getUnspentSkillPoints());
        unspentSkillPoints.setDataGetter(playerData, clazz.getMethod("getUnspentSkillPoints"));
        add(unspentSkillPoints);
    }
}
