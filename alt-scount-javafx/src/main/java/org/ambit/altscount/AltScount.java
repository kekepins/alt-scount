package org.ambit.altscount;

import java.io.IOException;

import org.ambit.altscount.controller.AltScountController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AltScount extends Application {
	
	private Stage primaryStage;
	private SplitPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AltScount");

		initRootLayout();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	  /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AltScount.class.getResource("view/RootLayout.fxml"));
            rootLayout = (SplitPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            this.primaryStage.getIcons().add(new Image (this.getClass().getResourceAsStream("/logo3.png")));
            primaryStage.setScene(scene);
            primaryStage.show();
            
            AltScountController controller =  loader.getController();
            controller.verifySyncDir();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
