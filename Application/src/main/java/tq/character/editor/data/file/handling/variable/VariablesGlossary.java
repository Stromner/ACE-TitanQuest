package tq.character.editor.data.file.handling.variable;

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
        glossaryMap.put("controllerStreamed", VariableType.INTEGER);
        // Block list 1 'Player metadata'
        glossaryMap.put("myPlayerName", VariableType.UTF16);
        glossaryMap.put("isInMainQuest", VariableType.INTEGER);
        glossaryMap.put("disableAutoPopV2", VariableType.INTEGER);
        glossaryMap.put("numTutorialPagesV2", VariableType.INTEGER);
        glossaryMap.put("currentPageV2", VariableType.INTEGER);
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
        // Block list 2 'Skills meta'
        glossaryMap.put("max", VariableType.INTEGER);
        glossaryMap.put("masteriesAllowed", VariableType.INTEGER);
        glossaryMap.put("skillReclamationPointsUsed", VariableType.INTEGER);
        // Block list 2.1 'Skills'
        glossaryMap.put("skillName", VariableType.UTF8);
        glossaryMap.put("skillLevel", VariableType.INTEGER);
        glossaryMap.put("skillEnabled", VariableType.INTEGER);
        glossaryMap.put("skillSubLevel", VariableType.INTEGER);
        glossaryMap.put("skillActive", VariableType.INTEGER);
        glossaryMap.put("skillTransition", VariableType.INTEGER);
        // Block list 3 'Active skills'
        glossaryMap.put("equipmentSelection", VariableType.INTEGER);
        glossaryMap.put("skillWindowSelection", VariableType.INTEGER);
        glossaryMap.put("skillSettingValid", VariableType.INTEGER);
        glossaryMap.put("primarySkill1", VariableType.INTEGER);
        glossaryMap.put("secondarySkill1", VariableType.INTEGER);
        glossaryMap.put("skillActive1", VariableType.INTEGER);
        glossaryMap.put("primarySkill2", VariableType.INTEGER);
        glossaryMap.put("secondarySkill2", VariableType.INTEGER);
        glossaryMap.put("skillActive2", VariableType.INTEGER);
        glossaryMap.put("primarySkill3", VariableType.INTEGER);
        glossaryMap.put("secondarySkill3", VariableType.INTEGER);
        glossaryMap.put("skillActive3", VariableType.INTEGER);
        glossaryMap.put("primarySkill4", VariableType.INTEGER);
        glossaryMap.put("secondarySkill4", VariableType.INTEGER);
        glossaryMap.put("skillActive4", VariableType.INTEGER);
        glossaryMap.put("primarySkill5", VariableType.INTEGER);
        glossaryMap.put("secondarySkill5", VariableType.INTEGER);
        glossaryMap.put("skillActive5", VariableType.INTEGER);
        // Block list 4
        glossaryMap.put("currentStats.charLevel", VariableType.INTEGER);
        glossaryMap.put("currentStats.experiencePoints", VariableType.INTEGER);
        glossaryMap.put("modifierPoints", VariableType.INTEGER);
        glossaryMap.put("skillPoints", VariableType.INTEGER);
        // Block list 5
        // glossaryMap.put("temp", VariableType.INTEGER);
        // Block list 6
        glossaryMap.put("playTimeInSeconds", VariableType.INTEGER);
        glossaryMap.put("numberOfDeaths", VariableType.INTEGER);
        glossaryMap.put("numberOfKills", VariableType.INTEGER);
        glossaryMap.put("experienceFromKills", VariableType.INTEGER);
        glossaryMap.put("healthPotionsUsed", VariableType.INTEGER);
        glossaryMap.put("manaPotionsUsed", VariableType.INTEGER);
        glossaryMap.put("maxLevel", VariableType.INTEGER);
        glossaryMap.put("numHitsReceived", VariableType.INTEGER);
        glossaryMap.put("numHitsInflicted", VariableType.INTEGER);
        glossaryMap.put("greatestDamageInflicted", VariableType.INTEGER);
        glossaryMap.put("(*greatestMonsterKilledName)[i]", VariableType.UTF16);
        glossaryMap.put("(*greatestMonsterKilledLevel)[i]", VariableType.INTEGER);
        glossaryMap.put("(*greatestMonsterKilledLifeAndMana)[i]", VariableType.INTEGER);
        glossaryMap.put("criticalHitsInflicted", VariableType.INTEGER);
        glossaryMap.put("criticalHitsReceived", VariableType.INTEGER);
        // Block list 7
        glossaryMap.put("itemPositionsSavedAsGridCoords", VariableType.INTEGER);
        glossaryMap.put("numberOfSacks", VariableType.INTEGER);
        glossaryMap.put("currentlyFocusedSackNumber", VariableType.INTEGER);
        glossaryMap.put("currentlySelectedSackNumber", VariableType.INTEGER);
        glossaryMap.put("tempBool", VariableType.INTEGER);
        glossaryMap.put("size", VariableType.INTEGER);
        // Block list 8
        glossaryMap.put("useAlternate", VariableType.INTEGER);
        glossaryMap.put("equipmentCtrlIOStreamVersion", VariableType.INTEGER);
        glossaryMap.put("baseName", VariableType.UTF8);
        glossaryMap.put("prefixName", VariableType.UTF8);
        glossaryMap.put("suffixName", VariableType.UTF8);
        glossaryMap.put("relicName", VariableType.UTF8);
        glossaryMap.put("relicBonus", VariableType.UTF8);
        glossaryMap.put("seed", VariableType.INTEGER);
        glossaryMap.put("var1", VariableType.INTEGER);
        glossaryMap.put("relicName2", VariableType.UTF8);
        glossaryMap.put("relicBonus2", VariableType.UTF8);
        glossaryMap.put("var2", VariableType.INTEGER);
        glossaryMap.put("itemAttached", VariableType.INTEGER);
        glossaryMap.put("alternate", VariableType.INTEGER);
        // Block list 9
        glossaryMap.put("storedType", VariableType.INTEGER);
        glossaryMap.put("isItemSkill", VariableType.INTEGER);
        glossaryMap.put("itemName", VariableType.UTF8);
        glossaryMap.put("description", VariableType.STREAM);
    }
}
