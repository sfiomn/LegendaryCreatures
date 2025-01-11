package sfiomn.legendarycreatures.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sfiomn.legendarycreatures.LegendaryCreatures;
import sfiomn.legendarycreatures.items.render.model.StrawHatModel;

import java.util.List;
import java.util.function.Consumer;

public class StrawHatItem extends ArmorItem {

    public StrawHatItem(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(
                new IClientItemExtensions() {
                    StrawHatModel<?> modelRenderer;

                    @Override
                    public @NotNull Model getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {

                        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
                        if (modelRenderer == null)
                            modelRenderer = new StrawHatModel<>(modelSet.bakeLayer(StrawHatModel.LAYER_LOCATION));

                        return modelRenderer.copyHead(original.head);
                    }
                }
        );
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return LegendaryCreatures.MOD_ID + ":textures/models/armor/straw_hat.png";
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> tooltips, TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, tooltips, p_41424_);
        tooltips.add(
                Component.translatable(this.getDescriptionId() + ".description")
                        .withStyle(ChatFormatting.BLUE));
    }
}
