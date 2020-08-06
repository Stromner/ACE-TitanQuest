package tq.character.editor.database.entities;

import javax.persistence.*;

@Entity
public class Variable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VariableType variableType;
    @Column(nullable = false, length = 255)
    private String variableName;

    public Long getId() {
        return id;
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public void setVariableType(VariableType variableType) {
        this.variableType = variableType;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "id=" + id +
                ", variableType=" + variableType +
                ", variableName='" + variableName + '\'' +
                '}';
    }
}
