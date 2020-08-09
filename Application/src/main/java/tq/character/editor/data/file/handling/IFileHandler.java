package tq.character.editor.data.file.handling;

import tq.character.editor.data.file.handling.reader.IFileReader;
import tq.character.editor.data.file.handling.writer.IFileWriter;

/**
 * Interface for file management
 *
 * @param <V> Format for the raw data
 */
public interface IFileHandler<V> extends IFileReader<V>, IFileWriter {
}
