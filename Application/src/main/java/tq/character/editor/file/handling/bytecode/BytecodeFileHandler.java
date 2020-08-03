package tq.character.editor.file.handling.bytecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tq.character.editor.file.handling.IFileHandler;
import tq.character.editor.file.handling.codec.PlayerData;
import tq.character.editor.file.handling.codec.variable.Block;
import tq.character.editor.file.handling.codec.variable.VariableInfo;
import tq.character.editor.file.handling.codec.variable.VariableType;
import tq.character.editor.file.handling.codec.variable.VariablesGlossary;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * TODO Add explanation how the file is encoded
 */
@Component
public class BytecodeFileHandler implements IFileHandler<PlayerData> {
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
        decode(byteBuffer);
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
        playerData = new PlayerData();

        while (data.remaining() > 0) {
            String variableName = readUTF8(data);
            VariableInfo variableInfo;
            if (variableName.equals("begin_block")) {
                variableInfo = readBlock(data);
            } else {
                variableInfo = readVariable(data, variableName);
            }
            playerData.addBlock(variableInfo);
        }

        return playerData;
    }

    private Block readBlock(ByteBuffer data) {
        Block block = new Block();
        data.getInt(); // Discard

        String variableName = readUTF8(data);
        while (!variableName.equals("end_block")) {
            VariableInfo variableInfo;
            if (variableName.equals("begin_block")) {
                variableInfo = readBlock(data);
            } else {
                variableInfo = readVariable(data, variableName);
            }
            block.addBlock(variableInfo);
            variableName = readUTF8(data);
        }

        data.getInt(); // Discard
        return block;
    }

    private VariableInfo readVariable(ByteBuffer data, String variableName) {
        VariableType variableType = VariablesGlossary.lookupVariable(variableName);
        var variableContent = getNextVariableContent(variableType, data);

        VariableInfo variableInfo = new VariableInfo();
        variableInfo.setVariableName(variableName);
        variableInfo.setVariableType(variableType);
        variableInfo.setVariableContent(variableContent);
        return variableInfo;
    }

    private String readUTF8(ByteBuffer data) {
        return new String(readBytes(data, false));
    }

    private String readUTF16(ByteBuffer data) {
        return new String(readBytes(data, true), StandardCharsets.UTF_16LE);
    }

    private byte[] readBytes(ByteBuffer data, boolean doubleLen) {
        int dataSize = data.getInt();
        if (doubleLen) {
            dataSize *= 2;
        }

        byte[] dataContent = new byte[dataSize];
        data.get(dataContent, 0, dataSize);
        return dataContent;
    }

    private byte[] readByteData(ByteBuffer data) {
        int dataSize = data.getInt();
        byte[] dataContent = new byte[dataSize];
        data.get(dataContent, 0, dataSize);
        return dataContent;
    }

    private <T> T getNextVariableContent(VariableType variableType, ByteBuffer data) {
        switch (variableType) {
            case INTEGER:
                return (T) Integer.valueOf(data.getInt());
            case UTF8:
                return (T) readUTF8(data);
            case UTF16:
                return (T) readUTF16(data);
            case ID:
                byte[] id = new byte[16];
                return (T) data.get(id, 0, 16);
            case STREAM:
                return (T) readByteData(data);
            default:
                log.error("Invalid variable type '{}'", variableType);
                throw new RuntimeException("Invalid variable type");
        }
    }
}
