package net.findsnow.nature_expansion.entity.custom;

import net.findsnow.nature_expansion.entity.ModEntities;
import net.findsnow.nature_expansion.entity.core.SleepingAnimal;
import net.findsnow.nature_expansion.entity.core.ai.SleepGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;
import java.util.function.Predicate;

public class BearEntity extends Animal implements GeoEntity, NeutralMob, SleepingAnimal{

	private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public static final RawAnimation BEAR_WALK, BEAR_RUN, BEAR_IDLE, BEAR_SLEEP, BEAR_ROAR, BEAR_BITE, BEAR_LAY_DOWN, BEAR_GET_UP;
	static {
		BEAR_WALK = RawAnimation.begin().then("animation.bear.walk", Animation.LoopType.LOOP);
		BEAR_RUN = RawAnimation.begin().then("animation.bear.run", Animation.LoopType.LOOP);
		BEAR_IDLE = RawAnimation.begin().then("animation.bear.idle", Animation.LoopType.LOOP);
		BEAR_SLEEP = RawAnimation.begin().then("animation.bear.lay_down", Animation.LoopType.PLAY_ONCE);
		BEAR_ROAR = RawAnimation.begin().then("animation.bear.roar", Animation.LoopType.PLAY_ONCE);
		BEAR_BITE = RawAnimation.begin().then("animation.bear.bite", Animation.LoopType.PLAY_ONCE);
		BEAR_LAY_DOWN = RawAnimation.begin().then("animation.bear.lay_down", Animation.LoopType.PLAY_ONCE);
		BEAR_GET_UP = RawAnimation.begin().then("animation.bear.lay_stand", Animation.LoopType.PLAY_ONCE);
	}

	private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(BearEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(BearEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> ROARING = SynchedEntityData.defineId(BearEntity.class, EntityDataSerializers.BOOLEAN);
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private static final EntityDataAccessor<Integer> REMAINING_ANGER_TIME = SynchedEntityData.defineId(BearEntity.class, EntityDataSerializers.INT);
	@Nullable
	private UUID persistentAngerTarget;
	private boolean playerDetected = false;

	public BearEntity(EntityType<? extends Animal> entityType, Level level) {
		super(entityType, level);
		this.maxUpStep();
	}

	@Override
	protected void customServerAiStep() {
		if (this.getMoveControl().hasWanted()) {
			this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.0D);
		} else {
			this.setSprinting(false);
		}
		super.customServerAiStep();
	}

	// BREEDING STUFF
	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		return ModEntities.BEAR.get().create(level);
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		return itemStack.is(Items.SALMON);
	}

	// ATTRIBUTES & GOALS

	public static AttributeSupplier.Builder createAttributes() {
		return Animal.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 20.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.3F)
				.add(Attributes.FOLLOW_RANGE, 16D)
				.add(Attributes.ATTACK_DAMAGE, 6.0F);
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		return new GroundPathNavigation(this, level);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new BearSleepGoal(this));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.5F));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Bee.class, 8.0F, 1.3, 1.3));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.3D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new BearAttackPlayerNearCubsGoal(this, Player.class, 20, false, true, null));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel) this.level(), true);
		}
		if (this.isSleeping() || this.isImmobile()) {
			this.jumping = false;
			this.xxa = 0.0F;
			this.zza = 0.0F;
		}
	}

	private boolean isMakingEyeContact(Player player) {
		Vec3 bearDirection = this.getViewVector(1.0F).normalize();
		Vec3 playerDirection = new Vec3(player.getX() - this.getX(), player.getEyeY() - this.getEyeY(), player.getZ() - this.getZ()).normalize();
		double dotProduct = bearDirection.dot(playerDirection);
		return dotProduct > 0.5; // Adjust the threshold as needed
	}

	public boolean isPlayerSpotted() {
		Player nearestPlayer = this.level().getNearestPlayer(this, 6.0D);
		if (nearestPlayer != null && !nearestPlayer.isCreative() && this.isMakingEyeContact(nearestPlayer)) {
			this.playerDetected = true;
			return true;
		}
		this.playerDetected = false;
		return false;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource source) {
		return source.equals(DamageTypes.SWEET_BERRY_BUSH) || super.isInvulnerableTo(source);
	}

	@Override
	public int getMaxHeadYRot() {
		return 25;
	}

	// DATA
	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SLEEPING, false);
		builder.define(SITTING, false);
		builder.define(ROARING, false);
		builder.define(REMAINING_ANGER_TIME, 0);
	}

	@Override
	public boolean isSleeping() {
		return this.entityData.get(SLEEPING);
	}

	@Override
	public boolean canSleep() {
		long dayTime = this.level().getDayTime();
		return (dayTime < 12000 || dayTime > 18000) && dayTime < 23000 && dayTime > 6000 && !this.isInWater();
	}

	@Override
	public void setSleeping(boolean sleeping) {
		this.entityData.set(SLEEPING, sleeping);
	}

	// ANGER
	@Override
	public int getRemainingPersistentAngerTime() {
		return this.entityData.get(REMAINING_ANGER_TIME);
	}


	@Override
	public void setRemainingPersistentAngerTime(int remainingPersistentAngerTime) {
		this.entityData.set(REMAINING_ANGER_TIME, remainingPersistentAngerTime);
	}

	@Nullable
	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID persistentAngerTarget) {
		this.persistentAngerTarget = persistentAngerTarget;
	}

	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Override
	public double getTick(Object object) {
		return 0;
	}

	// SOUNDS
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.POLAR_BEAR_STEP, 0.15F, 1.0F);
	}

	@Override
	public void playAmbientSound() {
		if (this.isBaby()) {
			this.playSound(SoundEvents.POLAR_BEAR_AMBIENT_BABY);
		} else {
			this.playSound(SoundEvents.POLAR_BEAR_AMBIENT);
		}
	}

	@Override
	protected void playHurtSound(DamageSource source) {
		this.playSound(SoundEvents.POLAR_BEAR_HURT);
	}

	// Geckolib Predicates

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controllerPredicateBear", 5, this::predicate));
	}

	private PlayState predicate(AnimationState<BearEntity> animationState) {
		if (this.isSleeping()) {
			animationState.getController().setAnimation(BEAR_SLEEP);
			return PlayState.CONTINUE;
		} else if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
			if (this.isSprinting()) {
				animationState.getController().setAnimation(BEAR_RUN);
				return PlayState.CONTINUE;
			} else {
				animationState.getController().setAnimation(BEAR_WALK);
				return PlayState.CONTINUE;
			}
		} else {
			animationState.getController().setAnimation(BEAR_IDLE);
		}
		animationState.getController().forceAnimationReset();
		return PlayState.STOP;
	}

	// GOALS
	static class BearAttackPlayerNearCubsGoal extends NearestAttackableTargetGoal<Player> {
		private final BearEntity bear;

		public BearAttackPlayerNearCubsGoal(BearEntity mob, Class<Player> targetType, int randomInterval, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> targetPredicate) {
			super(mob, targetType, randomInterval, mustSee, mustReach, targetPredicate);
			this.bear = mob;
		}

		@Override
		public boolean canUse() {
			if (!bear.isBaby() && !bear.isSleeping()) {
				if (super.canUse()) {
					for (BearEntity bear : bear.level().getEntitiesOfClass(BearEntity.class, bear.getBoundingBox().inflate(8.0D, 4.0D, 8.0D))) {
						if (bear.isBaby()) {
							return true;
						}
					}
				}

			}
			return false;
		}

		@Override
		protected double getFollowDistance() {
			return super.getFollowDistance() * 0.5D;
			}
		}
	}



	class BearSleepGoal extends SleepGoal<BearEntity> {
		public BearSleepGoal(BearEntity entity) {
			super(entity);
		}
		public void start() {
			super.start();
		}
	}
