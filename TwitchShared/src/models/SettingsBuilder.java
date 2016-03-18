package models;

import java.io.File;
import java.io.IOException;

import dao.DefaultSettingsDAO;
import exceptions.SettingsNotInitializedException;

public class SettingsBuilder
{
	public static final String path = "settings.ser";
	public static Settings settings;
	
	public static Settings getSettings() throws SettingsNotInitializedException
	{
		if(settings == null) throw new SettingsNotInitializedException();
		
		return settings;
	}
	
	public static void initialize(Settings defaultValue) throws ClassNotFoundException, IOException
	{
		settings = defaultValue;
		
		if(settingsFileExists())
		{
			settings = new DefaultSettingsDAO().loadSettings(path);
		}
		else
		{
			new DefaultSettingsDAO().saveSettings(settings, path);
		}
	}
	
	public static boolean settingsFileExists()
	{
		boolean result = false;
		result = new File(path).exists();
		return result;
	}

	public static void deleteConfig(boolean b)
	{
		if(b && settingsFileExists())
		{
			new File(path).delete();
		}
	}
}
