package sfiomn.legendarycreatures.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;

public class NetherWispEntity extends WispEntity {
    public NetherWispEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    protected WispPurseEntity getPurseEntity() {
        return EntityTypeRegistry.NETHER_WISP_PURSE.get().create(this.level());
    }
}
