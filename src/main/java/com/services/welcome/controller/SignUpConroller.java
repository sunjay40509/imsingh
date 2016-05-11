package com.services.welcome.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import com.services.common.dao.SuperDao;
import com.services.common.utils.Controller;
import com.services.controller.book.IssueBookController;
import com.services.model.User;
import com.services.ui.SuperScreen;

@Controller
public class SignUpConroller extends SuperScreen {

	// ============================= FXML Variable
	@FXML
	private AnchorPane root;
	@FXML
	private Button signUpButton;
	@FXML
	private Button cancelButton;
	@FXML
	private TextField firstNameTextField;
	@FXML
	private TextField lastNameTextField;
	@FXML
	private TextField userNameTextField;
	@FXML
	private TextField emailIdTextField;
	@FXML
	private PasswordField passowordPasswordField;
	@FXML
	private PasswordField confirmPasswordField;

	@FXML
	private Label emailIdValidationLabel;
	@FXML
	private Label passwordValidationLabel;
	@FXML
	private Label firstNameValidationLabel;
	@FXML
	private Label lastNameValidationLabel;
	@FXML
	private Label userNameValidationLabel;

	// ============================= Internal Variable
	private final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private User currentUser;

	// ============================= FXML Controller
	private UserMasterPageConroller userMasterPageConroller;
	private IssueBookController issueBookController;

	// ============================= Initialize Method
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clearAndSetField();
	}

	// ============================= FXML Method

	@FXML
	private void signUpButton_OnAction() {
		if (isValidSignUp()) {
			User user = getUserDetail();
			SuperDao superDao = new SuperDao();
			superDao.insertOrUpdate(user);
			if(userMasterPageConroller != null) {
				userMasterPageConroller.createPopupWindow.hide();
				userMasterPageConroller.searchUserButton_OnAction();				
			} else if(issueBookController != null) {
				issueBookController.createPopupWindow.hide();
				issueBookController.showActionMessage();
			}
		}
	}

	@FXML
	private void cancelButton_OnAction() {
		if(userMasterPageConroller != null) {
			userMasterPageConroller.createPopupWindow.hide();			
		} else if(issueBookController != null) {
			issueBookController.createPopupWindow.hide();
		}
	}
	

	// ============================= Internal Method
	
	private void clearAndSetField() {
		firstNameTextField.requestFocus();
	}

	/**
	 * @return true if all input criteria are matched.
	 */
		private boolean isValidSignUp() {
			clearAllValidation();
			boolean isValidSignUp = true;
	
			if (passowordPasswordField.getText().trim().isEmpty()) {
				isValidSignUp = false;
				passwordValidationLabel.setText("Enter Password.");
			} else if (confirmPasswordField.getText().trim().isEmpty()) {
				isValidSignUp = false;
				passwordValidationLabel.setText("Enter Confirm Password.");
			} else {
				if (!passowordPasswordField.getText().trim().isEmpty()
						&& !confirmPasswordField.getText().trim().isEmpty()) {
					if (!passowordPasswordField.getText().equals(confirmPasswordField.getText())) {
						isValidSignUp = false;
						passwordValidationLabel.setText("Password not match.");
					}
				}
			}
	
			if (emailIdTextField.getText().trim().isEmpty()) {
				emailIdValidationLabel.setText("Please enter Email Id");
				isValidSignUp = false;
			} else {
				Pattern pattern = Pattern.compile(EMAIL_PATTERN);
				Matcher matcher = pattern.matcher(emailIdTextField.getText());
				if (!matcher.matches()) {
					emailIdValidationLabel.setText("Please enter valid Email Id.");
					isValidSignUp = false;
				}
			}
			
			if(firstNameTextField.getText().trim().isEmpty()) {
				isValidSignUp = false;
				firstNameValidationLabel.setText("Please enter first name");
			}
			
			if(lastNameTextField.getText().trim().isEmpty()) {
				isValidSignUp = false;
				lastNameValidationLabel.setText("Please enter last name");
			}
			
			if(userNameTextField.getText().trim().isEmpty()) {
				isValidSignUp = false;
				userNameValidationLabel.setText("Please enter user name");
			}
	
			return isValidSignUp;
		}

	/**
	 * This method clear all validation message.
	 */
		private void clearAllValidation() {
			emailIdValidationLabel.setText("");
			passwordValidationLabel.setText("");
			firstNameValidationLabel.setText("");
			lastNameValidationLabel.setText("");
			userNameValidationLabel.setText("");
		}

	/**
	 * Prepare User Detail
	 */
		private User getUserDetail() {
			User user = new User();
			user.setFirstName(firstNameTextField.getText());
			user.setLastName(lastNameTextField.getText());
			user.setUserName(userNameTextField.getText());
			user.setPassword(passowordPasswordField.getText());
			user.setEmaiId(emailIdTextField.getText());
			return user;
		}

	// ============================= Get Screen
		
	@Override
	public Node getScreen() {
		return super.getScreen();
	}

	public void configureAndLoadController(UserMasterPageConroller userMasterPageConroller, User user) {
		this.userMasterPageConroller = userMasterPageConroller;
		this.currentUser = user;
		setDataToFXML();
	}

	/**
	 * Set Used Detail Into Form
	 */
		private void setDataToFXML() {
			if(currentUser == null) {
				return;
			}
			
			firstNameTextField.setText(currentUser.getFirstName());
			lastNameTextField.setText(currentUser.getLastName());
			userNameTextField.setText(currentUser.getUserName());
			userNameTextField.setDisable(true);
			passowordPasswordField.setText(currentUser.getPassword());
			confirmPasswordField.setText(currentUser.getPassword());
			emailIdTextField.setText(currentUser.getEmaiId());
			
		}
	
		public void configureAndLoadController(MasterPageConroller masterPageConroller) {
			
		}
	
		public void configureAndLoadController(IssueBookController issueBookController) {
			this.issueBookController = issueBookController;
		}
}
