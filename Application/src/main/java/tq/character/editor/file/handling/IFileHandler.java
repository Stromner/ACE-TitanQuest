package tq.character.editor.file.handling;

/**
 * Interface for file management
 *
 * @param <E> Structured representation of the data
 */
public interface IFileHandler<E> {
    void readFile(String filePath);

    void saveFile(E content, String filePath);

    E getData();
}
