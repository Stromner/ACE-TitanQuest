package tq.character.editor.data.file.handling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tq.character.editor.data.file.handling.reader.IFileReader;
import tq.character.editor.data.file.handling.writer.IFileWriter;

@Component
public class FileHandler<V> implements IFileHandler {
    @Autowired
    IFileReader<V> fileReader;
    @Autowired
    IFileWriter fileWriter;

    @Override
    public V loadFile(String filePath) {
        return fileReader.loadFile(filePath);
    }

    @Override
    public V getRawData() {
        return fileReader.getRawData();
    }

    @Override
    public void saveFile(String filePath) {
        fileWriter.saveFile(filePath);
    }
}
