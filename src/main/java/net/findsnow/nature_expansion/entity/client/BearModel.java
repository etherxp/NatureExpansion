package net.findsnow.nature_expansion.entity.client;

import net.findsnow.nature_expansion.NatureExpansion;
import net.findsnow.nature_expansion.entity.custom.BearEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.renderer.GeoRenderer;

public class BearModel extends GeoModel<BearEntity> {

	@Override
	public ResourceLocation getTextureResource(BearEntity animatable) {
		return getTextureResource(animatable, null);
	}

	@Override
	public ResourceLocation getTextureResource(BearEntity animatable, @Nullable GeoRenderer<BearEntity> renderer) {
		return ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID, "textures/entity/bear/bear.png");
	}

	@Override
	public ResourceLocation getModelResource(BearEntity animatable) {
		return getModelResource(animatable, null);
	}

	@Override
	public ResourceLocation getModelResource(BearEntity animatable, @Nullable GeoRenderer<BearEntity> renderer) {
		return ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID, "geo/bear.geo.json");
	}

	@Override
	public ResourceLocation getAnimationResource(BearEntity animatable) {
		return ResourceLocation.fromNamespaceAndPath(NatureExpansion.MOD_ID, "animations/bear.animation.json");
	}

	@Override
	public void setCustomAnimations(BearEntity animatable, long instanceId, AnimationState<BearEntity> animationState) {
		GeoBone head = this.getAnimationProcessor().getBone("head");

		if (animatable.isBaby()) {
			head.setScaleX(1.2F); head.setScaleY(1.2F); head.setScaleZ(1.2F);
		}

		if (head != null) {
			EntityModelData entityModelData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityModelData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityModelData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}
}
