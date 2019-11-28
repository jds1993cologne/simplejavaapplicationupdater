package de.systeme.system.simplejavaapplicationupdater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class SimpleJavaApplicationUpdater {
	
	public static void updateProceder(UpdateableClass mainClass, URL link, String actualVersion, Proxy proxy) {

		mainClass.startVersionCheck();
		
		String actualAvaiableVersion = "";
		
		try {
			actualAvaiableVersion = getActualVersionFromURL(link, proxy);
		} catch (IOException ioe) {
			
			mainClass.actualVersionCheckFailed();
			
			return;
			
		}
		
		if(versionCompare(actualAvaiableVersion, actualVersion) < 0) {

			//TODO Hier Quelltext einfügen
			
		} else {

			mainClass.actualVersionInstalled();
			
			return;
				
		}
		
	}
	
	public static void updateProceder(UpdateableClass mainClass, URL link, String actualVersion) {
		updateProceder(mainClass, link, actualVersion, null);
	}

	protected static String getActualVersionFromURL(URL link, Proxy proxy) throws IOException {
		
		URLConnection connection;
		URL website = new URL(link.toString() + "actualversion.txt");
		
		if(proxy == null){
			connection = website.openConnection();
		} else {
			connection = website.openConnection(proxy);
		}
		
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		StringBuilder response = new StringBuilder();
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);

		in.close();

		return response.toString();
		
	}
	
	/**
	 * Compares two version strings. 
	 * 
	 * Use this instead of String.compareTo() for a non-lexicographical 
	 * comparison that works for version strings. e.g. "1.10".compareTo("1.6").
	 * 
	 * @note It does not work if "1.10" is supposed to be equal to "1.10.0".
	 * 
	 * @param str1 a string of ordinal numbers separated by decimal points. 
	 * @param str2 a string of ordinal numbers separated by decimal points.
	 * @return The result is a negative integer if str1 is _numerically_ less than str2. 
	 *         The result is a positive integer if str1 is _numerically_ greater than str2. 
	 *         The result is zero if the strings are _numerically_ equal.
	 */
	protected static int versionCompare(String str1, String str2) {
		
	    String[] vals1 = str1.split("\\.");
	    String[] vals2 = str2.split("\\.");
	    int i = 0;
	    // set index to first non-equal ordinal or length of shortest version string
	    while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
	      i++;
	    }
	    // compare first non-equal ordinal number
	    if (i < vals1.length && i < vals2.length) {
	        int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
	        return Integer.signum(diff);
	    }
	    // the strings are equal or one string is a substring of the other
	    // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
	    return Integer.signum(vals1.length - vals2.length);
	    
	}

}
