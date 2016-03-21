package twitchRestApi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.AuthenticationException;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.crypto.URIReferenceException;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.ReadNotOpenedException;
import exceptions.WriteNotOpenedException;

public class APIConnection
{	
	public static JSONObject getConnection(String url, Map<String, String> headerValues) throws URIReferenceException, AuthenticationException, MalformedURLException, IOException, JSONException, ReadNotOpenedException
	{
		URLConnection c = makeConnection(makeURL(url), HTTPRequestMethod.GET, headerValues);
		return new JSONObject(getContents(c));
	}
	
	public static JSONObject putConnection(String url, Map<String, String> headerValues, JSONObject write) throws URIReferenceException, AuthenticationException, IOException, WriteNotOpenedException, JSONException, ReadNotOpenedException
	{
		URLConnection c = makeConnection(makeURL(url), HTTPRequestMethod.GET, headerValues);
		writeContents(c, write.toString());
		return new JSONObject(getContents(c));
	}
	
	public static JSONObject deleteConnection(String url, Map<String, String> headerValues, JSONObject deleted) throws URIReferenceException, AuthenticationException
	{
		//Method Dummy
		return new JSONObject();
	}
	
	public static JSONObject postConnection(String url, Map<String, String> headerValues, JSONObject write) throws URIReferenceException, AuthenticationException
	{
		//Method Dummy
		return new JSONObject();
	}
	
	private static URL makeURL(String request) throws MalformedURLException
	{
		return new URL(request);
	}
	
	private static URLConnection makeConnection(URL request, HTTPRequestMethod method, Map<String, String> headers) throws IOException
	{
		HttpsURLConnection connection = (HttpsURLConnection) request.openConnection(); 
		
	    connection.setDoOutput(true); 
	    if(method == HTTPRequestMethod.POST || method == HTTPRequestMethod.PUT || method == HTTPRequestMethod.DELETE)
	    {
	    	connection.setDoInput(true);
	    }
	    
	    connection.setInstanceFollowRedirects(false);
	    connection.setRequestMethod(method.name()); 
	    connection.setRequestProperty("Content-Type", "application/json"); 
	    connection.setRequestProperty("charset", "utf-8");
	    connection.setRequestProperty("Accept", "application/vnd.twitchtv.v3+json");
	    
	    for(Entry<String, String> entry:headers.entrySet())
	    {
	    	connection.setRequestProperty(entry.getKey(), entry.getValue());
	    }
	    
	    return connection;	    
	}
	
	private static BufferedReader getBufferedReader(URLConnection c) throws IOException, ReadNotOpenedException
	{
		if(c.getDoOutput())
		{
			return new BufferedReader(new InputStreamReader(c.getInputStream()));
		}
		else
		{
			throw new ReadNotOpenedException();
		}
	}
	
	private static String getContents(URLConnection c) throws IOException, ReadNotOpenedException
	{
		String buffer = "";
		
		try(BufferedReader r = getBufferedReader(c))
		{
			while(r.ready())
			{
				buffer = buffer + r.readLine();
			}
		}
		
		return buffer;
	}
	
	private static BufferedWriter getBufferedWriter(URLConnection c) throws IOException, WriteNotOpenedException
	{
		if(c.getDoInput())
		{
			return new BufferedWriter(new OutputStreamWriter(c.getOutputStream()));
		}
		else
		{
			throw new WriteNotOpenedException();
		}
	}
	
	private static void writeContents(URLConnection c, String contents) throws IOException, WriteNotOpenedException
	{		
		try(BufferedWriter w = getBufferedWriter(c))
		{
			w.write(contents);
		}
	}
}
