package tq.character.editor.database.entities.content;

import tq.character.editor.database.entities.Variable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class IntContent extends DataContent {
    @Column(nullable = false)
    private int dataContent;

    protected IntContent() {

    }

    public IntContent(Variable variable, int dataContent) {
        super(variable);
        this.dataContent = dataContent;
    }

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
