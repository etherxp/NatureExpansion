package net.findsnow.nature_expansion.worldgen;

import net.findsnow.nature_expansion.NatureExpansion;
import net.findsnow.nature_expansion.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.OptionalInt;

public class ModConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE_KEY = registerKey("red_maple_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_MAPLE_KEY = registerKey("orange_maple_tree");
	public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_MAPLE_KEY = registerKey("green_maple_tree");

	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

		// Maple
//		register(context, RED_MAPLE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
//				BlockStateProvider.simple(ModBlocks.MAPLE_LOG.get()),
//				new FancyTrunkPlacer(3, 11, 0),
//				BlockStateProvider.simple(ModBlocks.RED_MAPLE_LEAVES.get()),
//				new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
//				new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build());
//		register(context, ORANGE_MAPLE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
//				BlockStateProvider.simple(ModBlocks.MAPLE_LOG.get()),
//				new FancyTrunkPlacer(3, 11, 0),
//				BlockStateProvider.simple(ModBlocks.ORANGE_MAPLE_LEAVES.get()),
//				new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
//				new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build());
//		register(context, GREEN_MAPLE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
//				BlockStateProvider.simple(ModBlocks.MAPLE_LOG.get()),
//				new FancyTrunkPlacer(3, 11, 0),
//				BlockStateProvider.simple(ModBlocks.GREEN_MAPLE_LEAVES.get()),
//				new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
//				new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build());
	}

	public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID, name));
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
	                                                                                      ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
		context.register(key, new ConfiguredFeature<>(feature, configuration));
	}
}
