package net.findsnow.nature_expansion.event;

import net.findsnow.nature_expansion.NatureExpansion;
import net.findsnow.nature_expansion.entity.ModEntities;
import net.findsnow.nature_expansion.entity.ModModelLayers;
import net.findsnow.nature_expansion.entity.client.model.BearModel;
import net.findsnow.nature_expansion.entity.custom.BearEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = NatureExpansion.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(ModEntities.BEAR.get(), BearEntity.createAttributes().build());
	}
}
