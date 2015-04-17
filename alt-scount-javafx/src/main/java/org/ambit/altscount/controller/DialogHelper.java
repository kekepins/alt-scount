package org.ambit.altscount.controller;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import org.ambit.pref.AltScountPreferences;
import org.controlsfx.control.ButtonBar.ButtonType;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogAction;

public class DialogHelper {
	
	public static void displayDialogPreferences(Stage stage) {
		
		final TextField movescountEmail = new TextField();
		final TextField ambitSerial 	= new TextField();
		final TextField userKey		 	= new TextField();
		final TextField proxyHost	 	= new TextField();
		final TextField proxyPort	 	= new TextField();

		
		Action actionSave = 
				new DialogAction(
						"Save", 
						ButtonType.OK_DONE, 
						name -> 
						savePrefs(movescountEmail.getText(), 
								  ambitSerial.getText(), 
								  userKey.getText(),
								  proxyHost.getText(),
								  proxyPort.getText()));
		 
		Dialog dlg = new Dialog( stage, "Preferences");
		dlg.setMasthead("Modify AltScount preferences");
		 
		final GridPane content = new GridPane();
		content.setHgap(10);
		content.setVgap(10);
		
		AltScountPreferences preferences = AltScountPreferences.getPreference();
		 
		// email
		content.add(new Label("Movescount email"), 0, 0);
		movescountEmail.setText(preferences.getMovescountEmail());
		content.add(movescountEmail, 1, 0);
		
		// Serial
		content.add(new Label("Ambit Serial"), 0, 1);
		ambitSerial.setText(preferences.getAmbitSerial());
		content.add(ambitSerial, 1, 1);
		
		// User key
		content.add(new Label("User Key"), 0, 2);
		userKey.setText(preferences.getUserKey());
		content.add(userKey, 1, 2);
		
		// Proxy host
		content.add(new Label("Proxy host"), 0, 3);
		proxyHost.setText(preferences.getProxyHost());
		content.add(proxyHost, 1, 3);
		
		// Proxy port
		content.add(new Label("Proxy Port"), 0, 4);
		proxyPort.setText(preferences.getProxyPort());
		content.add(proxyPort, 1, 4);
		
		dlg.setResizable(false);
		dlg.setIconifiable(false);
		dlg.setContent(content);
		dlg.getActions().addAll(actionSave, Dialog.ACTION_CANCEL);
		 
		Action response = dlg.show();
		System.out.println("response: " + response);
	}
	
	private static void savePrefs(String email, String ambitSerial, String userKey, String proxyHost, String proxyPort ) {
		
		System.out.println("save pref");
		AltScountPreferences preferences = AltScountPreferences.getPreference();
		preferences.saveMovescountEmail(email);
		preferences.saveAmbitSerial(ambitSerial);
		preferences.saveUserKey(userKey);
		preferences.saveProxyHost(proxyHost);
		preferences.saveProxyPort(proxyPort);
	}
}
