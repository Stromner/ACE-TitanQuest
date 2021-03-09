package ace.titan.quest.data.file.handling.reader;

/**
 * Interface for loading data into the application
 *
 * @param <V> Raw representation of the data
 */
public interface IFileReader<V> {
    V loadFile(String filePath);

    V getRawData();
}
