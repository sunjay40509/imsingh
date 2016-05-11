package com.services.welcome.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import org.apache.log4j.Logger;

import com.services.common.dao.SuperDao;
import com.services.common.utils.Controller;
import com.services.common.utils.FXMLUtils;
import com.services.model.User;
import com.services.ui.SuperScreen;

@Controller
public class LoginAppAuthMainConroller extends SuperScreen {

	private Logger logger = Logger.getLogger(LoginAppAuthMainConroller.class);

	// ============================= FXML Variable
	@FXML
	private AnchorPane root;

	// ===== Login
	@FXML
	private Button loginButton;
	@FXML
	private TextField userNameTextField;
	@FXML
	private PasswordField passwordTextField;
	@FXML
	private Button signUpButton;
	@FXML
	private Label loginMessageLabel;

	// ============================= Internal Variable
	private User currentUser;

	// ============================= FXML Controller
	private MasterPageConroller masterPageConroller;
	private int i = 0;

	// ============================= Initialize Method
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clearAndSetField();
	}

	// ============================= FXML Method

	@FXML
	private void loginButton_OnAction() {
		if (i < 3) {
			loginMessageLabel.setText("");
			if (isValidLogin()) {
				loginButton.setDisable(true);
				SuperDao superDao = new SuperDao();
				List<User> users = superDao.findAllUser();
				if (isValidUserLogin(users) || isDefaultUserLogin()) {
					loginSuccess();
				} else {
					loginMessageLabel.setText("Username or password was Incorrect...!!!");
				}
				loginButton.setDisable(false);
			}
			i++;
		} else {
			loginMessageLabel.setText("maximum try limit over so your applicaion locked...!!!");
			root.setDisable(true);
		}

	}

	/**
	 *  Set by default admin as username and admin as a password.
	 */
		private boolean isDefaultUserLogin() {
			if(userNameTextField.getText().equals("admin") && passwordTextField.getText().equals("admin")) {
				return true;
			} else {
				return false;
			}
		}
	
		@FXML
		private void signUpButton_OnAction() {
			try {
				SignUpConroller signUpConroller = FXMLUtils.getController(SignUpConroller.class);
				signUpConroller.configureAndLoadController(masterPageConroller);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

	// ============================= Internal Method

	/**
	 * @return true if username and password are entered
	 */
		private boolean isValidLogin() {
			boolean flag = true;
	
			if (userNameTextField.getText().trim().isEmpty()) {
				flag = false;
			}
	
			if (passwordTextField.getText().trim().isEmpty()) {
				flag = false;
			}
	
			return flag;
		}

	/**
	 * @param users
	 * @return true if logged user are part of out system.
	 */
		private boolean isValidUserLogin(List<User> users) {
			for (User user : users) {
				if (user.getUserName().equals(userNameTextField.getText())
						&& user.getPassword().equals(passwordTextField.getText())) {
					currentUser = user;
					return true;
				}
			}
			return false;
		}

	/**
	 * This method set focus in username textfield when screen load
	 */
		private void clearAndSetField() {
			userNameTextField.requestFocus();
		}
	
		private void loginSuccess() {
			loadDashBoardMasterPageController();
		}
		
		private void loadDashBoardMasterPageController() {
			try {
				DashBoardMasterPageController userDetailConroller = FXMLUtils.getController(DashBoardMasterPageController.class);
				userDetailConroller.configureAndLoadController(masterPageConroller, currentUser);
			} catch (Exception exception) {
				logger.error(exception.getMessage(), exception);
			}
		}

	// ============================= Get Screen
	@Override
	public Node getScreen() {
		return super.getScreen();
	}

	public void configureAndLoadController(MasterPageConroller masterPageConroller) {
		this.masterPageConroller = masterPageConroller;
		reloadMyScreen();
	}

	private void reloadMyScreen() {
		setScreenToMasterPage();
	}

	private void setScreenToMasterPage() {
		masterPageConroller.setMainContent(getScreen());
	}

}
