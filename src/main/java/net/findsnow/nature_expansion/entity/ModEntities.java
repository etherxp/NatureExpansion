package net.findsnow.nature_expansion.entity;

import net.findsnow.nature_expansion.NatureExpansion;
import net.findsnow.nature_expansion.entity.custom.BearEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
			DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, NatureExpansion.MOD_ID);

	public static final Supplier<EntityType<BearEntity>> BEAR =
			ENTITY_TYPES.register("bear", () -> EntityType.Builder.of(BearEntity::new, MobCategory.CREATURE)
					.sized(1.6F, 1.6F)
					.build("bear"));

	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}
}
