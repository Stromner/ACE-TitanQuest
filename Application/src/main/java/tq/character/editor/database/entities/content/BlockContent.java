package tq.character.editor.database.entities.content;

import tq.character.editor.database.entities.Variable;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Arrays;

@Entity
public class BlockContent extends DataContent<byte[]> {
    @Column(name = "blockDataContent", nullable = false, length = 19)
    private byte[] dataContent;

    protected BlockContent() {

    }

    public BlockContent(Variable variable, byte[] dataContent) {
        super(variable);
        this.dataContent = dataContent;
    }

    @Override
    public byte[] getDataContent() {
        return dataContent;
    }

    @Override
    public void setDataContent(byte[] dataContent) {
        this.dataContent = dataContent;
    }

    @Override
    public String toString() {
        return super.toString() +
                "BlockContent{" +
                "dataContent=" + Arrays.toString(dataContent) +
                '}';
    }
}