package fr.eyzox.minecraftPattern.gui.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class Config {
	private static final File config = new File("config.properties");

	public final static String site = "https://github.com/EyZox/MinecraftPatternGUI";
	
	@SuppressWarnings("serial")
	private Map<String,String> defaultValue = new TreeMap<String,String>() {
		{
			put("resolution-x","800");
			put("resolution-y","600");
		}
	};
	
	public Properties loadConfig() {
		Properties prop = new Properties();
		OutputStream output = null;
		if(!config.exists()) {
			try {
				output = new FileOutputStream(config);
				for(String key : defaultValue.keySet()) {
					prop.setProperty(key, defaultValue.get(key));
				}
				prop.store(output, null);
			} catch (IOException io) {
				io.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}else {
			InputStream input = null; 
			try {
				input = new FileInputStream(config);
				// load a properties file
				prop.load(input);
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			//Check Config :
			boolean change = false;
			for(String key : defaultValue.keySet()) {
				if(prop.get(key) == null) {
					prop.setProperty(key, defaultValue.get(key));
					change = true;
				}
			}
			if(change) {
				saveProp(prop);
			}
		}
		return prop;
	}
	
	public String getDefaultValue(String key) {
		return defaultValue.get(key);
	}
	
	public Map<String,String> getDefaultValues() {
		return defaultValue;
	}
	
	public static void saveProp(Properties prop) {
		OutputStream output = null;;
		try {
			output = new FileOutputStream(config);
			prop.store(output, null);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
