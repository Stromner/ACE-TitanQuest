package tq.character.editor.ui.components.partial.text.field;

import javax.swing.*;

public class TextReadOnlyDataPanel<T> extends ATextField<T, JTextField> {
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
