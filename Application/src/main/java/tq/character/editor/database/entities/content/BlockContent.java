package tq.character.editor.database.entities.content;

import tq.character.editor.database.entities.Variable;

import javax.persistence.Entity;

@Entity
public class BlockContent extends DataContent {
    protected BlockContent() {

    }

    public BlockContent(Variable variable) {
        super(variable);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}