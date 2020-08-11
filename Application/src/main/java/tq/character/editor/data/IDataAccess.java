package tq.character.editor.data;

import tq.character.editor.data.file.handling.IFileHandler;
import tq.character.editor.data.player.IPlayerData;

/**
 * Interfacing class for the data
 *
 * @param <V> Format for the raw data that IFileHandler uses
 */
public interface IDataAccess<V> extends IPlayerData, IFileHandler<V> {
}
