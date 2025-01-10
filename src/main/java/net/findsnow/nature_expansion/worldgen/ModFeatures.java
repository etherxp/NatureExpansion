package net.findsnow.nature_expansion.worldgen;

import net.findsnow.nature_expansion.NatureExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES =
			DeferredRegister.create(Registries.FEATURE, NatureExpansion.MOD_ID);

	public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE = register("red_maple");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_RED_MAPLE = register("large_red_maple");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_MAPLE = register("orange_maple");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_ORANGE_MAPLE = register("large_orange_maple");
	public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_MAPLE = register("green_maple");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GREEN_MAPLE = register("large_green_maple");

	public static ResourceKey<ConfiguredFeature<?, ?>> register(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID, name));
	}
}
