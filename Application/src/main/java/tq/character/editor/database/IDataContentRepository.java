package tq.character.editor.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tq.character.editor.database.entities.content.DataContent;

public interface IDataContentRepository extends JpaRepository<DataContent, Long> {
    @Query(value = "SELECT * FROM VARIABLE WHERE VARIABLE_NAME=?1", nativeQuery = true)
    DataContent findByVariableName(String variableName);
}
