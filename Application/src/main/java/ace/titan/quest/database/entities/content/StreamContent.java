package ace.titan.quest.database.entities.content;

import ace.titan.quest.database.entities.Variable;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Arrays;

@Entity
public class StreamContent extends DataContent<byte[]> {
    @Column(name = "streamDataContent", nullable = false, length = 255)
    private byte[] dataContent;

    protected StreamContent() {

    }

    public StreamContent(Variable variable, byte[] dataContent) {
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
                "StreamContent{" +
                "dataContent=" + Arrays.toString(dataContent) +
                '}';
    }
}
