package de.systeme.system.simplejavaapplicationupdater;

public interface UpdateableClass {

	public void startVersionCheck();

	public void actualVersionCheckFailed();

	public void actualVersionInstalled();
	
}
