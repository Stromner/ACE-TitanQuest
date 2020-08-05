package tq.character.editor.data.file.handling;

/**
 * Interface for translating raw data to structured data
 *
 * @param <E> Structured representation of the data
 * @param <V> Raw representation of the data
 */
public interface ICodec<E, V> {
    V encode(E structuredData);

    E decode(V rawData);

    E getData();
}
