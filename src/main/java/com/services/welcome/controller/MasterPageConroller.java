package com.services.welcome.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import org.apache.log4j.Logger;

import com.services.common.utils.Controller;
import com.services.common.utils.FXMLUtils;
import com.services.ui.SuperScreen;

@Controller
public class MasterPageConroller extends SuperScreen {

	private Logger logger = Logger.getLogger(MasterPageConroller.class);

	// ============================= FXML Variable
	@FXML
	private AnchorPane root;
	
	@FXML
	private VBox mainContainer;

	// ============================= Initialize Method
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	// ============================= Internal Method
	
	/**
	 * Load Login Screen Controller
	 */
		public void loadLoginScreen() {
			try {
				LoginAppAuthMainConroller loginAppAuthMainConroller = FXMLUtils.
						getController(LoginAppAuthMainConroller.class);
				loginAppAuthMainConroller.configureAndLoadController(this);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} 
		}
		
		public void setMainContent(Node node) {
			mainContainer.getChildren().clear();
			mainContainer.getChildren().add(node);
		}

	// ============================= Get Screen
	@Override
	public Node getScreen() {
		return super.getScreen();
	}
	
}
