package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import models.ISettingsChangedListener;
import models.Settings;

public class DefaultSettingsDAO implements ISettingsDAO
{
	public static final String path = "settings.ser";
	
	private List<ISettingsChangedListener> listeners;
	private static DefaultSettingsDAO self;
	private Settings cache;
	
	private DefaultSettingsDAO()
	{
		listeners = new ArrayList<ISettingsChangedListener>();
	}
	
	public static DefaultSettingsDAO getInstance()
	{
		if(self == null)
		{
			self = new DefaultSettingsDAO();
		}
		return self;
	}
	
	@Override
	public void saveSettings(Settings s) throws IOException
	{		
		FileOutputStream fos = new FileOutputStream(path);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(s.getSettings());
		oos.close();

		settingsChanged();
	}
	
	@Override
	public Settings getSettings(boolean forceload) throws ClassNotFoundException, IOException
	{
		if(forceload)
		{
			cache = null;
		}
		
		if(cache == null)
		{
			cache = loadSettings();
		}
		
		return cache;
	}
	
	public void deleteSettings(boolean confirm)
	{
		if(confirm)
		{
			new File(path).delete();
		}
	}
	
	public void addSettingsChangedListener(ISettingsChangedListener listener)
	{
		listeners.add(listener);
	}
	
	private void settingsChanged()
	{
		cache = null;
		
		for(ISettingsChangedListener listener:listeners)
		{
			listener.notifySettingsChanged();
		}
	}

	private Settings loadSettings() throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(path);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		@SuppressWarnings("unchecked")
		Settings s = new Settings((HashMap<String, Object>) ois.readObject());
		
		ois.close();
		
		return s;
	}

	public boolean settingsExist()
	{
		if(new File(path).exists())
		{
			return true;
		}
		return false;
	}

	@Override
	public Settings getSettings() throws ClassNotFoundException, IOException
	{
		return getSettings(false);
	}
}
