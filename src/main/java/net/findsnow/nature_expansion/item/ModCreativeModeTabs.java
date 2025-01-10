package net.findsnow.nature_expansion.item;

import net.findsnow.nature_expansion.NatureExpansion;
import net.findsnow.nature_expansion.block.ModBlocks;
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
						output.accept(ModBlocks.MAPLE_LOG.get());
						output.accept(ModBlocks.MAPLE_WOOD.get());
						output.accept(ModBlocks.STRIPPED_MAPLE_LOG.get());
						output.accept(ModBlocks.STRIPPED_MAPLE_WOOD.get());
						output.accept(ModBlocks.MAPLE_PLANKS.get());
						output.accept(ModBlocks.RED_MAPLE_SAPLING.get());
						output.accept(ModBlocks.GREEN_MAPLE_SAPLING.get());
						output.accept(ModBlocks.ORANGE_MAPLE_SAPLING.get());
						output.accept(ModBlocks.RED_MAPLE_LEAVES.get());
						output.accept(ModBlocks.ORANGE_MAPLE_LEAVES.get());
						output.accept(ModBlocks.GREEN_MAPLE_LEAVES.get());
						output.accept(ModBlocks.MAPLE_LEAF_LITTER.get());
						output.accept(ModBlocks.TREE_TAP.get());
					})
					.build());


	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TABS.register(eventBus);
	}
}
