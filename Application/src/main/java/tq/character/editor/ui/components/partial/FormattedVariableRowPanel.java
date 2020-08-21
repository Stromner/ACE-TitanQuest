package tq.character.editor.ui.components.partial;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tq.character.editor.ui.utils.FormatCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Method;

public class FormattedVariableRowPanel<T> extends AbstractVariableRowPanel<T, JFormattedTextField> {
    private static final Logger log = LoggerFactory.getLogger(FormattedVariableRowPanel.class);

    public FormattedVariableRowPanel(String variableName, T variableValue) {
        super(variableName, variableValue);

        this.variableValue = new JFormattedTextField(FormatCreator.buildFormatter(variableValue));
        this.variableValue.setValue(variableValue);

        add(this.variableValue);
    }

    public void createListener(Object instance, Method method) {
        variableValue.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                log.debug("Setting value {} for method {}", variableValue.getText(), method.getName());
                if (NumberUtils.isCreatable(variableValue.getText())) {
                    executeMethod(instance, method, Integer.parseInt(variableValue.getText()));
                } else {
                    executeMethod(instance, method, variableValue.getText());
                }
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(getOuterInstance());
                updateAllVariableRows(frame.getContentPane());
            }
        });
    }

    private void updateAllVariableRows(Container c) {
        for (Component comp : c.getComponents()) {
            if (comp instanceof AbstractVariableRowPanel) {
                ((AbstractVariableRowPanel) comp).refetchData();
            } else if (comp instanceof Container) {
                updateAllVariableRows((Container) comp);
            }
        }
    }

    private FormattedVariableRowPanel<?> getOuterInstance() {
        return this;
    }
}
