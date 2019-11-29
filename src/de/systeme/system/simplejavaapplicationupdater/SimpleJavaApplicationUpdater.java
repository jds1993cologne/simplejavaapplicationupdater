package de.systeme.system.simplejavaapplicationupdater;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class SimpleJavaApplicationUpdater {
	
	/**
	 * checks wheather a newer version is avaiable and downloads it as second jar, if a newer version is avaiable
	 * 
	 * @param mainClass		the parent class
	 * @param link			the link to the folder on the server
	 * @param jarName		the name of the jar file
	 * @param actualVersion	the actual version of the software 
	 * @param proxy			the proxy for connection
	 */
	public static void updateProceder(UpdateableClass mainClass, URL link, String jarName, String actualVersion, Proxy proxy) {

		mainClass.startVersionCheck();
		
		String actualAvaiableVersion = "";
		
		try {
			actualAvaiableVersion = getActualVersionFromURL(link, proxy);
		} catch (IOException ioe) {
			
			mainClass.actualVersionCheckFailed();
			
			return;
			
		}
		
		if(versionCompare(actualAvaiableVersion, actualVersion) < 0) {

			try {
				
				downloadFile(new URL(link.toString() + jarName + "V" + actualAvaiableVersion + ".jar"), jarName + "2.jar", proxy);
				
				mainClass.downloadSucessfull();
				
			} catch (IOException e) {

				mainClass.downloadFailed();
				
				e.printStackTrace();
				
			}
			
		} else {
			mainClass.actualVersionInstalled();
		}
		
	}
	
	/**
	 * checks wheather a newer version is avaiable and downloads it as second jar, if a newer version is avaiable
	 * 
	 * @param mainClass		the parent class
	 * @param link			the link to the folder on the server
	 * @param jarName		the name of the jar file
	 * @param actualVersion	the actual version of the software 
	 */
	public static void updateProceder(UpdateableClass mainClass, URL link, String jarName, String actualVersion) {
		updateProceder(mainClass, link, jarName, actualVersion, null);
	}

	/**
	 * loads the actual version from the online file actualversion.txt
	 * 
	 * @param 	link		the url to the folder on the webspace
	 * @param 	proxy		if it is needed, the proxy for connection
	 * @return	String		the on the server saved actual version
	 * @throws 	IOException	thrown if the connection failed
	 */
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
	 * <u><b>Warning:</b> It does not work if "1.10" is supposed to be equal to "1.10.0".</u>
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

	/**
	 * load a file from an webserver and saves it
	 * 
	 * @param pathOnServer	url of the file
	 * @param pathToFile	place to save
	 * @param proxy			if it is needed, the proxy for connection
	 * @throws IOException	thrown if it fails
	 */
	protected static void downloadFile(URL pathOnServer, String pathToFile, Proxy proxy) throws IOException {

		FileOutputStream fos = new FileOutputStream(pathToFile);

		HttpURLConnection conn;

		if(proxy == null){
			conn = (HttpURLConnection) pathOnServer.openConnection();
		} else {
			conn = (HttpURLConnection) pathOnServer.openConnection(proxy);
		}
			
		conn.setRequestMethod("GET");

		conn.connect();

		byte tmp_buffer[] = new byte[4096];
		InputStream is = conn.getInputStream();
		int n;
			
		StringBuilder sb = new StringBuilder();

		while ((n = is.read(tmp_buffer)) > 0) {
		
			fos.write(tmp_buffer, 0, n);
			sb.append(tmp_buffer);
			fos.flush();

		}
			
		fos.close();

	}
	
}
