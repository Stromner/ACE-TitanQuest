package tq.character.editor.database;

import org.springframework.data.jpa.repository.JpaRepository;
import tq.character.editor.database.entities.content.DataContent;

import java.util.List;

public interface IDataContentRepository extends JpaRepository<DataContent<?>, Long> {
    <T extends DataContent<?>> T findByVariableName(String variableName);

    <T extends DataContent<?>> List<T> findAllByVariableName(String variableName);
}
