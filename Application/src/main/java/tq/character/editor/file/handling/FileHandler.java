package tq.character.editor.file.handling;

/**
 * Interface for reading and saving files
 *
 * @param <E> Structured representation of the data
 */
public interface FileHandler<E> {
    E getFileContent(String filePath);

    void saveFile(E content, String filePath);
}
