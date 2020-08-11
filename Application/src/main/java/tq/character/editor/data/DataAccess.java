package tq.character.editor.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tq.character.editor.data.file.handling.IFileHandler;
import tq.character.editor.data.player.IPlayerData;

@Service
public class DataAccess<T> implements IDataAccess<T> {
    @Autowired
    IFileHandler<T> fileHandler;
    @Autowired
    IPlayerData playerData;

    @Override
    public T loadFile(String filePath) {
        return fileHandler.loadFile(filePath);
    }

    @Override
    public T getRawData() {
        return fileHandler.getRawData();
    }

    @Override
    public void saveFile(String filePath) {
        fileHandler.saveFile(filePath);
    }

    @Override
    public String getPlayerName() {
        return playerData.getPlayerName();
    }

    @Override
    public void setPlayerName(String playerName) {
        playerData.setPlayerName(playerName);
    }
}
