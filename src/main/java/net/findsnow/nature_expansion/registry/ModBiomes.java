package net.findsnow.nature_expansion.registry;

import net.findsnow.nature_expansion.NatureExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class ModBiomes {

	public static final ResourceKey<Biome> AUTUMN_FOREST = registerBiome("autumn_forest");


	public static ResourceKey<Biome> registerBiome(String name) {
		return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID, name));
	}
}
