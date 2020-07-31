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
        glossaryMap.put("headerVersion", VariableType.INTEGER);
    }
}
