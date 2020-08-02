package tq.character.editor.file.handling;

/**
 * Interface for interacting with the data layer
 */
public interface IDataHandler<E> {
    void processFile(String filePath);

    E getData();
}
