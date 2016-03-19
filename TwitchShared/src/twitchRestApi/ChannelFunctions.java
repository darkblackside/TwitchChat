package twitchRestApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.json.JSONObject;

import twitchModels.Channel;

public class ChannelFunctions
{
	public static final String getChannelAdress = "https://api.twitch.tv/kraken/channels/<<<username>>>";
	
	
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
	
	public Channel getChannelInformation(String channelname) throws IOException
	{
		Channel result = new Channel();
		
		String request = getChannelAdress;
		request = request.replace("<<<username>>>", channelname);
		
		String buffer = sendRequest(request);
		
		try
		{
		    JSONObject json = new JSONObject(buffer);
		    
		    result.setMature(json.getBoolean("mature"));
		    result.setStatus(json.getString("status"));
		    result.setBroadcasterLanguage(json.getString("broadcaster_language"));
		    result.setDisplayName(json.getString("display_name"));
		    result.setGame(json.getString("game"));
		    result.setLanguage(json.getString("language"));
		    result.setName(json.getString("name"));
		    result.setCreatedAt(stringToDate(json.getString("created_at")));
		    result.setUpdatedAt(stringToDate(json.getString("updated_at")));
		    //result.setDelay(json.getString("delay"));
		    //result.setLogo(json.getString("logo"));
		    //result.setBanner(json.getString("banner"));
		    result.setVideoBanner(json.getString("video_banner"));
		    //result.setBackground(json.getString("background"));
		    result.setProfileBanner(json.getString("profile_banner"));
		    result.setProfileBannerBackgroundColor(json.getString("profile_banner_background_color"));
		    result.setViews(json.getInt("views"));
		    result.setFollowers(json.getInt("followers"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Error while calling twitch API");
			throw new IOException();
		}
		
	    return result;
	}


	private Date stringToDate(String string) {		
		Date result = new Date();
			int year = Integer.parseInt(string.substring(0, 3));
			int month = Integer.parseInt(string.substring(5, 6));
			int day = Integer.parseInt(string.substring(8, 9));
			int hours = Integer.parseInt(string.substring(11, 12));
			int minutes = Integer.parseInt(string.substring(14, 15));
			int seconds = Integer.parseInt(string.substring(17, 18));
			
		result = new Date(year, month, day, hours, minutes, seconds);		
			
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
