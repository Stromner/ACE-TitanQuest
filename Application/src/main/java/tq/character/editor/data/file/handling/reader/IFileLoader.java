package tq.character.editor.data.file.handling.reader;

/**
 * Interface for loading data into the application
 *
 * @param <V> Raw representation of the data
 */
public interface IFileLoader<V> {
    V loadFile(String filePath);

    V getRawData();
}
