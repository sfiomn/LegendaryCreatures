package sfiomn.legendarycreatures.entities;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import sfiomn.legendarycreatures.registry.EntityTypeRegistry;

public class EnderWispEntity extends WispEntity {
    public EnderWispEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected WispPurseEntity getPurseEntity() {
        return EntityTypeRegistry.ENDER_WISP_PURSE.get().create(this.level);
    }
}
