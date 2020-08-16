package tq.character.editor.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import tq.character.editor.core.events.DataLayerInitiatedEvent;
import tq.character.editor.data.file.handling.IFileHandler;
import tq.character.editor.data.player.IPlayerData;
import tq.character.editor.ui.components.DataPanel;
import tq.character.editor.ui.dialogs.WaitDialog;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.nio.ByteBuffer;

@Component
@ConditionalOnProperty(name = "editor.live.boot")
public class ApplicationWindow {
    @Value("classpath:ui/ApplicationIcon.png")
    private Resource applicationIcon;

    @Autowired
    IFileHandler<ByteBuffer> fileHandler;
    @Autowired
    IPlayerData playerData;

    private JFrame frame;
    private JFileChooser fileChooser;
    private WaitDialog waitDialog;
    private DataPanel dataPanel;

    @PostConstruct
    public void init() {
        createFrame();
        createFileChooser();
        createMenu();

        waitDialog = new WaitDialog(frame);

        frame.setVisible(true);
    }

    @EventListener
    public void onDatabaseInitiatedEvent(DataLayerInitiatedEvent event) {
        dataPanel = new DataPanel(playerData);
        frame.setContentPane(dataPanel);

        waitDialog.closeWaitDialog();

        frame.revalidate();
    }

    private void createFrame() {
        frame = new JFrame("TQCharacterEditor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        configureFrame();
    }

    private void configureFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            ImageIcon frameIcon = new ImageIcon(applicationIcon.getURL());
            frame.setIconImage(frameIcon.getImage());

            SwingUtilities.updateComponentTreeUI(frame);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createFileChooser() {
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    private void createMenu() {
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem item;
        item = new JMenuItem("Open File");
        item.addActionListener(event -> openFileChooser());
        menu.add(item);

        item = new JMenuItem("Save File");
        item.addActionListener(event -> saveFileChooser());
        menu.add(item);

        item = new JMenuItem("Exit");
        item.addActionListener(event -> System.exit(0));
        menu.add(item);

        frame.setJMenuBar(menuBar);
    }

    private void openFileChooser() {
        if (fileChooser.showOpenDialog(frame) != JFileChooser.CANCEL_OPTION) {
            waitDialog.showWaitDialog("Parsing file, please wait...");
            fileHandler.loadFile(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void saveFileChooser() {
        if (fileChooser.showSaveDialog(frame) != JFileChooser.CANCEL_OPTION) {
            waitDialog.showWaitDialog("Saving file, please wait...");
            fileHandler.saveFile(fileChooser.getSelectedFile().getAbsolutePath());
            waitDialog.closeWaitDialog();
        }
    }

}
