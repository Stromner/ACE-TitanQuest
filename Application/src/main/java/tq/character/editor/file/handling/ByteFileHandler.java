package tq.character.editor.file.handling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tq.character.editor.file.handling.codec.CodecInterpreter;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@Component
public class ByteFileHandler implements FileHandler {
    private static final Logger log = LoggerFactory.getLogger(ByteFileHandler.class);
    @Autowired
    private CodecInterpreter codecInterpreter;
    private ByteBuffer byteBuffer;

    @Override
    public Object getFileContent(String filePath) {
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

        return codecInterpreter.decode(byteBuffer);
    }

    @Override
    public void saveFile(Object content, String filePath) {
        // TODO
    }

    public ByteBuffer getByteBuffer() {
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
