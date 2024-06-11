package sfiomn.legendarycreatures.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.World;
import sfiomn.legendarycreatures.config.Config;

import java.util.List;

public class EnderWispPurseEntity extends WispPurseEntity {

    public EnderWispPurseEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected List<Integer> getRangeXp() {
        return Config.Baked.enderWispPurseXpReward;
    }
}
