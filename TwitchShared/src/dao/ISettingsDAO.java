package dao;

import java.io.IOException;

import models.Settings;

public interface ISettingsDAO
{
	public void saveSettings(Settings s) throws IOException;
	public Settings getSettings(boolean forceload) throws ClassNotFoundException, IOException;
	public Settings getSettings() throws ClassNotFoundException, IOException;
}
