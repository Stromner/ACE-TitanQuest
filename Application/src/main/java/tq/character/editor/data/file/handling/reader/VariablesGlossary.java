package tq.character.editor.data.file.handling.reader;

import org.springframework.stereotype.Component;
import tq.character.editor.database.entities.VariableType;

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
        // Blocks
        glossaryMap.put("begin_block", VariableType.BLOCK);
        glossaryMap.put("end_block", VariableType.BLOCK);
        // Standalone variables
        glossaryMap.put("headerVersion", VariableType.INT);
        glossaryMap.put("playerCharacterClass", VariableType.UTF8);
        glossaryMap.put("uniqueId", VariableType.ID);
        glossaryMap.put("streamData", VariableType.STREAM);
        glossaryMap.put("playerClassTag", VariableType.UTF8);
        glossaryMap.put("playerLevel", VariableType.INT);
        glossaryMap.put("playerVersion", VariableType.INT);
        glossaryMap.put("controllerStreamed", VariableType.INT);
        // Block list 1 'Player metadata'
        glossaryMap.put("myPlayerName", VariableType.UTF16);
        glossaryMap.put("isInMainQuest", VariableType.INT);
        glossaryMap.put("disableAutoPopV2", VariableType.INT);
        glossaryMap.put("numTutorialPagesV2", VariableType.INT);
        glossaryMap.put("currentPageV2", VariableType.INT);
        glossaryMap.put("teleportUIDsSize", VariableType.INT);
        glossaryMap.put("teleportUID", VariableType.ID);
        glossaryMap.put("versionCheckMovementInfo", VariableType.INT);
        glossaryMap.put("markerUIDsSize", VariableType.INT);
        glossaryMap.put("markerUID", VariableType.ID);
        glossaryMap.put("versionCheckRespawnInfo", VariableType.INT);
        glossaryMap.put("respawnUIDsSize", VariableType.INT);
        glossaryMap.put("respawnUID", VariableType.ID);
        glossaryMap.put("versionRespawnPoint", VariableType.INT);
        glossaryMap.put("versionCheckTeleportInfo", VariableType.INT);
        glossaryMap.put("strategicMovementRespawnPoint[i]", VariableType.ID);
        glossaryMap.put("money", VariableType.INT);
        glossaryMap.put("compassState", VariableType.INT);
        glossaryMap.put("skillWindowShowHelp", VariableType.INT);
        glossaryMap.put("alternateConfig", VariableType.INT);
        glossaryMap.put("alternateConfigEnabled", VariableType.INT);
        glossaryMap.put("playerTexture", VariableType.UTF8);
        glossaryMap.put("itemsFoundOverLifetimeUniqueTotal", VariableType.INT);
        glossaryMap.put("itemsFoundOverLifetimeRandomizedTotal", VariableType.INT);
        glossaryMap.put("temp", VariableType.INT);
        glossaryMap.put("hasBeenInGame", VariableType.INT);
        // Block list 2 'Skills meta'
        glossaryMap.put("max", VariableType.INT);
        glossaryMap.put("masteriesAllowed", VariableType.INT);
        glossaryMap.put("skillReclamationPointsUsed", VariableType.INT);
        // Block list 2.1 'Skills'
        glossaryMap.put("skillName", VariableType.UTF8);
        glossaryMap.put("skillLevel", VariableType.INT);
        glossaryMap.put("skillEnabled", VariableType.INT);
        glossaryMap.put("skillSubLevel", VariableType.INT);
        glossaryMap.put("skillActive", VariableType.INT);
        glossaryMap.put("skillTransition", VariableType.INT);
        // Block list 3 'Active skills'
        glossaryMap.put("equipmentSelection", VariableType.INT);
        glossaryMap.put("skillWindowSelection", VariableType.INT);
        glossaryMap.put("skillSettingValid", VariableType.INT);
        glossaryMap.put("primarySkill1", VariableType.INT);
        glossaryMap.put("secondarySkill1", VariableType.INT);
        glossaryMap.put("skillActive1", VariableType.INT);
        glossaryMap.put("primarySkill2", VariableType.INT);
        glossaryMap.put("secondarySkill2", VariableType.INT);
        glossaryMap.put("skillActive2", VariableType.INT);
        glossaryMap.put("primarySkill3", VariableType.INT);
        glossaryMap.put("secondarySkill3", VariableType.INT);
        glossaryMap.put("skillActive3", VariableType.INT);
        glossaryMap.put("primarySkill4", VariableType.INT);
        glossaryMap.put("secondarySkill4", VariableType.INT);
        glossaryMap.put("skillActive4", VariableType.INT);
        glossaryMap.put("primarySkill5", VariableType.INT);
        glossaryMap.put("secondarySkill5", VariableType.INT);
        glossaryMap.put("skillActive5", VariableType.INT);
        // Block list 4
        glossaryMap.put("currentStats.charLevel", VariableType.INT);
        glossaryMap.put("currentStats.experiencePoints", VariableType.INT);
        glossaryMap.put("modifierPoints", VariableType.INT);
        glossaryMap.put("skillPoints", VariableType.INT);
        // Block list 5
        // glossaryMap.put("temp", VariableType.INTEGER);
        // Block list 6
        glossaryMap.put("playTimeInSeconds", VariableType.INT);
        glossaryMap.put("numberOfDeaths", VariableType.INT);
        glossaryMap.put("numberOfKills", VariableType.INT);
        glossaryMap.put("experienceFromKills", VariableType.INT);
        glossaryMap.put("healthPotionsUsed", VariableType.INT);
        glossaryMap.put("manaPotionsUsed", VariableType.INT);
        glossaryMap.put("maxLevel", VariableType.INT);
        glossaryMap.put("numHitsReceived", VariableType.INT);
        glossaryMap.put("numHitsInflicted", VariableType.INT);
        glossaryMap.put("greatestDamageInflicted", VariableType.INT);
        glossaryMap.put("(*greatestMonsterKilledName)[i]", VariableType.UTF16);
        glossaryMap.put("(*greatestMonsterKilledLevel)[i]", VariableType.INT);
        glossaryMap.put("(*greatestMonsterKilledLifeAndMana)[i]", VariableType.INT);
        glossaryMap.put("criticalHitsInflicted", VariableType.INT);
        glossaryMap.put("criticalHitsReceived", VariableType.INT);
        // Block list 7
        glossaryMap.put("itemPositionsSavedAsGridCoords", VariableType.INT);
        glossaryMap.put("numberOfSacks", VariableType.INT);
        glossaryMap.put("currentlyFocusedSackNumber", VariableType.INT);
        glossaryMap.put("currentlySelectedSackNumber", VariableType.INT);
        glossaryMap.put("tempBool", VariableType.INT);
        glossaryMap.put("size", VariableType.INT);
        // Block list 8
        glossaryMap.put("useAlternate", VariableType.INT);
        glossaryMap.put("equipmentCtrlIOStreamVersion", VariableType.INT);
        glossaryMap.put("baseName", VariableType.UTF8);
        glossaryMap.put("prefixName", VariableType.UTF8);
        glossaryMap.put("suffixName", VariableType.UTF8);
        glossaryMap.put("relicName", VariableType.UTF8);
        glossaryMap.put("relicBonus", VariableType.UTF8);
        glossaryMap.put("seed", VariableType.INT);
        glossaryMap.put("var1", VariableType.INT);
        glossaryMap.put("relicName2", VariableType.UTF8);
        glossaryMap.put("relicBonus2", VariableType.UTF8);
        glossaryMap.put("var2", VariableType.INT);
        glossaryMap.put("itemAttached", VariableType.INT);
        glossaryMap.put("alternate", VariableType.INT);
        // Block list 9
        glossaryMap.put("storedType", VariableType.INT);
        glossaryMap.put("isItemSkill", VariableType.INT);
        glossaryMap.put("itemName", VariableType.UTF8);
        glossaryMap.put("description", VariableType.STREAM);
        glossaryMap.put("pointX", VariableType.INT);
        glossaryMap.put("pointY", VariableType.INT);
        // Block list 10
        glossaryMap.put("scrollName", VariableType.UTF8);
        glossaryMap.put("bitmapUpName", VariableType.UTF8);
        glossaryMap.put("bitmapDownName", VariableType.UTF8);
        glossaryMap.put("defaultText", VariableType.UTF8);
        // Older versions
        glossaryMap.put("storedDefaultType", VariableType.INT);
    }
}
