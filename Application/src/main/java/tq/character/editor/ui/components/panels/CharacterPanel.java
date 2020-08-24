package tq.character.editor.ui.components.panels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import tq.character.editor.ui.components.panels.sub.AttributesPanel;
import tq.character.editor.ui.components.panels.sub.GeneralPanel;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
@ConditionalOnProperty(name = "editor.live.boot")
public class CharacterPanel extends JPanel {
    @Autowired
    GeneralPanel generalPanel;
    @Autowired
    AttributesPanel attributesPanel;

    @PostConstruct
    private void init() {
        setLayout(new GridLayout(1, 0));
    }

    public void renderData() throws NoSuchMethodException {
        removeAll();
        generalPanel.renderData();
        attributesPanel.renderData();

        add(generalPanel);
        add(attributesPanel);
    }
}
