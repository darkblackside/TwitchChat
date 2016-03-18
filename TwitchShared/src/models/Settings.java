package models;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Settings {
	private Map<String, Object> settings;
	
	public Settings()
	{
		settings = new HashMap<String, Object>();
	}
	
	public Settings(HashMap<String, Object> readObject)
	{
		settings = readObject;
	}

	public Map<String, Object> getSettings()
	{
		Map<String, Object> settingsCopy = new HashMap<String, Object>();
		
		for(Entry<String, Object> entry:settings.entrySet())
		{
			settingsCopy.put(entry.getKey(), entry.getValue());
		}
		
		return settingsCopy;
	}
	
	public Object getSetting(String key)
	{
		return settings.get(key);
	}
	
	public void removeSetting(String key)
	{
		settings.remove(key);
	}
	
	public void updateSetting(String key, Object value)
	{
		settings.remove(key);
		settings.put(key, value);
	}
	
	public void addSetting(String key, Object value)
	{
		settings.put(key, value);
	}
}
