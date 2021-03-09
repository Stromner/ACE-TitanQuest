package ace.titan.quest.data.file.handling.reader;

import ace.titan.quest.core.events.DatabaseInitiatedEvent;
import ace.titan.quest.core.events.FailedToLoadFileEvent;
import ace.titan.quest.database.IDataContentRepository;
import ace.titan.quest.database.entities.Variable;
import ace.titan.quest.database.entities.VariableType;
import ace.titan.quest.database.entities.content.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * File structure:
 * <p>
 * Variable:
 * <4 bytes describing how many bytes the variableName starting at next byte is>
 * <x number of bytes for the variableName>
 * <4 bytes describing how many bytes the variableContent starting at next byte is>
 * SPECIAL CASE: ID - always the next 16 bytes
 * Block:
 * <BEGIN_BLOCK/END_BLOCK containers for Variables>
 */
@Component
public class DatabaseFileReader implements IFileReader<ByteBuffer> {
    private static final Logger log = LoggerFactory.getLogger(DatabaseFileReader.class);
    private static final byte[] BEGIN_BLOCK =
            {0x0B, 0x00, 0x00, 0x00, 0x62, 0x65, 0x67, 0x69, 0x6E, 0x5F, 0x62, 0x6C, 0x6F, 0x63, 0x6B, (byte) 0xCE, (byte) 0xFA, 0x1D, (byte) 0xB0};
    private static final byte[] END_BLOCK =
            {0x09, 0x00, 0x00, 0x00, 0x65, 0x6E, 0x64, 0x5F, 0x62, 0x6C, 0x6F, 0x63, 0x6B, (byte) 0xDE, (byte) 0xC0, (byte) 0xAD, (byte) 0xDE};
    private ByteBuffer byteBuffer;
    @Autowired
    private IDataContentRepository contentRepository;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public ByteBuffer loadFile(String filePath) {
        log.info("Started parsing file...");
        contentRepository.deleteAll();
        readFile(filePath);
        loadFileIntoDatabase(getRawData());

        log.info("Database initiated");
        eventPublisher.publishEvent(new DatabaseInitiatedEvent(this));
        log.info("Finished parsing file");

        return getRawData();
    }

    @Override
    public ByteBuffer getRawData() {
        return byteBuffer;
    }

    private void readFile(String filePath) {
        File f;
        FileInputStream fis;
        try {
            f = new File(filePath);
            fis = new FileInputStream(f);

            prepareByteBuffer((int) f.length());
            readData(fis);
            fis.close();
        } catch (IOException e) {
            log.error("Error while reading file {}", e.getMessage());
            throw new RuntimeException(e.getCause());
        }
    }

    private void loadFileIntoDatabase(ByteBuffer rawData) {
        while (rawData.remaining() > 0) {
            String variableName = readUTF8(rawData);
            contentRepository.saveAndFlush(parseVariable(rawData, variableName));
        }
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
            throw new RuntimeException(e.getCause());
        }
    }

    private DataContent<?> parseVariable(ByteBuffer data, String variableName) {
        VariableType variableType = VariablesGlossary.lookupVariable(variableName);
        if (variableType == null) {
            log.error("Could not find variableName '{}' in the glossary", variableName);
            eventPublisher.publishEvent(new FailedToLoadFileEvent(this));
            throw new RuntimeException("variableName not in glossary");
        }
        Variable variable = new Variable(variableName, variableType);
        return parseDataContent(data, variable);
    }

    private DataContent<?> parseDataContent(ByteBuffer data, Variable variable) {
        switch (variable.getVariableType()) {
            case INT:
                return new IntContent(variable, data.getInt());
            case FLOAT:
                return new FloatContent(variable, data.getFloat());
            case UTF8:
                return new UTF8Content(variable, readUTF8(data));
            case UTF16:
                return new UTF16Content(variable, readUTF16(data));
            case ID:
                byte[] id = new byte[16];
                data.get(id, 0, 16);
                return new IdContent(variable, id);
            case STREAM:
                return new StreamContent(variable, readByteData(data));
            case BLOCK:
                data.getInt(); // Ignore
                return new BlockContent(variable, (variable.getName().equals("begin_block") ? BEGIN_BLOCK : END_BLOCK));
            default:
                log.error("Invalid variable type '{}'", variable.getVariableType());
                throw new RuntimeException("Invalid variable type");
        }
    }

    private String readUTF8(ByteBuffer data) {
        return new String(readBytes(data, false));
    }

    private String readUTF16(ByteBuffer data) {
        return new String(readBytes(data, true));
    }

    private byte[] readBytes(ByteBuffer data, boolean doubleLen) {
        int dataSize = data.getInt();
        if (dataSize > data.remaining()) {
            log.error("dataSize '{}' is more than what's left in the buffer '{}'", dataSize, data.remaining());
            eventPublisher.publishEvent(new FailedToLoadFileEvent(this));
            throw new RuntimeException("Insufficient data left in file we're trying to load");
        }
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
