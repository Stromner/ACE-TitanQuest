package tq.character.editor.ui.components;

import javax.swing.*;
import java.awt.*;

public class WaitPanel extends JPanel {
    private final Frame parent;
    private JDialog waitDialog;
    private JLabel waitLabel;

    public WaitPanel(Frame parent) {
        super();
        this.parent = parent;

        init();
    }

    public void showWaitDialog(String dialogText) {
        if (waitLabel != null) {
            waitDialog.remove(waitLabel);
        }
        waitLabel = new JLabel(dialogText);
        waitDialog.add(waitLabel);
        waitDialog.pack();
        waitDialog.setLocationRelativeTo(parent);

        SwingWorker worker = new SwingWorker() {
            @Override
            protected String doInBackground() {
                waitDialog.setVisible(true);
                return "";
            }
        };
        worker.execute();
    }

    public void closeWaitDialog() {
        waitDialog.setVisible(false);
    }

    private void init() {
        waitDialog = new JDialog(parent);
        waitDialog.setUndecorated(true);
        waitDialog.setLocale(parent.getLocale());
        waitDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        waitDialog.setModal(true);
    }
}
