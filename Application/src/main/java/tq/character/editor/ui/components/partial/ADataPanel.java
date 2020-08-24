package tq.character.editor.ui.components.partial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;

public abstract class ADataPanel<T, V extends JComponent> extends JPanel {
    private static final Logger log = LoggerFactory.getLogger(ADataPanel.class);
    private final Label variableName;
    protected V variableValue;
    protected Object instance;
    protected Method method;

    public ADataPanel(String fieldName) {
        super();
        setLayout(new GridLayout(1, 0));

        this.variableName = new Label(fieldName);

        add(this.variableName);
    }

    public void setDataGetter(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }

    public void reloadData() { // TODO This should probably be protected again, was set to public for testing purpose
        log.debug("Getting value from method {}", method.getName());
        T fetchedValue = (T) executeMethod(instance, method, null);
        setData(fetchedValue);
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
            errorMessage = e.getCause().getMessage();
        } else {
            errorMessage = e.toString();
        }

        JOptionPane.showMessageDialog(this, errorMessage, "Invalid Data", JOptionPane.ERROR_MESSAGE);
    }

    protected abstract void setData(T value);

    protected void updateAllVariableRows(Container c) {
        for (Component comp : c.getComponents()) {
            if (comp instanceof ADataPanel) {
                ((ADataPanel) comp).reloadData();
            } else if (comp instanceof Container) {
                updateAllVariableRows((Container) comp);
            }
        }
    }
}
