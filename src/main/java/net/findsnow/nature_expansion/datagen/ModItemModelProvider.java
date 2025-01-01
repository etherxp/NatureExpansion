package net.findsnow.nature_expansion.datagen;

import net.findsnow.nature_expansion.NatureExpansion;
import net.findsnow.nature_expansion.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
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
