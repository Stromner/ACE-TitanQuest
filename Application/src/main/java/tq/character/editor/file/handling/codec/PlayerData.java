package tq.character.editor.file.handling.codec;

import tq.character.editor.file.handling.codec.variable.VariableBlock;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    private final List<VariableBlock> variableBlockList;

    public PlayerData() {
        variableBlockList = new ArrayList<>();
    }

    public void addBlock(VariableBlock variableBlock) {
        variableBlockList.add(variableBlock);
    }
}
