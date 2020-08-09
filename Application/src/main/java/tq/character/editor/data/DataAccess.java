package tq.character.editor.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tq.character.editor.data.file.handling.IFileHandler;
import tq.character.editor.data.player.IPlayerData;

import java.nio.ByteBuffer;

@Component
public class DataAccess implements IDataAccess {
    @Autowired
    IFileHandler<ByteBuffer> fileHandler;
    @Autowired
    IPlayerData playerData;

    @Override
    public ByteBuffer loadFile(String filePath) {
        return fileHandler.loadFile(filePath);
    }

    @Override
    public ByteBuffer getRawData() {
        return fileHandler.getRawData();
    }

    @Override
    public void saveFile(String filePath) {
        fileHandler.saveFile(filePath);
    }
}
