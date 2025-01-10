package net.findsnow.nature_expansion.event;

import net.findsnow.nature_expansion.NatureExpansion;
import net.findsnow.nature_expansion.block.ModBlocks;
import net.findsnow.nature_expansion.entity.ModEntities;
import net.findsnow.nature_expansion.entity.ModModelLayers;
import net.findsnow.nature_expansion.entity.client.model.BearModel;
import net.findsnow.nature_expansion.entity.custom.BearEntity;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = NatureExpansion.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(ModEntities.BEAR.get(), BearEntity.createAttributes().build());
	}

	@SubscribeEvent
	public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
		event.getItemColors().register((stack, index) ->
				FoliageColor.get(0.5D, 1.0D),
				ModBlocks.GREEN_MAPLE_LEAVES.get());
	}

	@SubscribeEvent
	public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
		event.getBlockColors().register((state, level, pos, tintIndex) ->
				level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : GrassColor.get(0.5D, 1.0D),
				ModBlocks.GREEN_MAPLE_LEAVES.get());
	}
}
