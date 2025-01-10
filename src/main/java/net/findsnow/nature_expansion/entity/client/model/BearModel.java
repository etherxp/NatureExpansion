package net.findsnow.nature_expansion.entity.client.model;

import net.findsnow.nature_expansion.entity.custom.BearEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;

public class BearModel extends HierarchicalModel<BearEntity> {

	@Override
	public ModelPart root() {
		return null;
	}

	@Override
	public void setupAnim(BearEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}
}