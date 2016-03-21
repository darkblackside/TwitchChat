package twitchRestApi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.AuthTokenNotFoundException;

public class APIKeyConnection
{
	public static String chatUrl = "https://api.twitch.tv/kraken/oauth2/authorize?response_type=token&client_id=eoiygvgy4rlmtlgu58nt83sr8w4z82h&redirect_uri=http://localhost&scope=chat_login";
	public static String editUrl = "https://api.twitch.tv/kraken/oauth2/authorize?response_type=token&client_id=eoiygvgy4rlmtlgu58nt83sr8w4z82h&redirect_uri=http://localhost&scope=chat_login+channel_editor+channel_read+channel_commercial+channel_subscriptions+user_read+user_blocks_edit+user_blocks_read";

	public static String getAuthToken(String returnUrl) throws AuthTokenNotFoundException
	{
		//Format http://localhost/#access_token=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx&scope=chat_login+channel_editor+channel_read+channel_commercial+channel_subscriptions+user_read+user_blocks_edit+user_blocks_read
		Pattern pattern = Pattern.compile("#access_token=(.*?)&scope=");
		Matcher matcher = pattern.matcher(returnUrl);
		if (matcher.find())
		{
		    return matcher.group(0).replace("#access_token=", "").replace("&scope=", "");
		}
		else
		{
			throw new AuthTokenNotFoundException();
		}
	}
}
