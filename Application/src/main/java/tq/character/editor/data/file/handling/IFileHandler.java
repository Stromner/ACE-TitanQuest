package tq.character.editor.data.file.handling;

import tq.character.editor.data.file.handling.reader.IFileLoader;
import tq.character.editor.data.file.handling.writer.IFileWriter;

/**
 * Interface for file management
 *
 * @param <V> Raw representation of the data
 */
public interface IFileHandler<V> extends IFileLoader<V>, IFileWriter {
}
