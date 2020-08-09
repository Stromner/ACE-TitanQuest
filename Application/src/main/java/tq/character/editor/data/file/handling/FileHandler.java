package tq.character.editor.data.file.handling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tq.character.editor.data.file.handling.reader.IFileLoader;
import tq.character.editor.data.file.handling.writer.IFileWriter;

@Component
public class FileHandler<V> implements IFileHandler<V> {
    @Autowired
    IFileLoader<V> fileLoader;
    @Autowired
    IFileWriter fileWriter;

    @Override
    public V loadFile(String filePath) {
        return fileLoader.loadFile(filePath);
    }

    @Override
    public V getRawData() {
        return fileLoader.getRawData();
    }

    @Override
    public void saveFile(String filePath) {
        fileWriter.saveFile(filePath);
    }
}
