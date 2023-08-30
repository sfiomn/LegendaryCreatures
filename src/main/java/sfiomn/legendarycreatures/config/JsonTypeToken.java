package sfiomn.legendarycreatures.config;

import com.google.gson.reflect.TypeToken;
import sfiomn.legendarycreatures.config.json.JsonSpawnInfo;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class JsonTypeToken
{
	public static Type get()
	{
		return new TypeToken<Map<String, JsonSpawnInfo>>(){}.getType();
	}
}
