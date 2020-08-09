package tq.character.editor.data.file.handling;

import org.springframework.stereotype.Component;
import tq.character.editor.data.file.handling.reader.IFileReader;
import tq.character.editor.data.file.handling.writer.IFileWriter;

import javax.annotation.Resource;

@Component
public class FileHandler<V> implements IFileHandler {
    @Resource(name = "databaseFileReader")
    IFileReader<V> fileReader;
    @Resource(name = "databaseFileWriter")
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
