package tq.character.editor.ui.dialogs;

import javax.swing.*;
import java.awt.*;

public class WaitDialog extends JDialog {
    private JLabel waitLabel;

    public WaitDialog(Frame frame) {
        super(frame);

        init();
    }

    public void showWaitDialog(String displayText) {
        if (waitLabel != null) {
            remove(waitLabel);
        }
        waitLabel = new JLabel(displayText);
        add(new JLabel(displayText));
        pack();
        setLocationRelativeTo(getParent());

        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                setVisible(true);
                return null;
            }
        };
        worker.execute();
    }

    public void closeWaitDialog() {
        setVisible(false);
    }

    private void init() {
        setUndecorated(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }
}
