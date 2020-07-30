package tq.character.editor.file.handling.codec;

/**
 * Interface for encoding and decoding files
 *
 * @param <E> Structured representation of the data
 * @param <V> Internal representation of the data
 */

public interface CodecInterpreter<E, V> {
    E decode(V data);

    V encode(E data);
}
