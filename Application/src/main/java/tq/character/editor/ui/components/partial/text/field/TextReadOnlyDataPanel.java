package tq.character.editor.ui.components.partial.text.field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class TextReadOnlyDataPanel<T> extends ATextField<T, JTextField> {
    private static final Logger log = LoggerFactory.getLogger(TextReadOnlyDataPanel.class);

    public TextReadOnlyDataPanel(String variableName, T variableValue) {
        super(variableName);

        this.variableValue = new JTextField();
        if (variableValue instanceof String) {
            this.variableValue.setText((String) variableValue);
        } else {
            this.variableValue.setText(String.valueOf(variableValue));
        }
        this.variableValue.setEditable(false);

        add(this.variableValue);
    }
}
