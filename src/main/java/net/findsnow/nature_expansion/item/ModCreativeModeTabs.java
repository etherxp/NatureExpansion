package net.findsnow.nature_expansion.item;

import net.findsnow.nature_expansion.NatureExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NatureExpansion.MOD_ID);

	public static final Supplier<CreativeModeTab> NATURE_EXPANSION_TAB =
			CREATIVE_MODE_TABS.register("nature_expansion_tab", () -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.nature_expansion.nature_expansion_tab"))
					.icon(() -> new ItemStack(ModItems.TEST_ITEM.get()))
					.displayItems((itemDisplayParameters, output) -> {
						output.accept(ModItems.TEST_ITEM);
						output.accept(ModItems.BEAR_SPAWN_EGG);
					})
					.build());


	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TABS.register(eventBus);
	}
}
