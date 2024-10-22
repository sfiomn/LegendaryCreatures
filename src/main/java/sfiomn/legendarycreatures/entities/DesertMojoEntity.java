package sfiomn.legendarycreatures.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import sfiomn.legendarycreatures.registry.ParticleTypeRegistry;

public class DesertMojoEntity extends MojoEntity {
    private final int baseAttackDuration = 11;
    private final int baseAttackActionPoint = 5;

    public DesertMojoEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        setVariant(0);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {

            double offsetX = (2 * this.getRandom().nextFloat() - 1) * 0.3F;
            double offsetZ = (2 * this.getRandom().nextFloat() - 1) * 0.3F;

            double x = this.position().x + offsetX;
            double y = this.position().y + 1 + (this.getRandom().nextFloat() * 0.05F);
            double z = this.position().z + offsetZ;

            if (this.level().getGameTime() % 3 == 0)
                this.level().addParticle(ParticleTypeRegistry.DESERT_MOJO_PARTICLE.get(), x, y, z, offsetX / 10, 0.04D, offsetZ / 10);
        }
    }

    @Override
    protected int getBaseAttackDuration() {
        return baseAttackDuration;
    }

    @Override
    protected int getBaseAttackActionPoint() {
        return baseAttackActionPoint;
    }
}
