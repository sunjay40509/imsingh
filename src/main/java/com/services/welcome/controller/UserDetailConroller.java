package com.services.welcome.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import org.apache.log4j.Logger;

import com.services.common.utils.Controller;
import com.services.common.utils.FXMLUtils;
import com.services.model.User;
import com.services.ui.SuperScreen;

@Controller
public class UserDetailConroller extends SuperScreen {

	private Logger logger = Logger.getLogger(UserDetailConroller.class);

	// ============================= FXML Variable
	@FXML
	private AnchorPane root;
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label userNameLabel;
	@FXML
	private Label emailIdLabel;

	// ============================= Internal Variable
	private User currentUser;

	// ============================= FXML Controller
	private MasterPageConroller masterPageConroller;

	// ============================= Initialize Method
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	// ============================= FXML Method
	@FXML
	private void logoutButton_OnAction() {
		try {
			LoginAppAuthMainConroller loginAppAuthMainConroller = FXMLUtils
					.getController(LoginAppAuthMainConroller.class);
			loginAppAuthMainConroller.configureAndLoadController(masterPageConroller);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	// ============================= Internal Method
	private void setDataToFxml() {
		if(currentUser == null) {
			return;
		}
		firstNameLabel.setText(currentUser.getFirstName());
		lastNameLabel.setText(currentUser.getLastName());
		userNameLabel.setText(currentUser.getUserName());
		emailIdLabel.setText(currentUser.getEmaiId());
	}

	// ============================= Get Screen
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
		setDataToFxml();
	}

}
