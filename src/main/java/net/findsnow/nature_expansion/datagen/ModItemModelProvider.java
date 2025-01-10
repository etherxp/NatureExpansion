package net.findsnow.nature_expansion.datagen;

import net.findsnow.nature_expansion.NatureExpansion;
import net.findsnow.nature_expansion.block.ModBlocks;
import net.findsnow.nature_expansion.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, NatureExpansion.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		withExistingParent(ModItems.BEAR_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
		saplingItem(ModBlocks.RED_MAPLE_SAPLING);
		saplingItem(ModBlocks.ORANGE_MAPLE_SAPLING);
		saplingItem(ModBlocks.GREEN_MAPLE_SAPLING);
		basicItem(ModBlocks.MAPLE_LEAF_LITTER.asItem());
	}

	private ItemModelBuilder saplingItem(DeferredBlock<Block> item) {
		return withExistingParent(item.getId().getPath(),
				ResourceLocation.parse("item/generated")).texture("layer0",
				ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID,"block/" + item.getId().getPath()));
	}

	public void buttonItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
		this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
				.texture("texture",  ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID,
						"block/" + baseBlock.getId().getPath()));
	}

	public void fenceItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
		this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
				.texture("texture",  ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID,
						"block/" + baseBlock.getId().getPath()));
	}

	public void wallItem(DeferredBlock<Block> block, DeferredBlock<Block> baseBlock) {
		this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
				.texture("wall",  ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID,
						"block/" + baseBlock.getId().getPath()));
	}
}
