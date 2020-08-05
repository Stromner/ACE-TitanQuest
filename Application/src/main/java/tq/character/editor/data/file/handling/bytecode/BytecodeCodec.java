package tq.character.editor.data.file.handling.bytecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tq.character.editor.data.file.handling.ICodec;
import tq.character.editor.data.file.handling.PlayerData;
import tq.character.editor.data.file.handling.variable.Block;
import tq.character.editor.data.file.handling.variable.VariableInfo;
import tq.character.editor.data.file.handling.variable.VariableType;
import tq.character.editor.data.file.handling.variable.VariablesGlossary;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Component
public class BytecodeCodec implements ICodec<PlayerData, ByteBuffer> {
    private static final Logger log = LoggerFactory.getLogger(BytecodeCodec.class);
    private PlayerData playerData;

    @Override
    public ByteBuffer encode(PlayerData rawData) {
        return null;
    }

    @Override
    public PlayerData decode(ByteBuffer structuredData) {
        playerData = new PlayerData();

        while (structuredData.remaining() > 0) {
            String variableName = readUTF8(structuredData);
            VariableInfo variableInfo;
            if (variableName.equals("begin_block")) {
                variableInfo = readBlock(structuredData);
            } else {
                variableInfo = readVariable(structuredData, variableName);
            }
            playerData.addBlock(variableInfo);
        }

        return playerData;
    }

    @Override
    public PlayerData getData() {
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
        if (variableType == null) {
            log.error("Could not find variableName '{}' in the glossary", variableName);
            throw new RuntimeException("variableName not in glossary");
        }
        var variableContent = getNextVariableContent(variableType, data);

        VariableInfo variableInfo = new VariableInfo();
        variableInfo.setVariableName(variableName);
        variableInfo.setVariableType(variableType);
        variableInfo.setVariableContent(variableContent);
        return variableInfo;
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
}
