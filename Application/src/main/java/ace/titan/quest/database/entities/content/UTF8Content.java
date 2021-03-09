package ace.titan.quest.database.entities.content;

import ace.titan.quest.database.entities.Variable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "UTF8_CONTENT")
public class UTF8Content extends DataContent<String> {
    @Column(name = "utf8DataContent", nullable = false, length = 255)
    private String dataContent;

    protected UTF8Content() {

    }

    public UTF8Content(Variable variable, String dataContent) {
        super(variable);
        this.dataContent = dataContent;
    }

    @Override
    public String getDataContent() {
        return dataContent;
    }

    @Override
    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    @Override
    public String toString() {
        return super.toString() +
                "UTF8Content{" +
                "dataContent='" + dataContent + '\'' +
                '}';
    }
}
