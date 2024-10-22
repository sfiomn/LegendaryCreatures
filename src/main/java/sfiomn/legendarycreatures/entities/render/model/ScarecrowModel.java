package sfiomn.legendarycreatures.entities.render.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.entities.HoundEntity;
import sfiomn.legendarycreatures.entities.ScarecrowEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ScarecrowModel extends GeoModel<ScarecrowEntity> {
    private final ResourceLocation model = new ResourceLocation(LegendaryCreatures.MOD_ID, "geo/scarecrow.geo.json");
    private final ResourceLocation texture = new ResourceLocation(LegendaryCreatures.MOD_ID,"textures/entity/scarecrow.png");
    private final ResourceLocation animations = new ResourceLocation(LegendaryCreatures.MOD_ID, "animations/scarecrow.animation.json");

    @Override
    public ResourceLocation getModelResource(ScarecrowEntity object) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(ScarecrowEntity object) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(ScarecrowEntity animatable) {
        return this.animations;
    }

    @Override
    public void setCustomAnimations(ScarecrowEntity animatable, long instanceId, AnimationState<ScarecrowEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("face");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
