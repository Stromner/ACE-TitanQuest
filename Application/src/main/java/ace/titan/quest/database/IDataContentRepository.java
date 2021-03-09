package ace.titan.quest.database;

import org.springframework.data.jpa.repository.JpaRepository;
import ace.titan.quest.database.entities.content.DataContent;

import java.util.List;

public interface IDataContentRepository extends JpaRepository<DataContent<?>, Long> {
    <T extends DataContent<?>> T findByVariableName(String variableName);

    <T extends DataContent<?>> List<T> findAllByVariableName(String variableName);
}
