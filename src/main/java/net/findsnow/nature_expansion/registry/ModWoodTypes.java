package net.findsnow.nature_expansion.registry;

import net.findsnow.nature_expansion.NatureExpansion;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes {
	public static final WoodType MAPLE = WoodType.register(new WoodType(NatureExpansion.MOD_ID + ":maple", ModBlockSetTypes.MAPLE));
}
