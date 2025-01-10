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

import java.util.UUID;
import java.util.function.Predicate;

public class BearEntity extends Animal implements NeutralMob, SleepingAnimal {

	@Nullable
	private UUID persistentAngerTarget;
	private boolean playerDetected = false;

	private long lastRoarTime = 0;
	private long lastDamageTime = 0;
	private static final long ROAR_COOLDOWN = 600;

	private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(BearEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(BearEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> ROARING = SynchedEntityData.defineId(BearEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Long> LAST_POSE_CHANGE_TICK = SynchedEntityData.defineId(BearEntity.class, EntityDataSerializers.LONG);
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private static final EntityDataAccessor<Integer> REMAINING_ANGER_TIME = SynchedEntityData.defineId(BearEntity.class, EntityDataSerializers.INT);

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

	@Override
	protected EntityDimensions getDefaultDimensions(Pose pose) {
		if (this.isRoaring()) {
			return EntityDimensions.scalable(1.2F, 2.5F);
		}
		return super.getDefaultDimensions(pose);
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
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.6F)
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
		this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.5F));
		this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Bee.class, 8.0F, 1.3, 1.3));
		this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(7, new FollowParentGoal(this, 1.3D));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));

		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
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

	public boolean isMakingEyeContact(Player player) {
		Vec3 bearDirection = this.getViewVector(1.0F).normalize();
		Vec3 playerDirection = new Vec3(player.getX() - this.getX(), player.getEyeY() - this.getEyeY(), player.getZ() - this.getZ()).normalize();
		double dotProduct = bearDirection.dot(playerDirection);
		return dotProduct > 0.5; // Adjust the threshold as needed
	}

	public boolean isPlayerSpotted() {
		Player nearestPlayer = this.level().getNearestPlayer(this, 5.0D);
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
		builder.define(LAST_POSE_CHANGE_TICK, 0L);
		builder.define(REMAINING_ANGER_TIME, 0);
	}

	@Override
	public boolean isSleeping() {
		return this.entityData.get(SLEEPING);
	}

	@Override
	public boolean canSleep() {
		long currentTime = this.level().getGameTime();
		long dayTime = this.level().getDayTime();
		boolean recentlyDamaged = (currentTime - this.lastDamageTime) < 600; // 30 seconds (20 ticks per second)
		return (dayTime < 12000 || dayTime > 18000) && dayTime < 23000 && dayTime > 6000
				&& !this.isInWater() && !recentlyDamaged;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		boolean hurt = super.hurt(source, amount);
		if (hurt) {
			this.lastDamageTime = this.level().getGameTime();
		}
		return hurt;
	}

	@Override
	public void setSleeping(boolean sleeping) {
		entityData.set(SLEEPING, sleeping);
	}

	public boolean isRoaring() {
		return this.entityData.get(ROARING);
	}

	public void setRoaring(boolean roaring) {
		this.entityData.set(ROARING, roaring);
		if (roaring) {
			this.lastRoarTime = this.level().getGameTime();
		}
	}

	public boolean canRoar() {
		long currentTime = this.level().getGameTime();
		return this.isPlayerSpotted() && !this.isRoaring() && (currentTime - lastRoarTime) >= ROAR_COOLDOWN;
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

	class BearSleepGoal extends SleepGoal<BearEntity> {
		public BearSleepGoal(BearEntity entity) {
			super(entity);
		}

		public void start() {
			super.start();
		}
	}
 }
