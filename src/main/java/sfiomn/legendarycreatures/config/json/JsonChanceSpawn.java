package sfiomn.legendarycreatures.config.json;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import net.minecraft.util.math.MathHelper;

public class JsonChanceSpawn
{
	@SerializedName("chance")
	public double chance;

	public JsonChanceSpawn(double chance)
	{
		this.chance = MathHelper.clamp(chance, 0, 1.0);
	}

	public JsonChanceSpawn(JsonObject jsonObject) {
		this(jsonObject.get("chance").getAsDouble());
	}
}
