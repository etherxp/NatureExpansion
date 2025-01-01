package net.findsnow.nature_expansion.block;

import net.findsnow.nature_expansion.NatureExpansion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
	public static final DeferredRegister.Blocks BLOCKS =
			DeferredRegister.createBlocks(NatureExpansion.MOD_ID);

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}
}
