package sfiomn.legendarycreatures.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;

public class EnderWispEntity extends WispEntity {
    public EnderWispEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    protected WispPurseEntity getPurseEntity() {
        return EntityTypeRegistry.ENDER_WISP_PURSE.get().create(this.level());
    }
}
