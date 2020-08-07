package tq.character.editor.data.file.handling;

/**
 * Interface for file management
 *
 * @param <V> Raw representation of the data
 */
public interface IFileHandler<V> {
    V loadFile(String filePath);

    void parseFile(V rawData);

    void saveFile(String filePath);

    V getRawData();
}
