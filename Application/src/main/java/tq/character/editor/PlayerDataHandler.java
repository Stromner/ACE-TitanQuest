package tq.character.editor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tq.character.editor.file.handling.IDataHandler;
import tq.character.editor.file.handling.IFileHandler;
import tq.character.editor.file.handling.codec.PlayerData;

@Component
public class PlayerDataHandler implements IDataHandler<PlayerData> {
    @Autowired
    private IFileHandler<PlayerData> fileHandler;
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
