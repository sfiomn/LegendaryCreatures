package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.CorpseEaterEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class CorpseEaterModel extends GeoModel<CorpseEaterEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/corpse_eater.geo.json");
    private final ResourceLocation texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/corpse_eater.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/corpse_eater.animation.json");

    @Override
    public ResourceLocation getModelResource(CorpseEaterEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(CorpseEaterEntity object) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(CorpseEaterEntity animatable) {
        return this.animations;
    }

    @Override
    public void setCustomAnimations(CorpseEaterEntity animatable, long instanceId, AnimationState<CorpseEaterEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
