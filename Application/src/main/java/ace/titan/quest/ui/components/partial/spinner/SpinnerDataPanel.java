package ace.titan.quest.ui.components.partial.spinner;

import ace.titan.quest.ui.components.partial.ADataPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.lang.reflect.Method;

public class SpinnerDataPanel extends ADataPanel<Integer, JSpinner> {
    private static final Logger log = LoggerFactory.getLogger(SpinnerDataPanel.class);

    public SpinnerDataPanel(String fieldName, Integer variableValue, int minValue, int stepSize) {
        super(fieldName);

        this.variableValue = new JSpinner(
                new SpinnerNumberModel(variableValue.intValue(), minValue, Integer.MAX_VALUE, stepSize));
        ((JSpinner.DefaultEditor) this.variableValue.getEditor()).getTextField().setEditable(false);
        add(this.variableValue);
    }

    public void createListener(Object instance, Method method) {
        variableValue.addChangeListener(e -> {
            log.debug("Setting value {} for method {}", variableValue.getValue(), method.getName());
            executeMethod(instance, method, variableValue.getValue());
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(variableValue);
            updateAllVariableRows(frame.getContentPane());
        });
    }

    @Override
    protected void setData(Integer value) {
        variableValue.setValue(value);
    }
}
