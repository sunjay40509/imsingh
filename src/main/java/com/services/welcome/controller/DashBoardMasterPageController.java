package com.services.welcome.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import org.apache.log4j.Logger;

import com.services.common.utils.Controller;
import com.services.common.utils.FXMLUtils;
import com.services.controller.book.BookMasterPageConroller;
import com.services.model.User;
import com.services.ui.SuperScreen;

/**
 * This is Main Controller Class 
 * 1. User Module
 * 2. Book Module
 */
@Controller
public class DashBoardMasterPageController extends SuperScreen {
	
	private Logger logger = Logger.getLogger(DashBoardMasterPageController.class);

	// ======================= FXML Variable
	@FXML
	private AnchorPane root;
	
	@FXML
	private VBox mainContainerVBox;

	// ======================= Internal Variable
	private User currentUser;

	// ======================= FXML Controller
	private MasterPageConroller masterPageConroller;

	// ======================= FXML Method

	@FXML
	private void logoutButton_OnAction() {
		try {
			LoginAppAuthMainConroller loginAppAuthMainConroller = FXMLUtils
					.getController(LoginAppAuthMainConroller.class);
			loginAppAuthMainConroller
					.configureAndLoadController(masterPageConroller);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@FXML
	private void userRegistryButton_OnAction() {
		try {
			UserMasterPageConroller userMasterPageConroller = FXMLUtils
					.getController(UserMasterPageConroller.class);
			
			userMasterPageConroller.configureAndLoadConroller(masterPageConroller);
//			userMasterPageConroller.loadUserDetailIntoTable();
			mainContainerVBox.getChildren().clear();
			mainContainerVBox.getChildren().add(userMasterPageConroller.getScreen());
		} catch(Exception exception) {
			logger.error(exception.getMessage(), exception);
		}
	}
	
	@FXML
	private void bookSearchButton_OnAction() {
		try {
			BookMasterPageConroller bookMasterPageConroller = FXMLUtils
					.getController(BookMasterPageConroller.class);
			
			bookMasterPageConroller.configureAndLoadConroller(masterPageConroller);
//			userMasterPageConroller.loadUserDetailIntoTable();
			mainContainerVBox.getChildren().clear();
			mainContainerVBox.getChildren().add(bookMasterPageConroller.getScreen());
		} catch(Exception exception) {
			logger.error(exception.getMessage(), exception);
		}
	}
	
	// ======================= Internal Method

	@Override
	public Node getScreen() {
		return super.getScreen();
	}

	public void configureAndLoadController(
			MasterPageConroller masterPageConroller, User currentUser) {
		this.masterPageConroller = masterPageConroller;
		this.currentUser = currentUser;
		reloadMyScreen();
	}

	private void reloadMyScreen() {
		setScreenToMasterPage();
	}

	private void setScreenToMasterPage() {
		masterPageConroller.setMainContent(getScreen());
	}

}
