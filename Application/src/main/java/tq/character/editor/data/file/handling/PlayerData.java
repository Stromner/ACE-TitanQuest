package tq.character.editor.data.file.handling;

import tq.character.editor.data.file.handling.variable.VariableInfo;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    private final List<VariableInfo> variableInfoList;

    public PlayerData() {
        variableInfoList = new ArrayList<>();
    }

    public void addBlock(VariableInfo variableInfo) {
        variableInfoList.add(variableInfo);
    }
}
