package tq.character.editor.database.entities.content;

import tq.character.editor.database.entities.Variable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "UTF16_CONTENT")
public class UTF16Content extends DataContent<String> {
    @Column(name = "utf16DataContent", nullable = false, length = 255)
    private String dataContent;

    protected UTF16Content() {

    }

    public UTF16Content(Variable variable, String dataContent) {
        super(variable);
        this.dataContent = dataContent;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    @Override
    public String toString() {
        return super.toString() +
                "UTF16Content{" +
                "dataContent='" + dataContent + '\'' +
                '}';
    }
}
