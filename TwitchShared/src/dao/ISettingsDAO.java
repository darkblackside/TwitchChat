package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import models.Settings;

public interface ISettingsDAO
{
	public void saveSettings(Settings s, String path) throws FileNotFoundException, IOException;
	public void saveSettingsList(List<Settings> s, String path) throws IOException;
	public List<Settings> loadSettingsList(String path) throws IOException, ClassNotFoundException;
	Settings loadSettings(String path) throws IOException, ClassNotFoundException;
}
