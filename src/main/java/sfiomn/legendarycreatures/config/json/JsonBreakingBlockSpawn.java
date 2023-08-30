package sfiomn.legendarycreatures.config.json;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import net.minecraft.util.math.MathHelper;

public class JsonBreakingBlockSpawn
{
	@SerializedName("chance")
	public double chance;

	public JsonBreakingBlockSpawn(double chance)
	{
		this.chance = MathHelper.clamp(chance, 0, 1.0);
	}

	public JsonBreakingBlockSpawn(JsonObject jsonObject) {
		this(jsonObject.get("chance").getAsDouble());
	}
}
