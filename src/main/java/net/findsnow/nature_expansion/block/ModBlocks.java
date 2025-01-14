package net.findsnow.nature_expansion.block;

import net.findsnow.nature_expansion.NatureExpansion;
import net.findsnow.nature_expansion.item.ModItems;
import net.findsnow.nature_expansion.sound.ModSoundEvents;
import net.findsnow.nature_expansion.worldgen.ModTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
	public static final DeferredRegister.Blocks BLOCKS =
			DeferredRegister.createBlocks(NatureExpansion.MOD_ID);


	// Maple
	public static final DeferredBlock<Block> MAPLE_LOG = registerBlock("maple_log",
			() -> new ModLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
	public static final DeferredBlock<Block> MAPLE_WOOD = registerBlock("maple_wood",
			() -> new ModLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
	public static final DeferredBlock<Block> STRIPPED_MAPLE_LOG = registerBlock("stripped_maple_log",
			() -> new ModLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
	public static final DeferredBlock<Block> STRIPPED_MAPLE_WOOD = registerBlock("stripped_maple_wood",
			() -> new ModLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));
	public static final DeferredBlock<Block> MAPLE_PLANKS = registerBlock("maple_planks",
			() -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}

				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 20;
				}
				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 5;
				}
			});
	public static final DeferredBlock<Block> RED_MAPLE_LEAVES = registerBlock("red_maple_leaves",
			() -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}
				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 60;
				}
				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 30;
				}
			});
	public static final DeferredBlock<Block> ORANGE_MAPLE_LEAVES = registerBlock("orange_maple_leaves",
			() -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}
				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 60;
				}
				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 30;
				}
			});
	public static final DeferredBlock<Block> GREEN_MAPLE_LEAVES = registerBlock("green_maple_leaves",
			() -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)) {
				@Override
				public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return true;
				}
				@Override
				public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 60;
				}
				@Override
				public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
					return 30;
				}
			});
	public static final DeferredBlock<Block> RED_MAPLE_SAPLING = registerBlock("red_maple_sapling",
			() -> new SaplingBlock(ModTreeGrower.RED_MAPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
	public static final DeferredBlock<Block> ORANGE_MAPLE_SAPLING = registerBlock("orange_maple_sapling",
			() -> new SaplingBlock(ModTreeGrower.ORANGE_MAPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
	public static final DeferredBlock<Block> GREEN_MAPLE_SAPLING = registerBlock("green_maple_sapling",
			() -> new SaplingBlock(ModTreeGrower.GREEN_MAPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
	public static final DeferredBlock<Block> MAPLE_LEAF_LITTER = registerBlock("maple_leaf_litter",
			() -> new ModMapleLeafLitterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET)
					.strength(0.1F)
					.noOcclusion()
					.sound(ModSoundEvents.LEAF_LITTER_SOUNDS)
					.pushReaction(PushReaction.DESTROY)));

	public static final DeferredBlock<Block> TREE_TAP = registerBlock("tree_tap",
			() -> new ModTreeTapBlock(BlockBehaviour.Properties.of().noOcclusion()));

	private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
		DeferredBlock<T> toReturn = BLOCKS.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}

	private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
		ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}


	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}
}
