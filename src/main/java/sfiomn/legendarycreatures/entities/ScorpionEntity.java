package sfiomn.legendarycreatures.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.goals.BaseMeleeAttackGoal;
import sfiomn.legendarycreatures.entities.goals.PoisonMeleeAttackGoal;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;
import sfiomn.legendarycreatures.registry.SoundRegistry;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import javax.annotation.Nullable;

public class ScorpionEntity extends AnimatedCreatureEntity {
    private final int baseAttackDuration = 16;
    private final int baseAttackActionPoint = 10;
    private final int tailAttackDuration = 20;
    private final int tailAttackActionPoint = 7;
    public ScorpionEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
        this.xpReward = 5;
        if (isLevel2())
            this.xpReward = 10;

        this.maxUpStep = 1.0F;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 18)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        // 70% to have babies
        boolean hasBabies = getRandom().nextInt(100) >= 70;

        // 10% to be level 2 scorpion
        if (getRandom().nextInt(100) >= 90)
            if (hasBabies)
                this.setVariant(8);
            else
                this.setVariant(2);
        else
            if (hasBabies)
                this.setVariant(7);

    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        PoisonMeleeAttackGoal poisonMeleeAttackGoal = new PoisonMeleeAttackGoal(this, 200, 0, 3, tailAttackDuration, tailAttackActionPoint, 5, 1.0, true, 200){
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return (double) (getMobLength() * 2.0F * getMobLength() * 2.0F + entity.getBbWidth());
            }

            @Override
            protected void executeAttack(LivingEntity target) {
                super.executeAttack(target);
                this.mob.playSound(SoundRegistry.SCORPION_TAIL_ATTACK_HIT.get(), 1.0f, 1.0f);
            }
        };

        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, poisonMeleeAttackGoal);
        this.goalSelector.addGoal(4, new BaseMeleeAttackGoal(this, baseAttackDuration, baseAttackActionPoint, 5, 1.0, true){
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return (double) (getMobLength() * 2.0F * getMobLength() * 2.0F + entity.getBbWidth());
            }

            @Override
            protected boolean executeAttack(LivingEntity target) {
                this.mob.playSound(SoundRegistry.SCORPION_CLAWS_ATTACK_HIT.get(), 1.0f, 1.0f);
                return super.executeAttack(target);
            }

            @Override
            public boolean canContinueToUse() {
                if (poisonMeleeAttackGoal.canUse())
                    return false;
                return super.canContinueToUse();
            }
        });
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false, false));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 0.6, 40));
    }

    private float getMobLength() {
        return 1.5f;
    }

    @Override
    public <E extends IAnimatable> PlayState attackingPredicate(AnimationEvent<E> event) {
        if (getAttackAnimation() == BASE_ATTACK && event.getController().getAnimationState() == AnimationState.Stopped) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("claws", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        } else if (getAttackAnimation() == EFFECT_ATTACK && event.getController().getAnimationState() == AnimationState.Stopped) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("tail", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        }
        return PlayState.CONTINUE;
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld serverWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt) {
        ModifiableAttributeInstance healthAttribute = this.getAttribute(Attributes.MAX_HEALTH);
        ModifiableAttributeInstance attackAttribute = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (this.isLevel2()) {
            if (healthAttribute != null) {
                healthAttribute.addPermanentModifier(new AttributeModifier(MAX_HEALTH_UUID, LegendaryCreatures.MOD_ID + ":scorpion_level2", healthAttribute.getBaseValue() * 2, AttributeModifier.Operation.ADDITION));
                this.setHealth(1000);
            }
            if (attackAttribute != null) {
                attackAttribute.addPermanentModifier(new AttributeModifier(ATTACK_DAMAGE_UUID, LegendaryCreatures.MOD_ID + ":scorpion_level2", attackAttribute.getBaseValue() * 2, AttributeModifier.Operation.ADDITION));
            }
        }

        return super.finalizeSpawn(serverWorld, difficultyInstance, spawnReason, entityData, nbt);
    }

    public boolean isLevel2() {
        return getVariant() == 2 || getVariant() == 8;
    }

    @Override
    public AnimationBuilder getSprintAnimation() {
        return new AnimationBuilder().addAnimation("run", ILoopType.EDefaultLoopTypes.LOOP);
    }

    public boolean hasBabies() {
        return this.getVariant() >= 7;
    }

    public CreatureAttribute getMobType() {
        return CreatureAttribute.ARTHROPOD;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return 0.35F;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.SCORPION_IDLE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.SCORPION_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundRegistry.SCORPION_HURT.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundRegistry.SCORPION_STEP.get(), 1.0F, 1.0F);
    }

    @Override
    public void remove(boolean keepData) {
        if (!this.level.isClientSide && this.isDeadOrDying() && !this.removed && hasBabies()) {
            int nbBabies = 1 + this.random.nextInt(3);

            for(int l = 0; l < nbBabies; ++l) {
                float f1 = ((float)(l % 2) - 0.5F);
                float f2 = ((float)(l / 2) - 0.5F);
                ScorpionBabyEntity scorpionBabyEntity = EntityTypeRegistry.SCORPION_BABY.get().create(this.level);
                if (scorpionBabyEntity == null)
                    return;
                if (this.isPersistenceRequired()) {
                    scorpionBabyEntity.setPersistenceRequired();
                }

                this.level.playSound(null, new BlockPos(this.position()), SoundEvents.SILVERFISH_STEP, SoundCategory.HOSTILE, 10.0F, 1.0F);
                scorpionBabyEntity.setInvulnerable(this.isInvulnerable());
                if (isLevel2()) {
                    scorpionBabyEntity.setVariant(2);
                }
                scorpionBabyEntity.moveTo(this.getX() + (double)f1, this.getY() + 0.5D, this.getZ() + (double)f2, this.random.nextFloat() * 360.0F, 0.0F);
                this.level.addFreshEntity(scorpionBabyEntity);
            }
        }

        super.remove(keepData);
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        if (isLevel2())
            return new ResourceLocation(LegendaryCreatures.MOD_ID, "entities/scorpion_level2");
        return super.getDefaultLootTable();
    }
}
