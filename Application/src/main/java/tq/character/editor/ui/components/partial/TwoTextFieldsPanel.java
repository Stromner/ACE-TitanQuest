package tq.character.editor.ui.components.partial;

import org.apache.commons.lang3.math.NumberUtils;
import tq.character.editor.data.player.IPlayerData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Method;

public class TwoTextFieldsPanel<T> extends JPanel {
    private final Label fieldName;
    private final JTextField fieldValue;
    private final T defaultValue;

    public TwoTextFieldsPanel(String fieldName, T fieldValue) {
        super();
        defaultValue = fieldValue;
        setLayout(new GridLayout(1, 0));

        this.fieldName = new Label(fieldName);
        if (fieldValue instanceof String) {
            this.fieldValue = new JTextField((String) fieldValue);
        } else {
            this.fieldValue = new JTextField(String.valueOf(fieldValue));
        }

        add(this.fieldName);
        add(this.fieldValue);
    }

    public void setFieldValueListener(IPlayerData instance, Method method) {
        fieldValue.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (isValidInput(e)) {
                    executeMethod(instance, method);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (isValidInput(e)) {
                    executeMethod(instance, method);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (isValidInput(e)) {
                    executeMethod(instance, method);
                }
            }
        });
    }

    private boolean isValidInput(KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && !Character.isLetter(c)) {
            return true;
        }
        if (NumberUtils.isCreatable(fieldValue.getText()) && !Character.isDigit(c)) {
            evt.consume();
            return false;
        }
        return true;
    }

    private void executeMethod(IPlayerData instance, Method method) {
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
