package tq.character.editor.ui.utils;

import javax.swing.*;
import java.awt.*;

public class GridBagUtil {
    public static void addTitle
            (JComponent parentContainer, String text, int gridX, int gridY) {
        JLabel title = new JLabel(text);
        title.setFont(new Font("Ariel Heavy", Font.BOLD, 22));
        addComponentWithConstraints(parentContainer, title, gridX, gridY, null);
    }

    public static void addComponent(JComponent parentContainer, JComponent component, int gridX, int gridY) {
        addComponentWithConstraints(parentContainer, component, gridX, gridY, null);
    }

    public static void addComponentWithConstraints
            (JComponent parentContainer, JComponent component, int gridX, int gridY, GridBagConstraints additionalConstraints) {
        if (additionalConstraints == null) {
            additionalConstraints = new GridBagConstraints();
        }
        additionalConstraints.gridx = gridX;
        additionalConstraints.gridy = gridY;

        parentContainer.add(component, additionalConstraints);
    }
}
