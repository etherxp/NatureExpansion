package net.findsnow.nature_expansion.entity.client.renderer;


import com.mojang.blaze3d.vertex.PoseStack;
import net.findsnow.nature_expansion.NatureExpansion;
import net.findsnow.nature_expansion.entity.client.model.BearModel;
import net.findsnow.nature_expansion.entity.custom.BearEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BearRenderer extends GeoEntityRenderer<BearEntity> {
	public BearRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new BearModel());
	}

	@Override
	public void render(BearEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
	                   MultiBufferSource bufferSource, int packedLight) {
		if (entity.isBaby()) {
			poseStack.scale(0.5F, 0.5F, 0.5F);
		}
		super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
	}
}