package tq.character.editor.database.entities.content;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Arrays;

@Entity
public class IdContent extends DataContent {
    @Column(nullable = false, length = 16)
    private byte[] dataContent;

    public byte[] getDataContent() {
        return dataContent;
    }

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
