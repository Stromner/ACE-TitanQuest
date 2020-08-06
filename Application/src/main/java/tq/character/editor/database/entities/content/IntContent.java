package tq.character.editor.database.entities.content;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class IntContent extends DataContent {
    @Column(nullable = false)
    private int dataContent;

    public int getDataContent() {
        return dataContent;
    }

    public void setDataContent(int dataContent) {
        this.dataContent = dataContent;
    }

    @Override
    public String toString() {
        return super.toString() +
                "IntContent{" +
                "dataContent=" + dataContent +
                '}';
    }
}
