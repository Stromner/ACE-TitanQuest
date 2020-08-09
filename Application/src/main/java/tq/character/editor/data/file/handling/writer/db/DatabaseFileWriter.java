package tq.character.editor.data.file.handling.writer.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import tq.character.editor.data.file.handling.writer.IFileWriter;
import tq.character.editor.database.IDataContentRepository;
import tq.character.editor.database.entities.VariableType;
import tq.character.editor.database.entities.content.DataContent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@Component
public class DatabaseFileWriter implements IFileWriter {
    private static final Logger log = LoggerFactory.getLogger(DatabaseFileWriter.class);
    private static final String SAVE_FILE_NAME = "Player.chr";
    @Autowired
    private IDataContentRepository contentRepository;

    @Override
    public void saveFile(String filePath) {
        File file = new File(filePath);
        file.mkdirs();
        file = new File(filePath + '/' + SAVE_FILE_NAME);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            for (DataContent content : contentRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))) {
                saveVariableName(fos, content);
                saveDataContent(fos, content);
            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private void saveVariableName(FileOutputStream fos, DataContent content) throws IOException {
        String varName = content.getVariable().getName();
        if (content.getVariable().getVariableType() != VariableType.BLOCK) {
            fos.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(varName.length()).array());
            fos.write(varName.getBytes());
        }
    }

    private void saveDataContent(FileOutputStream fos, DataContent content) throws IOException {
        switch (content.getVariable().getVariableType()) {
            case INT:
                Integer i = (Integer) content.getDataContent();
                fos.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i).array());
                break;
            case UTF8:
                saveString(fos, (String) content.getDataContent(), false);
                break;
            case UTF16:
                saveString(fos, (String) content.getDataContent(), true);
                break;
            case BLOCK:
            case ID:
                fos.write((byte[]) content.getDataContent());
                break;
            case STREAM:
                byte[] b = (byte[]) content.getDataContent();
                fos.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(b.length).array());
                fos.write(b);
                break;
            default:
                log.error("Invalid variable type '{}'", content.getVariable().getVariableType());
                throw new RuntimeException("Invalid variable type");
        }
    }

    private void saveString(FileOutputStream fos, String s, boolean isUTF16) throws IOException {
        int size = isUTF16 ? s.length() / 2 : s.length();

        fos.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(size).array());
        fos.write(new String(s.getBytes()).getBytes());
    }
}
