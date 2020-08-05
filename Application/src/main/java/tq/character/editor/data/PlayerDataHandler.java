package tq.character.editor.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tq.character.editor.data.file.handling.ICodec;
import tq.character.editor.data.file.handling.IFileHandler;
import tq.character.editor.data.file.handling.PlayerData;

import java.nio.ByteBuffer;

@Component
public class PlayerDataHandler implements IDataHandler<PlayerData> {
    @Autowired
    private IFileHandler<ByteBuffer> fileHandler;
    @Autowired
    private ICodec<PlayerData, ByteBuffer> codec;
    private PlayerData playerData;

    @Override
    public void processFile(String filePath) {
        fileHandler.readFile(filePath);
        codec.decode(fileHandler.getRawData());
        playerData = codec.getData();
    }

    @Override
    public PlayerData getData() {
        return playerData;
    }
}
