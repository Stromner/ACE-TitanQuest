package tq.character.editor.file.handling.codec.variable;

import java.util.ArrayList;
import java.util.List;

public class Block extends VariableInfo {
    // TODO See if we can remove the extend function in a nice way
    // The list and the two constants is all we need, the other VariableInfo specific information is going to be invalid
    public static final byte[] BEGIN_BLOCK =
            {0x0B, 0x00, 0x00, 0x00, 0x62, 0x65, 0x67, 0x69, 0x6E, 0x5F, 0x62, 0x6C, 0x6F, 0x63, 0x6B, (byte) 0xCE, (byte) 0xFA, 0x1D, (byte) 0xB0};
    public static final byte[] END_BLOCK =
            {0x65, 0x6E, 0x64, 0x5F, 0x62, 0x6C, 0x6F, 0x63, 0x6B, (byte) 0xDE, (byte) 0xC0, (byte) 0xAD, (byte) 0xDE};
    private final List<VariableInfo> variableInfoList;

    public Block() {
        variableInfoList = new ArrayList<>();
    }

    public void addBlock(VariableInfo variableInfo) {
        variableInfoList.add(variableInfo);
    }
}