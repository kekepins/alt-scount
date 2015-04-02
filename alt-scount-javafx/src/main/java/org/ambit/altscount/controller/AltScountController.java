package org.ambit.altscount.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.ambit.data.AmbitInfo;
import org.ambit.data.AmbitModel;
import org.ambit.data.AmbitSettings;
import org.ambit.data.LogInfo;
import org.ambit.usb.UsbException;
import org.controlsfx.dialog.Dialogs;

import eu.hansolo.enzo.common.Section;
import eu.hansolo.enzo.gauge.SimpleGauge;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Alt scount controller 
 */
public class AltScountController implements Initializable{
	
	private AmbitManager ambitManager = new AmbitManager();
	
	@FXML
	private Parent root;
	
	@FXML
	private Label statusTxt;

	@FXML
	private Label modelTxt;

	@FXML
	private Label serialTxt;
	
	@FXML
	private Label sexTxt;
	
	@FXML
	private Label birthYearTxt;

	@FXML
	private Label weightTxt;

	
	@FXML
	private SimpleGauge chargeGauge;
	
	@FXML
	private TableView<LogInfo> moveTableView;
	
	@FXML
	private TextField dirTextField;
	
	@FXML
	private TableColumn<LogInfo,Boolean> colCheck;
	
	// Model
	private List<LogInfo> moves;
	
	private File exportDir = new File("d:\\gps\\test\\");

	@FXML
	public void handleFindBtnAction(ActionEvent event) {
	    // Button was clicked, do something...
		
		if( !ambitManager.isConnected() ) {
			try {
				ambitManager.connectDevice();
				
				if ( !ambitManager.isConnected() ) {
					System.out.println("No device found");
					return;
				}
				
				statusTxt.setText("Connected");
				
				// Now get ambit info
				AmbitInfo ambitInfo = ambitManager.getDeviceInfo();
				AmbitModel ambitModel = ambitManager.getAmbitModel();
				
				if ( ambitModel != null ) {
					modelTxt.setText(ambitModel.getDescription() + "(" + ambitInfo.getModel() + ")");
				}
				serialTxt.setText(ambitInfo.getSerial());
				
				chargeGauge.setValue(ambitManager.getDeviceCharge());
				
				initMoves();
				
				initPersonalSettings();
			} 
			catch (UsbException e) {
				e.printStackTrace();
			}
		}
		
		// Look for device
	 }
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("initialize controller");
			
		chargeGauge.setSections(new Section(0, 10),
                new Section(10, 20),
                new Section(20, 30),
                new Section(30, 40),
                new Section(40, 50),
                new Section(50, 60),
                new Section(60, 70),
                new Section(70, 80),
                new Section(80, 90),
                new Section(90, 100));
		
		colCheck.setCellValueFactory(new PropertyValueFactory<LogInfo,Boolean>("selected"));
		colCheck.setCellFactory(column -> new CheckBoxTableCell<>()); 
		
	}
	
	@FXML
	public void handleExportBtnAction(ActionEvent event) {
		System.out.println("handleExportBtnAction");
		
		if ( moves != null && moves.size() > 0) {
			List<LogInfo> moveToExport = new ArrayList<LogInfo>();
			long maxStep = 0;
			for ( LogInfo move :  moves ) {
				if ( move.isSelected() ) {
					moveToExport.add(move);
					maxStep += move.getSampleCount();
				}
			}
			
			exportLoInfos(moveToExport, maxStep);
		}
	}
	
	private void exportLoInfos(List<LogInfo> logsToExport, long max) {
	
		Service<Void> service = new Service<Void>() {
	        @Override protected Task<Void> createTask() {
	            return new Task<Void>() {
	                @Override 
	                protected Void call() throws InterruptedException {
	                	
	                	ambitManager.setProgress(0);
	                	ambitManager.progressProperty().addListener(
                            (obs, oldProgress, newProgress) -> {
                            	System.out.println("listening: " + oldProgress + " to "+  newProgress + " max " + max);
                            	updateProgress(newProgress.longValue(), max);
                            }
	                    );
	                	long currentProgress = 0;
	                	for (LogInfo logInfo : logsToExport ) {
	                		updateMessage("Exporting log " + logInfo.getCompleteName());
	                		try {
								ambitManager.exportLog(logInfo, exportDir, currentProgress, max);
								currentProgress += logInfo.getSampleCount();
							} catch (Exception e) {
								e.printStackTrace();
							} 
	                	}
	                	
	                	return null;
	                	
	                }
	            };
	        }
	    };
 
	    Stage stage = (Stage) root.getScene().getWindow();
	    
	    Dialogs.create()
        	.owner(stage)
        	.title("Export to Gpx")
        	.masthead("Export in Progress")
        	.showWorkerProgress(service);

	    service.start();
	    
	}
	
	@FXML
	public void handleChangeDirBtnAction(ActionEvent event) {
		Window window = root.getScene().getWindow();
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		exportDir = directoryChooser.showDialog(window);
		
		if ( exportDir != null ) {
			dirTextField.setText(exportDir.getAbsolutePath());
		}
 
	}

	
	private void initMoves() throws UsbException {
		moves =  ambitManager.readMoveDescriptions();
		
		if ( moves != null && moves.size() > 0) {
			moveTableView.setItems(FXCollections.observableArrayList(moves));
		}
	}
	
	private void initPersonalSettings() throws UsbException {
		AmbitSettings settings = ambitManager.getSettings();
		sexTxt.setText(settings.getSexStr());
		birthYearTxt.setText("" + settings.getBirthYear());
		weightTxt.setText("" + settings.getWeight());

	}

}