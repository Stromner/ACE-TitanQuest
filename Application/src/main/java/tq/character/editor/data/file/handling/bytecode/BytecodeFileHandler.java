package tq.character.editor.data.file.handling.bytecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tq.character.editor.data.file.handling.IFileHandler;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * TODO Add explanation how the file is encoded
 */
@Component
public class BytecodeFileHandler implements IFileHandler<ByteBuffer> {
    private static final Logger log = LoggerFactory.getLogger(BytecodeFileHandler.class);
    private ByteBuffer byteBuffer;

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
    }

    @Override
    public void saveFile(ByteBuffer content, String filePath) {
        // TODO
    }

    @Override
    public ByteBuffer getRawData() {
        return byteBuffer;
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
}
