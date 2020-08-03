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
        // Standalone variables
        glossaryMap.put("headerVersion", VariableType.INTEGER);
        glossaryMap.put("playerCharacterClass", VariableType.UTF8);
        glossaryMap.put("uniqueId", VariableType.ID);
        glossaryMap.put("streamData", VariableType.STREAM);
        glossaryMap.put("playerClassTag", VariableType.UTF8);
        glossaryMap.put("playerLevel", VariableType.INTEGER);
        glossaryMap.put("playerVersion", VariableType.INTEGER);
        // Block list 1
        glossaryMap.put("myPlayerName", VariableType.UTF16);
        glossaryMap.put("isInMainQuest", VariableType.INTEGER);
        glossaryMap.put("disableAutoPopV2", VariableType.INTEGER);
        glossaryMap.put("numTutorialPagesV2", VariableType.INTEGER);
        glossaryMap.put("currentPageV2", VariableType.INTEGER);
        glossaryMap.put("versionCheckTeleportInf", VariableType.INTEGER);
        glossaryMap.put("teleportUIDsSize", VariableType.INTEGER);
        glossaryMap.put("versionCheckMovementInfo", VariableType.INTEGER);
        glossaryMap.put("markerUIDsSize", VariableType.INTEGER);
        glossaryMap.put("versionCheckRespawnInfo", VariableType.INTEGER);
        glossaryMap.put("respawnUIDsSize", VariableType.INTEGER);
        glossaryMap.put("versionRespawnPoint", VariableType.INTEGER);
        glossaryMap.put("versionCheckTeleportInfo", VariableType.INTEGER);
        glossaryMap.put("strategicMovementRespawnPoint[i]", VariableType.ID);
        glossaryMap.put("money", VariableType.INTEGER);
        glossaryMap.put("compassState", VariableType.INTEGER);
        glossaryMap.put("skillWindowShowHelp", VariableType.INTEGER);
        glossaryMap.put("alternateConfig", VariableType.INTEGER);
        glossaryMap.put("alternateConfigEnabled", VariableType.INTEGER);
        glossaryMap.put("playerTexture", VariableType.UTF8);
        glossaryMap.put("itemsFoundOverLifetimeUniqueTotal", VariableType.INTEGER);
        glossaryMap.put("itemsFoundOverLifetimeRandomizedTotal", VariableType.INTEGER);
        glossaryMap.put("temp", VariableType.INTEGER);
        glossaryMap.put("hasBeenInGame", VariableType.INTEGER);
        // Block list 2
        glossaryMap.put("max", VariableType.INTEGER);
    }
}
