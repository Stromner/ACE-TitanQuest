package tq.character.editor.file.handling.codec.variable;

public class VariableBlock<T> {
    private String variableName;
    private VariableType variableType;
    private T variableContent;

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public void setVariableType(VariableType variableType) {
        this.variableType = variableType;
    }

    public T getVariableContent() {
        return variableContent;
    }

    public void setVariableContent(T variableContent) {
        this.variableContent = variableContent;
    }
}
