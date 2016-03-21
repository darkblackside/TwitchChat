package dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import exceptions.SettingNotInitializedException;

public class Authentication
{
	public static Map<String, String> getMightyAuthenticationHeader() throws SettingNotInitializedException, ClassNotFoundException, IOException
	{
		Map<String, String> result = new HashMap<String, String>();
		
		Object mightyoauth = DefaultSettingsDAO.getInstance().getSettings().getSetting("mightyoauthkey");
		
		if(mightyoauth != null)
		{
			result.put("Authorization", "OAuth " + mightyoauth.toString());
		}
		else
		{
			throw new SettingNotInitializedException("mightyoauthkey");
		}
		
		return result;
	}
}
