package tq.character.editor.ui.components.partial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.lang.reflect.Method;

public class ReadOnlyVariableRowPanel<T> extends AbstractVariableRowPanel<T, JTextField> {
    private static final Logger log = LoggerFactory.getLogger(FormattedVariableRowPanel.class);

    public ReadOnlyVariableRowPanel(String variableName, T variableValue) {
        super(variableName, variableValue);

        this.variableValue = new JTextField();
        if (variableValue instanceof String) {
            this.variableValue.setText((String) variableValue);
        } else {
            this.variableValue.setText(String.valueOf(variableValue));
        }
        this.variableValue.setEditable(false);

        add(this.variableValue);
    }

    public void createListener(Object instance, Method method) {
        variableValue.addHierarchyListener(e -> {
            log.debug("Getting value from method {}", method.getName());
            executeMethod(instance, method, null);
        });
    }
}
