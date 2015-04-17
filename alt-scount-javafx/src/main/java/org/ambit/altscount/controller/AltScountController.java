package org.ambit.altscount.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.ambit.data.AmbitInfo;
import org.ambit.data.AmbitModel;
import org.ambit.data.AmbitSettings;
import org.ambit.data.LogInfo;
import org.ambit.movescount.MovesScountService;
import org.ambit.movescount.model.AmbitDeviceInfo;
import org.ambit.movescount.model.POI;
import org.ambit.pref.AltScountPreferences;
import org.ambit.usb.UsbException;
import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.ButtonBar.ButtonType;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogAction;
import org.controlsfx.dialog.Dialogs;

import eu.hansolo.enzo.common.Section;
import eu.hansolo.enzo.gauge.SimpleGauge;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Alt scount controller 
 */
public class AltScountController implements Initializable{
	
	private AmbitManager ambitManager = new AmbitManager();
	
	private MovesScountService movescountService = new MovesScountService();
	
	private AltScountPreferences prefs;
	
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
	
	@FXML
	private TableColumn<LogInfo,String> colColor;
	
	@FXML
	private Tab tabPois;
	
	@FXML
	private TableView<POI> poisTableView;
	
	@FXML
	private ImageView ambitImgView;

	
	boolean isPoisInit = false;
	
	private Image okImg;
	
	private Image unsyncImg;
	
	// Model
	private List<LogInfo> moves;
	
	private List<POI> pois;
	
	private File exportDir;

	@FXML
	public void handleFindBtnAction(ActionEvent event) {
	   
		searchForAmbit();
	 }
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		prefs = AltScountPreferences.getPreference();
		exportDir = prefs.getSynchroDir();
		//exportDir = null;
		if ( exportDir != null ) {
			dirTextField.setText(exportDir.getAbsolutePath());
		}
		
		okImg = new Image("/icon/ok.png");
		
		unsyncImg = new Image("/icon/notsync.png");
			
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
		

		colColor.setCellValueFactory(new PropertyValueFactory<LogInfo, String>("completeName"));
		colColor.setCellFactory(column -> {
		    return new TableCell<LogInfo, String>() {
		    	@Override
		        protected void updateItem(String item, boolean empty) {
		    		super.updateItem(item, empty); 
		    		if ( !empty ) {
		    			
		    			if ( exportDir != null ) {
		    				File file = new File(exportDir, item +".gpx");
		    				if ( file.exists() ) {
		    					ImageView imageView = new ImageView(okImg);
								imageView.setPreserveRatio(true);
								imageView.setSmooth(true);
								setGraphic(imageView);
		    				}
		    				else {
		    					ImageView imageView = new ImageView(unsyncImg);
								imageView.setPreserveRatio(true);
								imageView.setSmooth(true);
								setGraphic(imageView);
		    				}
		    			}

		    		}
		    	}
		    };
		});
		
		tabPois.setOnSelectionChanged(e -> handleTabPoisChanged());
		
		//  Is there any device connected ?
		searchForAmbit();
		
	}
	



	/**
	 * Tab pois selected 
	 */
	private void handleTabPoisChanged() {
		
		if ( isPoisInit ) {
			return;
		}
		
		if  (tabPois.isSelected() ) {
			System.out.println("Selected");
			
			String email = this.prefs.getMovescountEmail();
			
			if ( email == null ) {
				// ask user
				TextInputDialog dialog = new TextInputDialog("@");
				dialog.setTitle("Movescount email ?");
				dialog.setHeaderText("Movescount email is needed to call movescount");
				dialog.setContentText("Email:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();

				// The Java 8 way to get the response value (with lambda expression).
				result.ifPresent( 
					name -> {
						prefs.saveMovescountEmail(name);
						});
				
				email = this.prefs.getMovescountEmail();
				
			}
			
			String userKey = this.prefs.getUserKey();
			
			if ( userKey == null ) {
				TextInputDialog dialog = new TextInputDialog("");
				dialog.setTitle("Movescount user key ?");
				dialog.setHeaderText("Movescount user key is needed to call movescount");
				dialog.setContentText("user key:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();

				// The Java 8 way to get the response value (with lambda expression).
				result.ifPresent(
						name -> {
							prefs.saveUserKey(name);
						});
				userKey = this.prefs.getUserKey();

			}
			String deviceId =  this.prefs.getAmbitSerial();
			if ( email != null && userKey != null && deviceId != null) {
				AmbitDeviceInfo ambitDeviceInfo = movescountService.readAmbitDeviceInfo(deviceId, email, userKey);
				if (ambitDeviceInfo != null ) {
					pois = ambitDeviceInfo.getPois();
					
					if ( pois != null && pois.size() > 0) {
						poisTableView.setItems(FXCollections.observableArrayList(pois));
					}

				}
				
			}
		}

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
	                
	                @Override 
	                protected void succeeded() {
	                    super.succeeded();
	                    for (LogInfo logInfo : logsToExport ) {
	                    	logInfo.selectedProperty().set(false);
	                    }
	                    
	                    refreshListView();
	                    
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
		
		chooseSynchDirectory();

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
	
	private void searchForAmbit() {
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
				
				prefs.saveAmbitSerial(ambitInfo.getSerial());
				
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
	}
	
	private void chooseSynchDirectory() {
		Window window = root.getScene().getWindow();
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Choose a directory to persist Gps files");
		
		if ( exportDir != null ) {
			directoryChooser.setInitialDirectory(exportDir);
		}
		
		exportDir = directoryChooser.showDialog(window);
		
		if ( exportDir != null ) {
			dirTextField.setText(exportDir.getAbsolutePath());
			
			// Save in prefs
			prefs.saveSynchoDir(exportDir);
		}
	}
	
	public void verifySyncDir() {
		if ( exportDir == null) {
			chooseSynchDirectory();
		}

	}
	
	
	private void refreshListView() {

		System.out.println("Refresh List view");
		// Better solution ??
		if ( moves != null && moves.size() > 0) {
			moveTableView.setItems(null);
			moveTableView.layout();
			moveTableView.setItems(FXCollections.observableArrayList(moves));
		}

	}
	
	@FXML
	public void onImgClicked() {
		DialogHelper.displayDialogPreferences((Stage) root.getScene().getWindow());
	}
	
}
