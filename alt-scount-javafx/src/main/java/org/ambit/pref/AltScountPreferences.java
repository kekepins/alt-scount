package org.ambit.pref;

import java.io.File;
import java.util.prefs.Preferences;

public class AltScountPreferences {
	
	private static final String PREF_SYNCHRO_DIR 		= "synchro.dir";
	private static final String PREF_MOVESCOUNT_EMAIL 	= "movescount.email";
	private static final String PREF_AMBIT_SERIAL 		= "ambit.serial";
	private static final String PREF_USER_KEY 			= "user.key";
	private static final String PREF_PROXY_HOST			= "proxy.host";
	private static final String PREF_PROXY_PORT			= "proxy.port";
		
	
	private final Preferences ambitPrefs;
	
	private static AltScountPreferences instance;
	
	private AltScountPreferences() {
		Preferences prefsRoot = Preferences.userRoot();
		ambitPrefs = prefsRoot
	            .node("org.ambit.altscount");
	}
	
	public File getSynchroDir() {
		
		String dirStr = ambitPrefs.get(PREF_SYNCHRO_DIR, null);
		
		if ( dirStr != null ) {
			return new File(dirStr);
		}
		
		
		return null;
	}
	
	public String getMovescountEmail() {
		return ambitPrefs.get(PREF_MOVESCOUNT_EMAIL, null);
	}
	
	public String getAmbitSerial() {
		return ambitPrefs.get(PREF_AMBIT_SERIAL, null);
	}
	
	public String getUserKey() {
		return ambitPrefs.get(PREF_USER_KEY, null);
	}
	
	public void saveSynchoDir(File dir) {
		if ( dir != null ) {
			ambitPrefs.put(PREF_SYNCHRO_DIR, dir.getAbsolutePath());
		}
	}
	
	public void saveMovescountEmail(String email) {
		if ( email != null ) {
			ambitPrefs.put(PREF_MOVESCOUNT_EMAIL, email);
		}
	}
	
	public void saveAmbitSerial(String ambitSerial) {
		if ( ambitSerial != null ) {
			ambitPrefs.put(PREF_AMBIT_SERIAL, ambitSerial);
		}
	}
	
	public void saveUserKey(String userKey) {
		if ( userKey != null ) {
			ambitPrefs.put(PREF_USER_KEY, userKey);
		}
	}
	
	
	public static AltScountPreferences getPreference() {
		if ( instance == null ) {
			instance = new AltScountPreferences();
		}
		
		return instance;
	}

	public String getProxyHost() {
		return ambitPrefs.get(PREF_PROXY_HOST, null);
	}

	public String getProxyPort() {
		return ambitPrefs.get(PREF_PROXY_PORT, null);
	}
	
	public void saveProxyPort(String proxyPort) {
		if ( proxyPort != null ) {
			ambitPrefs.put(PREF_PROXY_PORT, proxyPort);
		}
	}

	public void saveProxyHost(String proxyHost) {
		if ( proxyHost != null ) {
			ambitPrefs.put(PREF_PROXY_HOST, proxyHost);
		}
	}

	

}
