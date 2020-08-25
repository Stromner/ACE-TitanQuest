package tq.character.editor.ui.components.panels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import tq.character.editor.ui.components.panels.sub.AttributesPanel;
import tq.character.editor.ui.components.panels.sub.GeneralPanel;
import tq.character.editor.ui.utils.GridBagUtil;
import tq.character.editor.ui.utils.builders.GridBagConstraintsBuilder;

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
        setLayout(new GridBagLayout());
    }

    public void renderData() throws NoSuchMethodException {
        removeAll();

        GridBagConstraints c = new GridBagConstraintsBuilder()
                .withGridHeight(2)
                .withFill(GridBagConstraints.VERTICAL)
                .withDefaultWidthPadding()
                .build();
        GridBagUtil.addComponentWithConstraints(this, new JSeparator(SwingConstants.VERTICAL), 1, 0, c);

        renderGeneralPanel();
        renderAttributePanel();
    }

    private void renderGeneralPanel() throws NoSuchMethodException {
        generalPanel.renderData();

        GridBagConstraints c = new GridBagConstraintsBuilder()
                .withFillOutWeight()
                .build();

        GridBagUtil.addTitle(this, "General", 0, 0);
        GridBagUtil.addComponentWithConstraints(this, generalPanel, 0, 1, c);
    }

    private void renderAttributePanel() throws NoSuchMethodException {
        attributesPanel.renderData();

        GridBagConstraints c = new GridBagConstraintsBuilder()
                .withFillOutWeight()
                .build();

        GridBagUtil.addTitle(this, "Attributes", 2, 0);
        GridBagUtil.addComponentWithConstraints(this, attributesPanel, 2, 1, c);
    }
}
