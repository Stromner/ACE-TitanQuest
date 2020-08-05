package tq.character.editor.data.file.handling;

/**
 * Interface for file management
 *
 * @param <V> Raw representation of the data
 */
public interface IFileHandler<V> {
    void readFile(String filePath);

    void saveFile(V content, String filePath);

    V getRawData();
}
