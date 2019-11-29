package de.systeme.system.simplejavaapplicationupdater;

import java.awt.Component;
import java.io.IOException;
import java.net.Proxy;
import java.net.URL;

import javax.swing.JOptionPane;

public class SimpleJavaApplicationUpdaterUI extends SimpleJavaApplicationUpdater {

	/**
	 * checks wheather a newer version is avaiable and downloads it as second jar, if a newer version is avaiable
	 * 
	 * @param mainFrame		the parent component
	 * @param link			the link to the folder on the server
	 * @param jarName		the name of the jar file
	 * @param actualVersion	the actual version of the software  
	 * @param proxy			the proxy for connection
	 */
	public static void updateProceder(Component parentComponent, URL link, String jarName, String actualVersion, Proxy proxy) {
		
		UpdaterProgressMonitor progressMonitor = new UpdaterProgressMonitor(parentComponent, "Check actual avaiable version");

		String actualAvaiableVersion = "";
		
		try {
			actualAvaiableVersion = getActualVersionFromURL(link, proxy);
		} catch (IOException ioe) {

			progressMonitor.dismiss();
			
			JOptionPane.showMessageDialog(parentComponent, "Could not load actual version", "Error", JOptionPane.ERROR_MESSAGE);
			
			return;
			
		}
		
		if(versionCompare(actualAvaiableVersion, actualVersion) < 0) {

			try {
				
				progressMonitor.setText("Downloading new Version");
				
				downloadFile(new URL(link.toString() + jarName + "V" + actualAvaiableVersion + ".jar"), jarName + "2.jar", proxy);
				
				JOptionPane.showMessageDialog(parentComponent, "Succesful downloaded the new version", "Updater", JOptionPane.INFORMATION_MESSAGE);
				
			} catch (IOException e) {

				progressMonitor.dismiss();
				
				JOptionPane.showMessageDialog(parentComponent, "Download of the new version failed", "Error", JOptionPane.ERROR_MESSAGE);
				
				e.printStackTrace();
				
			}
			
		} else {

			progressMonitor.dismiss();
			
			JOptionPane.showMessageDialog(parentComponent, "The installed version is the newest", "Updater", JOptionPane.INFORMATION_MESSAGE);
				
		}
		
	}
	
	/**
	 * checks wheather a newer version is avaiable and downloads it as second jar, if a newer version is avaiable
	 * 
	 * @param mainFrame		the parent component
	 * @param link			the link to the folder on the server
	 * @param jarName		the name of the jar file
	 * @param actualVersion	the actual version of the software 
	 */
	public static void updateProceder(Component parentComponent, URL link, String jarName, String actualVersion) {
		updateProceder(parentComponent, link, jarName, actualVersion, null);
	}
	
}
