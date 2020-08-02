package tq.character.editor.file.handling.codec.variable;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class VariablesGlossary {
    private static Map<String, VariableType> glossaryMap;

    public static VariableType lookupVariable(String variableName) {
        return glossaryMap.get(variableName);
    }

    @PostConstruct
    private void init() {
        glossaryMap = new HashMap<>();
        fillGlossary();
    }

    private void fillGlossary() {
        // Before any block list
        glossaryMap.put("headerVersion", VariableType.INTEGER);
        glossaryMap.put("playerCharacterClass", VariableType.UTF8);
        glossaryMap.put("uniqueId", VariableType.ID);
        glossaryMap.put("streamData", VariableType.STREAM);
        glossaryMap.put("playerClassTag", VariableType.UTF8);
        glossaryMap.put("playerLevel", VariableType.INTEGER);
        glossaryMap.put("playerVersion", VariableType.INTEGER);
        // Block list 1


    }
}
