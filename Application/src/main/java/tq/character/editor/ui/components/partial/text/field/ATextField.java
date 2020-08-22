package tq.character.editor.ui.components.partial.text.field;

import tq.character.editor.ui.components.partial.ADataPanel;

import javax.swing.*;

public abstract class ATextField<T, V extends JTextField> extends ADataPanel<T, V> {
    public ATextField(String fieldName, T variableValue) {
        super(fieldName, variableValue);
    }

    @Override
    protected void setData(T value) {
        if (value instanceof Integer) {
            variableValue.setText(String.valueOf(value));
        } else {
            variableValue.setText((String) value);
        }
    }
}
