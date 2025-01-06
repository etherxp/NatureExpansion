package net.findsnow.nature_expansion.registry;

import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.biome.sub.BiomeParameterTargets;
import com.terraformersmc.biolith.api.biome.sub.Criterion;
import com.terraformersmc.biolith.api.biome.sub.CriterionBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class ModBiomeReplacements {
	public static void registerBiomes() {
		BiomePlacement.replaceOverworld(
				Biomes.FOREST,
				ModBiomes.AUTUMN_FOREST,
				0.3);
	}




	public static void transitionBiome(ResourceKey<Biome> mainBiome, ResourceKey<Biome> secondaryBiome, ResourceKey<Biome> transitionBiome) {
		BiomePlacement.addSubOverworld(
				mainBiome,
				transitionBiome,
				CriterionBuilder.allOf(CriterionBuilder.neighbor(secondaryBiome), CriterionBuilder.not(CriterionBuilder.NEAR_INTERIOR))
		);
	}

	public static void edgeBiome(ResourceKey<Biome> interiorBiome, ResourceKey<Biome> edgeBiome) {
		BiomePlacement.addSubOverworld(
				interiorBiome,
				edgeBiome,
				CriterionBuilder.anyOf(
						CriterionBuilder.allOf(
								CriterionBuilder.NEAR_BORDER,
								CriterionBuilder.not(CriterionBuilder.NEAR_INTERIOR)
						),
						CriterionBuilder.anyOf(CriterionBuilder.BEACHSIDE, CriterionBuilder.OCEANSIDE, CriterionBuilder.RIVERSIDE)
				)
		);
	}

	public static void clearingBiome(ResourceKey<Biome> interiorBiome, ResourceKey<Biome> clearingBiome) {
		BiomePlacement.addSubOverworld(
				interiorBiome,
				clearingBiome,
				CriterionBuilder.allOf(CriterionBuilder.deviationMin(BiomeParameterTargets.PEAKS_VALLEYS, .05F), CriterionBuilder.NEAR_INTERIOR)
		);
	}
}
