package net.findsnow.nature_expansion.worldgen;

import com.terraformersmc.biolith.api.surface.SurfaceGeneration;
import net.findsnow.nature_expansion.NatureExpansion;
import net.findsnow.nature_expansion.registry.ModBiomes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRules {
	public static final SurfaceRules.RuleSource PODZOL = makeStateRules(Blocks.PODZOL);
	public static final SurfaceRules.RuleSource COARSE_DIRT = makeStateRules(Blocks.COARSE_DIRT);
	public static final SurfaceRules.RuleSource MUD = makeStateRules(Blocks.MUD);

	public static void register() {
		SurfaceRules.RuleSource autumnForest = SurfaceRules.ifTrue(
				SurfaceRules.isBiome(ModBiomes.AUTUMN_FOREST),
				SurfaceRules.ifTrue(surfaceNoiseAbove(1.25), PODZOL)
		);
		SurfaceRules.RuleSource autumnFields = SurfaceRules.ifTrue(
				SurfaceRules.isBiome(ModBiomes.AUTUMN_FOREST), SurfaceRules.sequence(
						SurfaceRules.ifTrue(surfaceNoiseAbove(1.25), PODZOL),
						SurfaceRules.ifTrue(surfaceNoiseAbove(-0.92), COARSE_DIRT)
				)
		);
		SurfaceGeneration.addOverworldSurfaceRules(ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID, "rules/overworld"),
				SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(
						autumnForest, autumnFields))));
	}



	private static SurfaceRules.RuleSource makeStateRules(Block block) {
		return SurfaceRules.state(block.defaultBlockState());
	}

	private static SurfaceRules.ConditionSource surfaceNoiseAbove(double value) {
		return SurfaceRules.noiseCondition(Noises.SURFACE, value / 8.0D, Double.MAX_VALUE);
	}
}
