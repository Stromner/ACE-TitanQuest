package ace.titan.quest.ui.components.panels.sub;

import ace.titan.quest.data.player.attributes.IAttributesData;
import ace.titan.quest.ui.components.partial.ADataPanel;
import ace.titan.quest.ui.components.partial.spinner.SpinnerDataPanel;
import ace.titan.quest.ui.components.partial.text.field.TextReadOnlyDataPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
@ConditionalOnProperty(name = "editor.live.boot")
public class AttributesPanel extends JPanel {
    @Autowired
    private IAttributesData attributesData;

    private SpinnerDataPanel strengthAttribute;
    private SpinnerDataPanel dexterityAttribute;
    private SpinnerDataPanel intelligenceAttribute;
    private SpinnerDataPanel healthAttribute;
    private SpinnerDataPanel manaAttribute;
    private TextReadOnlyDataPanel<Integer> unspentAttributePoints;
    private JButton resetAttributes;

    @PostConstruct
    private void init() {
        setLayout(new GridLayout(0, 1));
    }

    public void renderData() throws NoSuchMethodException {
        removeAll();
        createAttributesPanel();
    }

    private void createAttributesPanel() throws NoSuchMethodException {
        int charStep = attributesData.getCharacteristicGain();
        int fluidStep = attributesData.getFluidGain();

        Class<?> clazz = attributesData.getClass();

        strengthAttribute = new SpinnerDataPanel("Strength", attributesData.getStrengthAttribute(), 0, charStep);
        strengthAttribute.setDataGetter(attributesData, clazz.getMethod("getStrengthAttribute"));
        strengthAttribute.createListener(attributesData, clazz.getMethod("setStrengthAttribute", Integer.class));
        add(strengthAttribute);

        dexterityAttribute = new SpinnerDataPanel("Dexterity", attributesData.getDexterityAttribute(), 0, charStep);
        dexterityAttribute.setDataGetter(attributesData, clazz.getMethod("getDexterityAttribute"));
        dexterityAttribute.createListener(attributesData, clazz.getMethod("setDexterityAttribute", Integer.class));
        add(dexterityAttribute);

        intelligenceAttribute = new SpinnerDataPanel("Intelligence", attributesData.getIntelligenceAttribute(), 0, charStep);
        intelligenceAttribute.setDataGetter(attributesData, clazz.getMethod("getIntelligenceAttribute"));
        intelligenceAttribute.createListener(attributesData, clazz.getMethod("setIntelligenceAttribute", Integer.class));
        add(intelligenceAttribute);

        healthAttribute = new SpinnerDataPanel("Health", attributesData.getHealthAttribute(), 0, fluidStep);
        healthAttribute.setDataGetter(attributesData, clazz.getMethod("getHealthAttribute"));
        healthAttribute.createListener(attributesData, clazz.getMethod("setHealthAttribute", Integer.class));
        add(healthAttribute);

        manaAttribute = new SpinnerDataPanel("Mana", attributesData.getManaAttribute(), 0, fluidStep);
        manaAttribute.setDataGetter(attributesData, clazz.getMethod("getManaAttribute"));
        manaAttribute.createListener(attributesData, clazz.getMethod("setManaAttribute", Integer.class));
        add(manaAttribute);

        unspentAttributePoints = new TextReadOnlyDataPanel<>("Unspent points", attributesData.getUnspentAttributePoints());
        unspentAttributePoints.setDataGetter(attributesData, clazz.getMethod("getUnspentAttributePoints"));
        add(unspentAttributePoints);

        resetAttributes = new JButton("Reset");
        resetAttributes.addActionListener(e -> {
            attributesData.resetAllAttributes();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(resetAttributes);
            reloadAllData(frame.getContentPane());
        });
        add(resetAttributes);
    }

    private void reloadAllData(Container c) {
        for (java.awt.Component comp : c.getComponents()) {
            if (comp instanceof ADataPanel) {
                ((ADataPanel<?, ?>) comp).reloadData();
            } else if (comp instanceof Container) {
                reloadAllData((Container) comp);
            }
        }
    }
}
