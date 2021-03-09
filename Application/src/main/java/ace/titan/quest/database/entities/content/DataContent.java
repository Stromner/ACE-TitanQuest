package ace.titan.quest.database.entities.content;

import ace.titan.quest.database.entities.Variable;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class DataContent<E> { // Abstract so we don't get a DB table for this class
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(nullable = false)
    private Variable variable;

    protected DataContent() {

    }

    public DataContent(Variable variable) {
        this.variable = variable;
    }

    public Long getId() {
        return id;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public abstract E getDataContent();

    public abstract void setDataContent(E dataContent);

    @Override
    public String toString() {
        return "DataContent{" +
                "id=" + id +
                ", variable=" + variable +
                '}';
    }
}
