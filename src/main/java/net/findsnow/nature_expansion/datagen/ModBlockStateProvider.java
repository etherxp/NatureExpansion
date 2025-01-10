package net.findsnow.nature_expansion.datagen;

import net.findsnow.nature_expansion.NatureExpansion;
import net.findsnow.nature_expansion.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, NatureExpansion.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		// Maple
		logBlock(((RotatedPillarBlock) ModBlocks.MAPLE_LOG.get()));
		axisBlock(((RotatedPillarBlock) ModBlocks.MAPLE_WOOD.get()), blockTexture(ModBlocks.MAPLE_LOG.get()), blockTexture(ModBlocks.MAPLE_LOG.get()));
		logBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_MAPLE_LOG.get()));
		axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_MAPLE_WOOD.get()), blockTexture(ModBlocks.STRIPPED_MAPLE_LOG.get()), blockTexture(ModBlocks.STRIPPED_MAPLE_LOG.get()));
		blockItem(ModBlocks.MAPLE_LOG);
		blockItem(ModBlocks.MAPLE_WOOD);
		blockItem(ModBlocks.STRIPPED_MAPLE_LOG);
		blockItem(ModBlocks.STRIPPED_MAPLE_WOOD);
		blockWithItem(ModBlocks.MAPLE_PLANKS);
		leavesBlock(ModBlocks.RED_MAPLE_LEAVES);
		leavesBlock(ModBlocks.ORANGE_MAPLE_LEAVES);
		leavesBlock(ModBlocks.GREEN_MAPLE_LEAVES);
		saplingBlock(ModBlocks.RED_MAPLE_SAPLING);
		saplingBlock(ModBlocks.GREEN_MAPLE_SAPLING);
		saplingBlock(ModBlocks.ORANGE_MAPLE_SAPLING);
		leafLitterBlock(ModBlocks.MAPLE_LEAF_LITTER);
		blockItem(ModBlocks.MAPLE_LEAF_LITTER);
	}

	private void leavesBlock(DeferredBlock<Block> deferredBlock) {
		simpleBlockWithItem(deferredBlock.get(),
				models().singleTexture(BuiltInRegistries.BLOCK.getKey(deferredBlock.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
						"all", blockTexture(deferredBlock.get())).renderType("cutout"));
	}

	private void saplingBlock(DeferredBlock<Block> deferredBlock) {
		simpleBlock(deferredBlock.get(), models().cross(BuiltInRegistries.BLOCK.getKey(deferredBlock.get()).getPath(), blockTexture(deferredBlock.get())).renderType("cutout"));
	}

	private void blockWithItem(DeferredBlock<Block> deferredBlock) {
		simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
	}

	private void blockItem(DeferredBlock<Block> deferredBlock) {
		simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("nature_expansion:block/" + deferredBlock.getId().getPath()));
	}

	private void blockItem(DeferredBlock<Block> deferredBlock, String appendix) {
		simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("nature_expansion:block/" + deferredBlock.getId().getPath() + appendix));
	}

	private void leafLitterBlock(DeferredBlock<Block> deferredBlock) {
		String basePath = BuiltInRegistries.BLOCK.getKey(deferredBlock.get()).getPath();
		MultiPartBlockStateBuilder builder = getMultipartBuilder(deferredBlock.get());

		int[] rotations = {0, 90, 180, 270};
		String[] facings = {"north", "east", "south", "west"};

		for (int amount = 1; amount <= 4; amount++) {
			StringBuilder amountCondition = new StringBuilder();
			for (int i = amount; i <= 4; i++) {
				amountCondition.append(i);
				if (i < 4) amountCondition.append("|");
			}
			for (int i = 0; i < rotations.length; i++) {
				builder.part()
						.modelFile(models().getExistingFile(modLoc("block/" + basePath + "_" + amount)))
						.rotationY(rotations[i])
						.addModel()
						.condition(BlockStateProperties.HORIZONTAL_FACING, Direction.fromYRot(rotations[i]))
						.condition(BlockStateProperties.FLOWER_AMOUNT, amount);
			}
		}
	}
}
