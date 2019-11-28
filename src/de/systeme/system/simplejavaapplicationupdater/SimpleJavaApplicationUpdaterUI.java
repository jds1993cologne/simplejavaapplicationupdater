package de.systeme.system.simplejavaapplicationupdater;

import java.awt.Frame;
import java.io.IOException;
import java.net.Proxy;
import java.net.URL;

public class SimpleJavaApplicationUpdaterUI extends SimpleJavaApplicationUpdater {

	public static void updateProceder(Frame mainFrame, URL link, String actualVersion, Proxy proxy) {
		
		//TODO Hier Quelltext ändern
		System.out.println("Check actual avaiable version");
		
		String actualAvaiableVersion = "";
		
		try {
			actualAvaiableVersion = getActualVersionFromURL(link, proxy);
		} catch (IOException ioe) {

			//TODO Hier Quelltext ändern
			System.out.println("Could not load actual version");
			
			return;
			
		}
		
		if(versionCompare(actualAvaiableVersion, actualVersion) < 0) {

			//TODO Hier Quelltext einfügen
			
		} else {

			//TODO Hier Quelltext ändern
			System.out.println("The installed version is the newest");
			
			return;
				
		}
		
	}
	
	public static void updateProceder(Frame mainFrame, URL link, String actualVersion) {
		updateProceder(mainFrame, link, actualVersion, null);
	}
	
}
