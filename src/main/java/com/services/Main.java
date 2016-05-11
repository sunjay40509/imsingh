package com.services;

import java.awt.Frame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

import com.services.common.utils.FXMLUtils;
import com.services.image.IMAGE;
import com.services.style.CSS;
import com.services.welcome.controller.MasterPageConroller;

/**
 * This is main class of library management project run.
 *
 */
public class Main extends Application {

	public static Stage TEMP_STAGE;
	public static BorderPane PRIMARTBORDERPANE;

	// splash size
	@Override
	public void start(Stage firstStage) {

		// Check Java Version If Current Installed Java Version is 
		// Not 8 then application give error message.
		if (!System.getProperty("java.version").startsWith("1.8")) {
			Frame frame = new Frame();
			JOptionPane.showMessageDialog(frame, "Java Version is not compatible. Upgrade Java to 1.8.0-ea-b109.",
					"Version error", JOptionPane.ERROR_MESSAGE);
			Platform.exit();
			System.exit(0);
		}
		
		PRIMARTBORDERPANE = new BorderPane();
		try {
			MasterPageConroller masterPageConroller  = FXMLUtils.
					getController(MasterPageConroller.class);
			AnchorPane anchorPane = (AnchorPane) masterPageConroller.getScreen();
			masterPageConroller.loadLoginScreen();
			PRIMARTBORDERPANE.setCenter(anchorPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
		TEMP_STAGE = firstStage;

		Scene scene = new Scene(PRIMARTBORDERPANE, Color.TRANSPARENT);

		scene.getStylesheets().addAll(CSS.getStyleSheet("flip.css"));
		TEMP_STAGE.setTitle("Library Management Information System");
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		TEMP_STAGE.setHeight(screenBounds.getHeight());
		TEMP_STAGE.setWidth(screenBounds.getWidth());
		//TEMP_STAGE.setResizable(false);
		TEMP_STAGE.getIcons().add(IMAGE.getImage("cd.png"));
		TEMP_STAGE.setScene(scene);
		TEMP_STAGE.centerOnScreen();
		TEMP_STAGE.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
