package sfiomn.legendarycreatures.config.json;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import net.minecraft.util.math.MathHelper;
import sfiomn.legendarycreatures.api.entities.MobEntityEnum;

public class JsonMobIdBreakingBlockSpawn
{
	@SerializedName("chance")
	public JsonBreakingBlockSpawn jsonBreakingBlockSpawn;
	@SerializedName("mobEntityEnum")
	public MobEntityEnum mobEntityEnum;

	public JsonMobIdBreakingBlockSpawn(double chance, MobEntityEnum mobEntityEnum)
	{
		this.jsonBreakingBlockSpawn = new JsonBreakingBlockSpawn(chance);
		this.mobEntityEnum = mobEntityEnum;
	}
}
