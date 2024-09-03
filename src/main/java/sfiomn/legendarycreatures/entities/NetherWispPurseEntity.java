package sfiomn.legendarycreatures.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import sfiomn.legendarycreatures.config.Config;

import java.util.List;

public class NetherWispPurseEntity extends WispPurseEntity {

    public NetherWispPurseEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected List<Integer> getRangeXp() {
        return Config.Baked.netherWispPurseXpReward;
    }
}
