package tq.character.editor.file.handling.codec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tq.character.editor.file.handling.codec.variable.VariableBlock;
import tq.character.editor.file.handling.codec.variable.VariableType;
import tq.character.editor.file.handling.codec.variable.VariablesGlossary;

import java.nio.ByteBuffer;

@Component
public class TranslationHandler implements CodecInterpreter<TQPlayerData, ByteBuffer> {
    private static final Logger log = LoggerFactory.getLogger(TranslationHandler.class);

    @Override
    public TQPlayerData decode(ByteBuffer data) {
        TQPlayerData tqPlayerData = new TQPlayerData();

        while (data.remaining() > 0) {
            VariableBlock block = new VariableBlock();

            String variableName = getNextVariableName(data);
            VariableType variableType = VariablesGlossary.lookupVariable(variableName);
            Object variableContent = getNextVariableContent(variableType, data);

            block.setVariableName(variableName);
            block.setVariableType(variableType);
            block.setVariableContent(variableContent);
            tqPlayerData.addBlock(block);
        }

        return tqPlayerData;
    }

    @Override
    public ByteBuffer encode(TQPlayerData data) {
        return null;
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
