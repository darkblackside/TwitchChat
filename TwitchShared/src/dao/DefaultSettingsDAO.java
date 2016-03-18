package dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import models.Settings;

public class DefaultSettingsDAO implements ISettingsDAO
{	
	@Override
	public void saveSettings(Settings s, String path) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(path);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(s.getSettings());
		oos.close();
	}

	@Override
	public void saveSettingsList(List<Settings> s, String path) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(path);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeInt(s.size());
		
		for(Settings settings:s)
		{
			oos.writeObject(settings.getSettings());
		}
		
		oos.close();
	}

	@Override
	public Settings loadSettings(String path) throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(path);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		@SuppressWarnings("unchecked")
		Settings s = new Settings((HashMap<String, Object>) ois.readObject());
		
		ois.close();
		
		return s;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Settings> loadSettingsList(String path) throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(path);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		int no = ois.readInt();
		List<Settings> s = new ArrayList<Settings>();
		
		for(int i = 0; i < no; i++)
		{
			s.add(new Settings((HashMap<String, Object>) ois.readObject()));
		}
		
		ois.close();
		
		return s;
	}

}
