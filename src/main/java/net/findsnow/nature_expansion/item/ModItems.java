package net.findsnow.nature_expansion.item;

import net.findsnow.nature_expansion.NatureExpansion;
import net.findsnow.nature_expansion.entity.ModEntities;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NatureExpansion.MOD_ID);

	public static final DeferredItem<Item> BEAR_SPAWN_EGG = ITEMS.register("bear_spawn_egg", () -> new DeferredSpawnEggItem(
			ModEntities.BEAR, 0x322422, 0x4e3e35, new Item.Properties()));


	public static final DeferredItem<Item> TEST_ITEM = ITEMS.registerSimpleItem("test_item");

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
