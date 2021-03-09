package ace.titan.quest.database.entities.content;

import ace.titan.quest.database.entities.Variable;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Arrays;

@Entity
public class IdContent extends DataContent<byte[]> {
    @Column(name = "idDataContent", nullable = false, length = 16)
    private byte[] dataContent;

    protected IdContent() {

    }

    public IdContent(Variable variable, byte[] dataContent) {
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
                "IdContent{" +
                "dataContent=" + Arrays.toString(dataContent) +
                '}';
    }
}
