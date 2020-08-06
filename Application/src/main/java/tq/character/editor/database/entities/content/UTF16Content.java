package tq.character.editor.database.entities.content;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "UTF16_CONTENT")
public class UTF16Content extends DataContent {
    @Column(nullable = false, length = 255)
    private String dataContent;

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
