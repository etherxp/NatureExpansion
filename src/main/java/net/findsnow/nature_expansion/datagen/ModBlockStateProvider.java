package net.findsnow.nature_expansion.datagen;

import net.findsnow.nature_expansion.NatureExpansion;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, NatureExpansion.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {

	}
}
