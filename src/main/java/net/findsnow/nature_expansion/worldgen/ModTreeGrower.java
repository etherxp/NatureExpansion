package net.findsnow.nature_expansion.worldgen;

import net.findsnow.nature_expansion.NatureExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrower {
	public static final TreeGrower RED_MAPLE = new TreeGrower(
			"red_maple_tree",
			0.1F,
			Optional.empty(),
			Optional.empty(),
			Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID, "red_maple"))),
			Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID, "large_red_maple"))),
			Optional.empty(),
			Optional.empty()
	);
	public static final TreeGrower ORANGE_MAPLE = new TreeGrower(
			"orange_maple_tree",
			0.1F,
			Optional.empty(),
			Optional.empty(),
			Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID, "orange_maple"))),
			Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID, "large_orange_maple"))),
			Optional.empty(),
			Optional.empty()
	);
	public static final TreeGrower GREEN_MAPLE = new TreeGrower(
			"green_maple_tree",
			0.1F,
			Optional.empty(),
			Optional.empty(),
			Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID, "green_maple"))),
			Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID, "large_green_maple"))),
			Optional.empty(),
			Optional.empty()
	);
}
