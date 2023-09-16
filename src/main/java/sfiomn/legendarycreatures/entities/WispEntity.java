package sfiomn.legendarycreatures.entities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;
import sfiomn.legendarycreatures.registry.ParticleTypeRegistry;
import sfiomn.legendarycreatures.registry.SoundRegistry;
import sfiomn.legendarycreatures.util.WorldUtil;

import javax.annotation.Nullable;
import java.util.Random;

public class WispEntity extends AnimatedCreatureEntity implements IFlyingAnimal {
    public WispEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
        this.maxUpStep = 1.0F;

        this.moveControl = new FlyingMovementController(this, 10, true);
        this.navigation = new FlyingPathNavigator(this, world);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
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
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerEntity.class, (float) 30, 0.8, 1.4));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(4, new SwimGoal(this));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0, 20) {
            @Override
            protected Vector3d getPosition() {
                return RandomPositionGenerator.getAirPos(this.mob, 5, 5, 3, null, 0);
            }
        });
    }

    @Override
    public void tick() {
        super.tick();

        double offsetX = (2 * this.getRandom().nextFloat() - 1) * 0.3F;
        double offsetZ = (2 * this.getRandom().nextFloat() - 1) * 0.3F;

        double x = this.position().x + offsetX;
        double y = this.position().y + (this.getRandom().nextFloat() * 0.05F);
        double z = this.position().z + offsetZ;

        if (this.level.getGameTime() % 3 == 0)
            this.level.addParticle(ParticleTypeRegistry.WISP_PARTICLE.get(), x, y, z, offsetX / 10, 0.01D, offsetZ / 10);
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
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.HOSTILE_HURT;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source == DamageSource.ANVIL)
            return false;
        if (source == DamageSource.DRAGON_BREATH)
            return false;
        if (source == DamageSource.CACTUS)
            return false;
        return super.hurt(source, amount);
    }

    @Override
    public void remove(boolean keepData) {
        if (!this.level.isClientSide && this.isDeadOrDying() && !this.removed) {
            WispPurseEntity wispPurseEntity = EntityTypeRegistry.WISP_PURSE.get().create(this.level);
            if (wispPurseEntity == null)
                return;

            if (this.isPersistenceRequired()) {
                wispPurseEntity.setPersistenceRequired();
            }

            wispPurseEntity.setInvulnerable(this.isInvulnerable());
            wispPurseEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.random.nextFloat() * 360.0F, this.random.nextFloat() * 360.0F);
            this.level.addFreshEntity(wispPurseEntity);
        }

        super.remove(keepData);
    }

    // Only used by ModEvents to spawn an entity based on killing entity or breaking block
    public static void spawn(IWorld world, Vector3d pos) {
        if (!world.isClientSide()) {
            WispEntity entityToSpawn = EntityTypeRegistry.WISP.get().create((World) world);
            if (entityToSpawn != null) {
                WorldUtil.spawnEntity(entityToSpawn, world, pos);
            }
        }
    }
}
