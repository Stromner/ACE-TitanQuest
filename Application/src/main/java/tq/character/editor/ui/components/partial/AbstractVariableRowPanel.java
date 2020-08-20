package tq.character.editor.ui.components.partial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;

public abstract class AbstractVariableRowPanel<T, V extends JTextField> extends JPanel {
    private static final Logger log = LoggerFactory.getLogger(AbstractVariableRowPanel.class);
    private final Label variableName;
    private final T defaultValue;
    protected V variableValue;

    public AbstractVariableRowPanel(String fieldName, T variableValue) {
        super();
        defaultValue = variableValue;
        setLayout(new GridLayout(1, 0));

        this.variableName = new Label(fieldName);

        add(this.variableName);
    }

    protected Object executeMethod(Object instance, Method method, Object args) {
        try {
            if (args == null) {
                return method.invoke(instance);
            } else {
                return method.invoke(instance, args);
            }
        } catch (Exception e) {
            exceptionHandling(e);
        }
        return variableValue;
    }

    private void exceptionHandling(Exception e) {
        String errorMessage;
        if (e.getCause() != null) {
            errorMessage = e.getCause().getMessage() + ": " + variableValue.getText();
        } else {
            errorMessage = e.toString();
        }

        JOptionPane.showMessageDialog(
                this, errorMessage, "Invalid Data", JOptionPane.ERROR_MESSAGE);

        if (defaultValue instanceof String) {
            variableValue.setText((String) defaultValue);
        } else {
            variableValue.setText(String.valueOf(defaultValue));
        }
    }
}
