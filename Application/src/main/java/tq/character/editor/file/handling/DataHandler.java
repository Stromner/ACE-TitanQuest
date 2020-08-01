package tq.character.editor.file.handling;

/**
 * Interface for interacting with the data layer
 */
public interface DataHandler<E> {
    void processFile(String filePath);

    E getData();
}
