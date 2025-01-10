package net.findsnow.nature_expansion.datagen;

import net.findsnow.nature_expansion.NatureExpansion;
import net.findsnow.nature_expansion.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
	public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
	                          CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, blockTags, NatureExpansion.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		tag(ItemTags.LOGS_THAT_BURN)
				.add(ModBlocks.MAPLE_LOG.get().asItem())
				.add(ModBlocks.MAPLE_WOOD.get().asItem())
				.add(ModBlocks.STRIPPED_MAPLE_LOG.get().asItem())
				.add(ModBlocks.STRIPPED_MAPLE_WOOD.get().asItem());
		tag(ItemTags.PLANKS)
				.add(ModBlocks.MAPLE_PLANKS.get().asItem());

	}
}
