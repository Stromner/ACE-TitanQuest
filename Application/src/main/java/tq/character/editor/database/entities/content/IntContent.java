package tq.character.editor.database.entities.content;

import tq.character.editor.database.entities.Variable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class IntContent extends DataContent<Integer> {
    @Column(name = "intDataContent", nullable = false)
    private Integer dataContent;

    protected IntContent() {

    }

    public IntContent(Variable variable, int dataContent) {
        super(variable);
        this.dataContent = dataContent;
    }

    @Override
    public Integer getDataContent() {
        return dataContent;
    }

    @Override
    public void setDataContent(Integer dataContent) {
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
