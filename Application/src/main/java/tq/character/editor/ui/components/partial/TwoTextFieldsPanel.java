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

public class TwoTextFieldsPanel<T> extends JPanel {
    private static final Logger log = LoggerFactory.getLogger(TwoTextFieldsPanel.class);
    private final Label fieldName;
    private final JFormattedTextField fieldValue;
    private final T defaultValue;

    public TwoTextFieldsPanel(String fieldName, T fieldValue) {
        super();
        defaultValue = fieldValue;
        setLayout(new GridLayout(1, 0));

        this.fieldName = new Label(fieldName);
        this.fieldValue = new JFormattedTextField(FormatCreator.buildFormatter(fieldValue));
        this.fieldValue.setValue(fieldValue);

        add(this.fieldName);
        add(this.fieldValue);
    }

    public void createListener(Object instance, Method method) {
        fieldValue.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                log.debug("Setting value {} for method {}", fieldValue.getText(), method.getName());
                executeMethod(instance, method);
            }
        });
    }

    private void executeMethod(Object instance, Method method) {
        try {
            if (NumberUtils.isCreatable(fieldValue.getText())) {
                method.invoke(instance, Integer.parseInt(fieldValue.getText()));
            } else {
                method.invoke(instance, fieldValue.getText());
            }
        } catch (Exception e) {
            exceptionHandling(e);
        }
    }

    private void exceptionHandling(Exception e) {
        String errorMessage;
        if (e.getCause() != null) {
            errorMessage = e.getCause().getMessage() + ": " + fieldValue.getText();
        } else {
            errorMessage = e.toString();
        }

        JOptionPane.showMessageDialog(
                this, errorMessage, "Invalid Data", JOptionPane.ERROR_MESSAGE);

        if (defaultValue instanceof String) {
            fieldValue.setText((String) defaultValue);
        } else {
            fieldValue.setText(String.valueOf(defaultValue));
        }
    }
}
