package tq.character.editor.file.handling.bytecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tq.character.editor.file.handling.FileHandler;
import tq.character.editor.file.handling.codec.PlayerData;
import tq.character.editor.file.handling.codec.variable.VariableBlock;
import tq.character.editor.file.handling.codec.variable.VariableType;
import tq.character.editor.file.handling.codec.variable.VariablesGlossary;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@Component
public class BytecodeFileHandler implements FileHandler<PlayerData> {
    private static final Logger log = LoggerFactory.getLogger(BytecodeFileHandler.class);
    private ByteBuffer byteBuffer;
    private PlayerData playerData;

    @Override
    public void readFile(String filePath) {
        File f;
        FileInputStream fis;
        try {
            f = new File(filePath);
            fis = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            log.error("Filepath '{}' could not be opened", filePath);
            throw new IllegalArgumentException("Filepath could not be opened");
        }

        prepareByteBuffer((int) f.length());
        readData(fis);
        playerData = decode(byteBuffer);
    }

    @Override
    public void saveFile(PlayerData content, String filePath) {
        // TODO
    }

    @Override
    public PlayerData getData() {
        return playerData;
    }

    private void prepareByteBuffer(int size) {
        byteBuffer = ByteBuffer.allocate(size);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    private <E extends InputStream> void readData(E dataStream) {
        try {
            byteBuffer
                    .put(dataStream.readAllBytes())
                    .rewind();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PlayerData decode(ByteBuffer data) {
        PlayerData playerData = new PlayerData();

        while (data.remaining() > 0) {
            VariableBlock block = new VariableBlock();

            String variableName = getNextVariableName(data);
            VariableType variableType = VariablesGlossary.lookupVariable(variableName);
            Object variableContent = getNextVariableContent(variableType, data);

            block.setVariableName(variableName);
            block.setVariableType(variableType);
            block.setVariableContent(variableContent);
            playerData.addBlock(block);
        }

        return playerData;
    }

    private String getNextVariableName(ByteBuffer data) {
        int variableSize = data.getInt();
        byte[] variableNameBuffer = new byte[variableSize];
        data.get(variableNameBuffer, 0, variableSize);
        return new String(variableNameBuffer);
    }

    private <T> T getNextVariableContent(VariableType variableType, ByteBuffer data) {
        switch (variableType) {
            case INTEGER:
                return (T) Integer.valueOf(data.getInt());
            default:
                log.error("Invalid variable type '{}'", variableType);
                throw new RuntimeException("Invalid variable type");
        }
    }
}
