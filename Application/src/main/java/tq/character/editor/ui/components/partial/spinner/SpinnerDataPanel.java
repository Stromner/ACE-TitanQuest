package tq.character.editor.ui.components.partial.spinner;

import tq.character.editor.ui.components.partial.ADataPanel;

import javax.swing.*;

public class SpinnerDataPanel<T> extends ADataPanel<T, JSpinner> {

    public SpinnerDataPanel(String fieldName, T variableValue) {
        super(fieldName, variableValue);
    }

    @Override
    protected void setData(T value) {
        if (value instanceof Integer) {
            variableValue.setValue(String.valueOf(value));
        } else {
            variableValue.setValue(value);
        }
    }
}
