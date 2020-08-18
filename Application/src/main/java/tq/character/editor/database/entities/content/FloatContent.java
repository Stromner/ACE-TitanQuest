package tq.character.editor.database.entities.content;

import tq.character.editor.database.entities.Variable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class FloatContent extends DataContent<Float> {
    @Column(name = "floatDataContent", nullable = false, length = 16)
    private Float dataContent;

    protected FloatContent() {

    }

    public FloatContent(Variable variable, Float dataContent) {
        super(variable);
        this.dataContent = dataContent;
    }

    @Override
    public Float getDataContent() {
        return dataContent;
    }

    @Override
    public void setDataContent(Float dataContent) {
        this.dataContent = dataContent;
    }

    @Override
    public String toString() {
        return super.toString() +
                "FloatContent{" +
                "dataContent=" + dataContent +
                '}';
    }
}