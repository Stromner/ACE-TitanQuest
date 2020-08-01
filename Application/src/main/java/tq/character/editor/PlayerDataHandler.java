package tq.character.editor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tq.character.editor.file.handling.DataHandler;
import tq.character.editor.file.handling.FileHandler;
import tq.character.editor.file.handling.codec.PlayerData;

@Component
public class PlayerDataHandler implements DataHandler<PlayerData> {
    @Autowired
    private FileHandler<PlayerData> fileHandler;
    private PlayerData playerData;

    @Override
    public void processFile(String filePath) {
        fileHandler.readFile(filePath);
        playerData = fileHandler.getData();
    }

    @Override
    public PlayerData getData() {
        return playerData;
    }
}
