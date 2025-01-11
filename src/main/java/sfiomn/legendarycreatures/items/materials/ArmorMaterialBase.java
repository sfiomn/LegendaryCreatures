package sfiomn.legendarycreatures.items.materials;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import sfiomn.legendarycreatures.LegendaryCreatures;

import java.util.function.Supplier;

public enum ArmorMaterialBase implements ArmorMaterial
{
	STRAW("straw_hat", 20.0f, new int[] { 0, 0, 0, 0}, 17, SoundEvents.ARMOR_EQUIP_LEATHER, 0, 0.0f, () -> Ingredient.of(Items.WHEAT));

	private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};
	private final String name;
	private final float maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantmentValue;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final float knockbackResistance;
	private final Supplier<Ingredient> repairIngredient;
	
	ArmorMaterialBase(String name, float durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient)
	{
		this.name = name;
		this.maxDamageFactor = durabilityMultiplier;
		this.damageReductionAmountArray = protectionAmounts;
		this.enchantmentValue = enchantmentValue;
		this.soundEvent = soundEvent;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairIngredient = repairIngredient;
	}
	
	@Override
	public int getDurabilityForType(ArmorItem.Type slotIn)
	{
		return (int) (BASE_DURABILITY[slotIn.getSlot().getIndex()] * this.maxDamageFactor);
	}

	@Override
	public int getDefenseForType(ArmorItem.Type slotIn)
	{
		return this.damageReductionAmountArray[slotIn.getSlot().getIndex()];
	}

	@Override
	public int getEnchantmentValue()
	{
		return this.enchantmentValue;
	}

	@Override
	public @NotNull SoundEvent getEquipSound()
	{
		return this.soundEvent;
	}

	@Override
	public @NotNull Ingredient getRepairIngredient()
	{
		return this.repairIngredient.get();
	}

	@Override
	public @NotNull String getName()
	{
		return LegendaryCreatures.MOD_ID + ":" + this.name;
	}

	@Override
	public float getToughness()
	{
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance()
	{
		return this.knockbackResistance;
	}
}
