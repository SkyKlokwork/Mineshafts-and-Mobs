package net.mineshafts.mnm.spells.spelltree;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import net.minecraft.loot.LootGsons;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionManager;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

public class SpellEntityPredicateDeserializer {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Identifier advancementId;
    private final LootConditionManager conditionManager;
    private final Gson gson = LootGsons.getConditionGsonBuilder().create();

    public SpellEntityPredicateDeserializer(Identifier advancementId, LootConditionManager conditionManager) {
        this.advancementId = advancementId;
        this.conditionManager = conditionManager;
    }

    public final LootCondition[] loadConditions(JsonArray array, String key, LootContextType contextType) {
        LootCondition[] lootConditions = this.gson.fromJson((JsonElement)array, LootCondition[].class);
        LootTableReporter lootTableReporter = new LootTableReporter(contextType, this.conditionManager::get, tableId -> null);
        for (LootCondition lootCondition : lootConditions) {
            lootCondition.validate(lootTableReporter);
            lootTableReporter.getMessages().forEach((name, message) -> LOGGER.warn("Found validation problem in advancement trigger {}/{}: {}", key, name, message));
        }
        return lootConditions;
    }

    public Identifier getAdvancementId() {
        return this.advancementId;
    }
}
