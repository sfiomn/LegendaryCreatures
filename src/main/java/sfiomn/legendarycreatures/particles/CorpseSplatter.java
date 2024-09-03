package sfiomn.legendarycreatures.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

//  Deeply inspired by the tall AloeVera from MinecraftAbnormals
//  https://github.com/team-abnormals/atmospheric/blob/1.16.x/src/main/java/com/minecraftabnormals/atmospheric/client/particle/AloeBlossomParticle.java
public class CorpseSplatter extends TextureSheetParticle {
    protected final SpriteSet animatedSprite;
    private float angle;

    protected CorpseSplatter(SpriteSet animatedSprite, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);

        this.setSpriteFromAge(animatedSprite);
        this.animatedSprite = animatedSprite;

        this.angle = this.random.nextFloat() * ((float) Math.PI * 2F);

        float size = 0.5f + (this.random.nextFloat() * 0.5f);

        //  Duration of particle in ticks
        this.lifetime = this.random.nextInt(10) + (int) (20.0f * size);

        //  Scaling
        this.quadSize *= (float) (size * 0.8F + (this.random.nextFloat() * 0.4F));

        //  Motion of particles
        this.xd = xd / size;
        this.yd = (yd / size) + (this.random.nextDouble() * 0.05D);
        this.zd = zd / size;

        //  Color
        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;

        this.gravity = 0.08F;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age % 5 == 0) {
            this.angle = (float) Math.random() * ((float) Math.PI * 2F);
        }
        this.xd += Math.cos(this.angle) * 0.0005;
        this.zd += Math.sin(this.angle) * 0.0005;
        this.yd -= this.gravity;
        this.setSpriteFromAge(this.animatedSprite);
        fadeOut();
    }

    private void fadeOut() {
        if (((float) age / lifetime) < 0.8f) {
            this.alpha = 1;
        } else {
            this.alpha = 1 - (((float) age / lifetime - 0.8f) * 5.0f);
        }
    }

    @Override
    protected int getLightColor(float partialTick) {
        float f = this.lifetime / (((this.age + (this.lifetime * 0.5F)) + partialTick));
        f = Mth.clamp(f, 0F, 1.0F);
        int i = super.getLightColor(partialTick);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int) (f * 15f * 16f);
        if (j > 240) {
            j = 240;
        }
        return j | k << 16;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet animatedSprite;

        public Factory(SpriteSet animatedSprite) {
            this.animatedSprite = animatedSprite;
        }

        @Nullable
        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            return new CorpseSplatter(this.animatedSprite, level, x, y, z, xd, yd, zd);
        }
    }
}
