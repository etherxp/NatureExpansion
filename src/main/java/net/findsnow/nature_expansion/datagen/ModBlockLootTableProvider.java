package net.findsnow.nature_expansion.datagen;

import net.findsnow.nature_expansion.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Map;
import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
	protected ModBlockLootTableProvider(HolderLookup.Provider provider) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
	}

	@Override
	protected void generate() {
		this.dropSelf(ModBlocks.MAPLE_LOG.get());
		this.dropSelf(ModBlocks.MAPLE_WOOD.get());
		this.dropSelf(ModBlocks.STRIPPED_MAPLE_LOG.get());
		this.dropSelf(ModBlocks.STRIPPED_MAPLE_WOOD.get());
		this.dropSelf(ModBlocks.MAPLE_PLANKS.get());
		this.dropSelf(ModBlocks.RED_MAPLE_SAPLING.get());
		this.dropSelf(ModBlocks.ORANGE_MAPLE_SAPLING.get());
		this.dropSelf(ModBlocks.GREEN_MAPLE_SAPLING.get());
		this.add(ModBlocks.RED_MAPLE_LEAVES.get(), block ->
				createLeavesDrops(block, ModBlocks.RED_MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		this.add(ModBlocks.ORANGE_MAPLE_LEAVES.get(), block ->
				createLeavesDrops(block, ModBlocks.ORANGE_MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		this.add(ModBlocks.GREEN_MAPLE_LEAVES.get(), block ->
				createLeavesDrops(block, ModBlocks.GREEN_MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		this.dropSelf(ModBlocks.MAPLE_LEAF_LITTER.get());
		this.dropSelf(ModBlocks.TREE_TAP.get());
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
	}
}
