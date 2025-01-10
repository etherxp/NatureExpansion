package net.findsnow.nature_expansion.datagen;

import net.findsnow.nature_expansion.NatureExpansion;
import net.findsnow.nature_expansion.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
	public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, NatureExpansion.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(BlockTags.LOGS_THAT_BURN)
				.add(ModBlocks.MAPLE_LOG.get())
				.add(ModBlocks.MAPLE_WOOD.get())
				.add(ModBlocks.STRIPPED_MAPLE_LOG.get())
				.add(ModBlocks.STRIPPED_MAPLE_WOOD.get());
		this.tag(BlockTags.PLANKS)
				.add(ModBlocks.MAPLE_PLANKS.get());
		this.tag(BlockTags.LEAVES)
				.add(ModBlocks.RED_MAPLE_LEAVES.get())
				.add(ModBlocks.ORANGE_MAPLE_LEAVES.get())
				.add(ModBlocks.GREEN_MAPLE_LEAVES.get());
		this.tag(BlockTags.SAPLINGS)
				.add(ModBlocks.RED_MAPLE_SAPLING.get())
				.add(ModBlocks.ORANGE_MAPLE_SAPLING.get())
				.add(ModBlocks.GREEN_MAPLE_SAPLING.get());
		this.tag(BlockTags.LOGS)
				.add(ModBlocks.MAPLE_LOG.get())
				.add(ModBlocks.MAPLE_WOOD.get())
				.add(ModBlocks.STRIPPED_MAPLE_LOG.get())
				.add(ModBlocks.STRIPPED_MAPLE_WOOD.get());
	}
}
