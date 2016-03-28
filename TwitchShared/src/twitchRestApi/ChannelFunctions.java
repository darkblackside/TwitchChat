package twitchRestApi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.xml.crypto.URIReferenceException;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.ChannelAPINotCallableException;
import exceptions.HttpRequestFailed;
import exceptions.JSONMalformedException;
import exceptions.ReadNotOpenedException;
import exceptions.WriteNotOpenedException;
import twitchModels.Channel;

public class ChannelFunctions
{
	public static final String getChannelAdress = "https://api.twitch.tv/kraken/channels/<<<username>>>";
	
	private JSONObject getGetRequest(String requestDestination, Map<String, String> headerValues) throws AuthenticationException, MalformedURLException, JSONException, URIReferenceException, IOException, ReadNotOpenedException, HttpRequestFailed, JSONMalformedException
	{		
		return APIConnection.getConnection(requestDestination, headerValues);
	}
	
	private JSONObject getPutRequest(String requestDestination, Map<String, String> headerValues, JSONObject toWrite) throws AuthenticationException, JSONException, URIReferenceException, IOException, WriteNotOpenedException, ReadNotOpenedException, HttpRequestFailed, JSONMalformedException
	{
		return APIConnection.putConnection(requestDestination, headerValues, toWrite);
	}
	
	public Channel getChannelInformation(String channelname, Map<String, String> headers) throws ChannelAPINotCallableException
	{
		Channel result = new Channel();
		
		String request = getChannelAdress;
		request = request.replace("<<<username>>>", channelname);
		
		try
		{
		    JSONObject json = getGetRequest(request, headers);
		    
		    result = jsonObjectToChannel(json);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Error while calling twitch API");
			throw new ChannelAPINotCallableException();
		}
		
	    return result;
	}
	
	public Channel updateChannelInformation(String channelname, Map<String, String> headers, Channel c) throws ChannelAPINotCallableException, HttpRequestFailed
	{
		JSONObject toWrite = new JSONObject();
	    JSONObject channelObject = new JSONObject();
	    
	    if(c.getStatus() != null)
	    {
		    channelObject.put("status", c.getStatus());
	    }
	    if(c.getGame() != null)
	    {
		    channelObject.put("game", c.getGame());
	    }
	    if(c.getBroadcasterLanguage() != null)
	    {
		    channelObject.put("broadcaster_language", c.getBroadcasterLanguage());
	    }
	    if(c.isMature() != null)
	    {
		    channelObject.put("mature", c.isMature());
	    }
	    if(c.getDelay() != null)
	    {
	    	if(c.isPartner())
	    	{
		    	channelObject.put("delay", c.getDelay());
	    	}
	    }
	    
	    toWrite.put("channel", channelObject);
	    
		String request = getChannelAdress;
		request = request.replace("<<<username>>>", channelname);
		
	    try
	    {
			return jsonObjectToChannel(getPutRequest(request, headers, toWrite));
		}
	    catch (AuthenticationException | JSONException | URIReferenceException | IOException | WriteNotOpenedException
				| ReadNotOpenedException | JSONMalformedException e)
	    {
			e.printStackTrace();
			throw new ChannelAPINotCallableException();
		}
	}


	private Channel jsonObjectToChannel(JSONObject json)
	{
		Channel result = new Channel();
		
		if(!json.get("mature").toString().equals("null"))
		{
		    result.setMature(json.getBoolean("mature"));
		}
		else
		{
			result.setMature(null);
		}
		if(!json.get("status").toString().equals("null"))
		{
		    result.setStatus(json.getString("status"));
		}
		else
		{
			result.setStatus(null);
		}
		if(!json.get("display_name").toString().equals("null"))
		{
		    result.setDisplayName(json.getString("display_name"));
		}
		else
		{
			result.setDisplayName(null);
		}
		if(!json.get("game").toString().equals("null"))
		{
		    result.setGame(json.getString("game"));
		}
		else
		{
			result.setGame(null);
		}
		if(!json.get("language").toString().equals("null"))
		{
		    result.setLanguage(json.getString("language"));
		}
		else
		{
			result.setLanguage(null);
		}
		if(!json.get("broadcaster_language").toString().equals("null"))
		{
		    result.setBroadcasterLanguage(json.getString("broadcaster_language"));
		}
		else
		{
			result.setBroadcasterLanguage(null);
		}
		if(!json.get("name").toString().equals("null"))
		{
		    result.setName(json.getString("name"));
		}
		else
		{
			result.setName(null);
		}
		if(!json.get("created_at").toString().equals("null"))
		{
		    result.setCreatedAt(stringToDate(json.getString("created_at")));
		}
		else
		{
			result.setCreatedAt(null);
		}
		if(!json.get("updated_at").toString().equals("null"))
		{
		    result.setUpdatedAt(stringToDate(json.getString("updated_at")));
		}
		else
		{
			result.setUpdatedAt(null);
		}
		if(!json.get("delay").toString().equals("null"))
		{
		    result.setDelay(json.getInt("delay"));
		}
		else
		{
			result.setDelay(null);
		}
		if(!json.get("logo").toString().equals("null"))
		{
		    result.setLogo(json.getString("logo"));
		}
		else
		{
			result.setLogo(null);
		}
		if(!json.get("banner").toString().equals("null"))
		{
		    result.setBanner(json.getString("banner"));
		}
		else
		{
			result.setBanner(null);
		}
		if(!json.get("video_banner").toString().equals("null"))
		{
		    result.setVideoBanner(json.getString("video_banner"));
		}
		else
		{
			result.setVideoBanner(null);
		}
		if(!json.get("background").toString().equals("null"))
		{
		    result.setBackground(json.getString("background"));
		}
		else
		{
			result.setBackground(null);
		}
		if(!json.get("profile_banner").toString().equals("null"))
		{
		    result.setProfileBanner(json.getString("profile_banner"));
		}
		else
		{
			result.setProfileBanner(null);
		}
		if(!json.get("profile_banner_background_color").toString().equals("null"))
		{
		    result.setProfileBannerBackgroundColor(json.getString("profile_banner_background_color"));
		}
		else
		{
			result.setProfileBannerBackgroundColor(null);
		}
		if(!json.get("views").toString().equals("null"))
		{
		    result.setViews(json.getInt("views"));
		}
		else
		{
			result.setViews(null);
		}
		if(!json.get("followers").toString().equals("null"))
		{
		    result.setFollowers(json.getInt("followers"));
		}
		else
		{
			result.setFollowers(null);
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
}
