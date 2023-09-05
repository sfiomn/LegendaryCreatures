package sfiomn.legendarycreatures.config.json;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class JsonBiomeSpawn
{
	@SerializedName("weight")
	public int weight;

	@SerializedName("min_group")
	public int minGroup;

	@SerializedName("max_group")
	public int maxGroup;
	
	public JsonBiomeSpawn(int weight)
	{
		this(weight, 1, 1);
	}
	public JsonBiomeSpawn(int weight, int minGroup)
	{
		this(weight, minGroup, minGroup);
	}
	public JsonBiomeSpawn(int weight, int minGroup, int maxGroup)
	{
		this.weight = Math.max(weight, 0);
		this.minGroup = Math.max(minGroup, 1);
		this.maxGroup = Math.max(minGroup, maxGroup);
	}

	public JsonBiomeSpawn(JsonObject jsonObject) {
		this(jsonObject.get("weight").getAsInt(), jsonObject.get("min_group").getAsInt(), jsonObject.get("max_group").getAsInt());
	}
}
