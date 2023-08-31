package sfiomn.legendarycreatures.entities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
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
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;
import sfiomn.legendarycreatures.registry.SoundRegistry;
import sfiomn.legendarycreatures.util.WorldUtil;

import javax.annotation.Nullable;
import java.util.Random;

public class WispEntity extends AnimatedCreatureEntity implements IFlyingAnimal {
    public WispEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
        this.xpReward = 12;
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
                .add(Attributes.FLYING_SPEED, 0.45);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new PanicGoal(this, 5));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerEntity.class, (float) 30, 0.8, 1.4));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(4, new SwimGoal(this));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 0.8, 40) {
            @Override
            protected Vector3d getPosition() {
                Random random = this.mob.getRandom();
                double dir_x = this.mob.position().x + ((random.nextFloat() * 2 - 1) * 4);
                double dir_y = Math.max(this.mob.position().y + ((random.nextFloat() * 2 - 1) * 2), 120);
                double dir_z = this.mob.position().z + ((random.nextFloat() * 2 - 1) * 4);
                return new Vector3d(dir_x, dir_y, dir_z);
            }
        });
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

    public static void spawn(IWorld world, Vector3d pos) {
        if (!world.isClientSide()) {
            WispEntity entityToSpawn = EntityTypeRegistry.WISP.get().create((World) world);
            if (entityToSpawn != null) {
                WorldUtil.spawnEntity(entityToSpawn, world, pos);
            }
        }
    }
}
