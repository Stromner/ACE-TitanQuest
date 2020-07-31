package tq.character.editor.file.handling.codec;

import tq.character.editor.file.handling.codec.variable.VariableBlock;

import java.util.ArrayList;
import java.util.List;

public class TQPlayerData {
    private final List<VariableBlock> variableBlockList;

    public TQPlayerData() {
        variableBlockList = new ArrayList<>();
    }

    public void addBlock(VariableBlock variableBlock) {
        variableBlockList.add(variableBlock);
    }
}
