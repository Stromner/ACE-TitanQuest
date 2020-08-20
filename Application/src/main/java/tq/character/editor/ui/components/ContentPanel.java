package tq.character.editor.ui.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import tq.character.editor.core.events.DataLayerInitiatedEvent;
import tq.character.editor.ui.components.sub.panels.GeneralPanel;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
@ConditionalOnProperty(name = "editor.live.boot")
public class ContentPanel extends JPanel {
    @Autowired
    private GeneralPanel generalPanel;
    private JTabbedPane sidebar;

    @PostConstruct
    private void init() {
        setLayout(new GridLayout());
        sidebar = new JTabbedPane(JTabbedPane.LEFT);
        sidebar.addTab("General", generalPanel);
        sidebar.addTab("Attributes", new JPanel());
        sidebar.addTab("Cheats", new JPanel());
        sidebar.addChangeListener(e -> {
            try {
                switch (sidebar.getSelectedIndex()) {
                    case 0:
                        generalPanel.renderData();
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    default:
                        throw new RuntimeException("Invalid tab selected");
                }
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            }
        });
    }

    @EventListener
    public void onDatabaseInitiatedEvent(DataLayerInitiatedEvent event) {
        try {
            generalPanel.renderData();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        add(sidebar);
        revalidate();
    }
}
