package app.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;


public class Configuration
{
	private static final HashMap<String, String> configuration = new HashMap<String, String>();
	

	public static void initialize()
	{
		Properties properties = new Properties();
		
	    try {
	    	for (String configType : new String[] {"defaults", "current"})
	    	{
	    		for (String configFile : new String[] {"general", "database", "color"})
				{
					properties.load(Configuration.class.getResourceAsStream("/config/" + configType + "/" + configFile + ".properties"));
					for (final Entry<Object, Object> entry : properties.entrySet()) {
					    configuration.put((String) entry.getKey(), (String) entry.getValue());
					}
				}
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static String get(String key)
	{
		String value = configuration.get(key);
		if (value == null)
		{
			throw new IllegalArgumentException("no such configuration key '" + key + "'");
		}
		return value;
	}
	

	public static int getInt(String key)
	{
		try
		{
			return Integer.parseInt(get(key));
		}
		catch (NumberFormatException e)
		{
			throw new IllegalArgumentException("no such configuration key '" + key + "' as integer");
		}
	}
}
