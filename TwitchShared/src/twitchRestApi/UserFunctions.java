package twitchRestApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.json.JSONArray;
import org.json.JSONObject;

import models.UserState;

public class UserFunctions
{
	public static final String getAllChattersAdress = "http://tmi.twitch.tv/group/user/<<<username>>>/chatters";
	
	
	private String sendRequest(String request) throws IOException
	{		
	    URL url = new URL(request); 
	    
	    HttpURLConnection connection = getConnection(url);
	    
	    String buffer = "";
	    try( BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
	    	while(reader.ready())
	    	{
		    	buffer = buffer + reader.readLine();
	    	}
		}
	    connection.disconnect();
	    
	    return buffer;
	}
	
	public Map<String, UserState> getChatUsers(String channelname) throws IOException
	{
		Map<String, UserState> result = new HashMap<String, UserState>();
		
		String request = getAllChattersAdress;
		request = request.replace("<<<username>>>", channelname);
		
		String buffer = sendRequest(request);
		
		try
		{
		    JSONObject json = new JSONObject(buffer);
		    
		    List<String> categoriesList = new ArrayList<String>();
		    Iterator<String> chatterCategories = json.getJSONObject("chatters").keys();
		    
		    do
		    {
		    	try
		    	{
		    		categoriesList.add(chatterCategories.next());
		    	}
		    	catch(NoSuchElementException e)
		    	{
		    		break;
		    	}
		    }
		    while(chatterCategories.hasNext());
		    
		    for(String category:categoriesList)
		    {
		    	UserState state;
		    	
		    	switch(category)
		    	{
			    	case "moderators": state = UserState.Moderator; break;
			    	case "staff": state = UserState.Staff; break;
			    	case "admins": state = UserState.Admin; break;
			    	case "global_mods": state = UserState.GlobalModerator; break;
			    	default: state = UserState.Viewer; break;
		    	}
		    	
		    	JSONArray usersList = json.getJSONObject("chatters").getJSONArray(category);
		    	
		    	for(Object object:usersList)
		    	{
		    		result.put(object.toString(), state);
		    	}
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Error while calling twitch API");
		}
		
	    return result;
	}


	private HttpURLConnection getConnection(URL url) throws IOException {

	    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
	    connection.setDoOutput(true); 
	    connection.setInstanceFollowRedirects(false); 
	    connection.setRequestMethod("GET"); 
	    connection.setRequestProperty("Content-Type", "text/plain"); 
	    connection.setRequestProperty("charset", "utf-8");
	    connection.connect();
	    
	    return connection;
	}
}