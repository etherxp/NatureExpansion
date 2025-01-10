package net.findsnow.nature_expansion.sound;

import net.findsnow.nature_expansion.NatureExpansion;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSoundEvents {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
			DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, NatureExpansion.MOD_ID);

	public static final Supplier<SoundEvent> LEAF_LITTER_BREAK = registerSoundEvent("maple_leaf_litter_break");
	public static final Supplier<SoundEvent> LEAF_LITTER_STEP = registerSoundEvent("maple_leaf_litter_step");
	public static final Supplier<SoundEvent> LEAF_LITTER_PLACE = registerSoundEvent("maple_leaf_litter_place");
	public static final Supplier<SoundEvent> LEAF_LITTER_HIT = registerSoundEvent("maple_leaf_litter_hit");
	public static final Supplier<SoundEvent> LEAF_LITTER_FALL = registerSoundEvent("maple_leaf_litter_fall");
	public static final DeferredSoundType LEAF_LITTER = new DeferredSoundType(
			1.0F, 1.0F,
			ModSoundEvents.LEAF_LITTER_BREAK,
			ModSoundEvents.LEAF_LITTER_STEP,
			ModSoundEvents.LEAF_LITTER_PLACE,
			ModSoundEvents.LEAF_LITTER_HIT,
			ModSoundEvents.LEAF_LITTER_FALL);

	private static Supplier<SoundEvent> registerSoundEvent(String name) {
		ResourceLocation id = ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID, name);
		return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
	}

	public static void register(IEventBus eventBus) {
		SOUND_EVENTS.register(eventBus);
	}
}
