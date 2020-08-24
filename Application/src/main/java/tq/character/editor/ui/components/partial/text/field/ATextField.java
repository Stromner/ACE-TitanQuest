package tq.character.editor.ui.components.partial.text.field;

import tq.character.editor.ui.components.partial.ADataPanel;

import javax.swing.*;

public abstract class ATextField<T, V extends JTextField> extends ADataPanel<T, V> {
    public ATextField(String fieldName) {
        super(fieldName);
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
