package net.findsnow.nature_expansion;

import net.findsnow.nature_expansion.block.ModBlocks;
import net.findsnow.nature_expansion.entity.ModEntities;
import net.findsnow.nature_expansion.item.ModCreativeModeTabs;
import net.findsnow.nature_expansion.item.ModItems;
import net.findsnow.nature_expansion.registry.ModBiomeReplacements;
import net.findsnow.nature_expansion.sound.ModSoundEvents;
import net.findsnow.nature_expansion.worldgen.ModFeatures;
import net.findsnow.nature_expansion.worldgen.ModSurfaceRules;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(NatureExpansion.MOD_ID)
public class NatureExpansion {

    public static final String MOD_ID = "nature_expansion";

    private static final Logger LOGGER = LogUtils.getLogger();

    public NatureExpansion(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModEntities.register(modEventBus);
        ModBiomeReplacements.registerBiomes();
        ModFeatures.FEATURES.register(modEventBus);
        ModSoundEvents.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModSurfaceRules.register();
        });
    }


    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.MAPLE_LEAF_LITTER.get(), RenderType.cutout());
        }
    }
}
