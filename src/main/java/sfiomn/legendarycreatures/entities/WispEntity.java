package sfiomn.legendarycreatures.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.util.AirRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendarycreatures.entities.goals.FleeAirGoal;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;
import sfiomn.legendarycreatures.registry.ParticleTypeRegistry;
import sfiomn.legendarycreatures.registry.SoundRegistry;

import javax.annotation.Nullable;

public class WispEntity extends AnimatedCreatureEntity implements FlyingAnimal {
    public WispEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);

        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.navigation = new FlyingPathNavigation(this, level);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 0)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.FLYING_SPEED, 0.35);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new PanicGoal(this, 5));
        this.goalSelector.addGoal(2, new FleeAirGoal<>(this, Player.class, (float) 40, 1.0, 1.7));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new FloatGoal(this));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0, 10) {
            @Override
            protected Vec3 getPosition() {
                Vec3 viewVector = this.mob.getViewVector(0);
                return AirRandomPos.getPosTowards(this.mob, 8, 4, -2, viewVector, 1.5707963705062866);
            }
        });
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {

            double offsetX = (2 * this.getRandom().nextFloat() - 1) * 0.3F;
            double offsetZ = (2 * this.getRandom().nextFloat() - 1) * 0.3F;

            double x = this.position().x + offsetX;
            double y = this.position().y + (this.getRandom().nextFloat() * 0.05F);
            double z = this.position().z + offsetZ;

            if (this.level().getGameTime() % 3 == 0)
                this.level().addParticle(ParticleTypeRegistry.WISP_PARTICLE.get(), x, y, z, offsetX / 10, 0.01D, offsetZ / 10);
        }
    }

    @Override
    public void setNoGravity(boolean noGravity) {
        super.setNoGravity(true);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.WISP_IDLE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.WISP_DEATH.get();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FALLING_ANVIL))
            return false;
        if (source.is(DamageTypes.DRAGON_BREATH))
            return false;
        if (source.is(DamageTypes.CACTUS))
            return false;
        return super.hurt(source, amount);
    }

    protected WispPurseEntity getPurseEntity() {
        return EntityTypeRegistry.WISP_PURSE.get().create(this.level());
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        if (!this.level().isClientSide && this.isDeadOrDying() && !this.isRemoved()) {
            WispPurseEntity wispPurseEntity = getPurseEntity();
            if (wispPurseEntity == null)
                return;

            if (this.isPersistenceRequired()) {
                wispPurseEntity.setPersistenceRequired();
            }

            wispPurseEntity.setInvulnerable(this.isInvulnerable());
            wispPurseEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.random.nextFloat() * 360.0F, this.random.nextFloat() * 360.0F);
            this.level().addFreshEntity(wispPurseEntity);
        }

        super.remove(reason);
    }

    @Override
    public boolean isFlying() {
        return true;
    }
}
