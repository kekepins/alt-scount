package org.ambit.pref;

import java.io.File;
import java.util.prefs.Preferences;

public class AltScountPreferences {
	
	private static final String PREF_SYNCHRO_DIR = "syncho.dir";
	
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
	
	public void saveSynchoDir(File dir) {
		if ( dir != null ) {
			ambitPrefs.put(PREF_SYNCHRO_DIR, dir.getAbsolutePath());
		}
	}
	
	
	public static AltScountPreferences getPreference() {
		if ( instance == null ) {
			instance = new AltScountPreferences();
		}
		
		return instance;
	}
	

}
