package net.findsnow.nature_expansion.mixin;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import oshi.util.tuples.Pair;

import java.util.function.Consumer;

@Mixin(value = OverworldBiomeBuilder.class, priority = 69421)
public final class ModOverworldBiomeBuilderMixin {

	@Inject(method = "addBiomes", at = @At(value = "HEAD"), cancellable = true)
	private void addBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> key, CallbackInfo ci) {
		ci.cancel();
	}
}
