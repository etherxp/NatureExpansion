package net.findsnow.nature_expansion.entity.core.ai;

import net.findsnow.nature_expansion.entity.core.SleepingAnimal;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class SleepGoal <E extends PathfinderMob & SleepingAnimal> extends Goal {
	private final E entity;

	public SleepGoal(E entity) {
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
		this.entity = entity;
	}


	@Override
	public boolean canUse() {
		if (entity.xxa == 0.0F && entity.yya == 0.0F && entity.zza == 0.0F) {
			return entity.canSleep() || entity.isSleeping();
		} else {
			return false;
		}
	}

	@Override
	public boolean canContinueToUse() {
		return entity.canSleep();
	}

	public void start() {
		entity.setJumping(false);
		entity.setSleeping(true);
		entity.getNavigation().stop();
		entity.getMoveControl().setWantedPosition(entity.getX(), entity.getY(), entity.getZ(), 0.0D);
	}

	@Override
	public void stop() {
		entity.setSleeping(false);
	}
}
