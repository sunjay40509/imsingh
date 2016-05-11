package com.services.controller.book;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.log4j.Logger;

import com.services.common.dao.BookDao;
import com.services.common.dao.SuperDao;
import com.services.common.utils.Controller;
import com.services.common.utils.DialogUtils;
import com.services.common.utils.FXMLUtils;
import com.services.common.utils.MyUtils;
import com.services.model.Book;
import com.services.model.User;
import com.services.ui.SuperScreen;
import com.services.welcome.controller.SignUpConroller;

/**
 * This controller class used to issue book to particular user
 */
@Controller
public class IssueBookController extends SuperScreen {

	private Logger logger = Logger.getLogger(getClass());

	// ======================= FXML Variable
	@FXML
	protected AnchorPane root;
	
	@FXML
	protected TextField memberSearchTextField;
	@FXML
	protected Button validateMemberButton;
	@FXML
	protected Button addMemberButton;
	@FXML
	protected Label validateMemberValidationLabel;
	@FXML
	protected VBox memberDetailVBox;
	@FXML
	protected Button issueBookButton;
	@FXML
	protected VBox mainContainerVBox;
	@FXML
	protected Label firstNameLabel;
	@FXML
	protected Label lastNameLabel;
	@FXML
	protected Label emailLabel;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);
		clearAndSetField();
	}

	// ======================= Internal Variable
	private Book currentBook; 
	public Stage createPopupWindow;
	private User currentUser;

	// ======================= FXML Controller
	private BookMasterPageConroller bookMasterPageConroller;

	// ======================= FXML Method
	@FXML
	private void issueBookButton_OnAction() {
		if(isCheckMaximumIssueCopies() && !isUserAlreadyBookIssue()) {
			
			updateBookDetail();
			updateUserBookDetail();
			
			bookMasterPageConroller.issuePopupWindow.hide();
			bookMasterPageConroller.displaySucessMessage(currentBook, currentUser);
			bookMasterPageConroller.reloadMyScreen();
		}
	}
	
	@FXML
	private void validateMemberButton_OnAction() {
		if(isValidMember()) {
			clearValidation();
			SuperDao superDao = new SuperDao();
			List<User> memberList = superDao.select(memberSearchTextField.getText());
			if(memberList != null && !memberList.isEmpty()) {
				addMemberButton.setVisible(false);
				setDataToFxml(memberList);
			} else {
				addMemberButton.setVisible(true);
				validateMemberValidationLabel.setText("Please Add New Member.");
				if(mainContainerVBox.getChildren().contains(memberDetailVBox)) {
					mainContainerVBox.getChildren().remove(memberDetailVBox);	
				}
			}
		}
	}

	@FXML
	private void addNewMemberButton_OnAction() {
		try {
			SignUpConroller signUpConroller = FXMLUtils.getController(SignUpConroller.class);
			signUpConroller.configureAndLoadController(this);
			createPopupWindow = DialogUtils.createPopupWindow(signUpConroller.getScreen(), "Add User");
			createPopupWindow.show();
		} catch (Exception exception) {
			logger.error(exception.getMessage(), exception);
		}
	}
	// ======================= Internal Method

	private void clearAndSetField() {
		mainContainerVBox.getChildren().remove(memberDetailVBox);
	}
	
	/**
	 * @return true if book can't issue more then no of issue copies
	 */
		private boolean isCheckMaximumIssueCopies() {
			Integer noOfIssueCopies = MyUtils.getInteger(currentBook.getNoOfIssueCopies());
			clearValidation();
			if(noOfIssueCopies > 0) {
				return true;
			} else {
				validateMemberValidationLabel.setText("Book Cannot be issue. Maximum qutota exceeded.");
				return false;
			}
		}
	
	private boolean isUserAlreadyBookIssue() {
		clearValidation();
		if(currentUser != null && currentUser.getBooks() != null &&
				!currentUser.getBooks().isEmpty()) {
			List<Book> books = currentUser.getBooks();
			for(Book book : books) {
				book.getIsbn().equals(currentBook.getIsbn());
				validateMemberValidationLabel.setText("This user have already book issue.");
				return true;
			}
		}
		return false;
	}
	
	private void updateBookDetail() {
		Book book = currentBook;
		Integer noOfCopies = book.getNoOfIssueCopies() - 1;
		book.setNoOfIssueCopies(noOfCopies);

		
		List<User> users = null;
		if(book.getUsers() != null && book.getUsers().isEmpty()) {
			users = new ArrayList<User>();
			users.add(currentUser);				
		} else {
			users = book.getUsers();
			users.add(currentUser);
		}
		
		book.setUsers(users);
		
		BookDao bookDao = new BookDao();
		bookDao.insertOrUpdate(book);
	}

	private void updateUserBookDetail() {
		if(currentUser != null) {
			
			List<Book> books = null;
			if(currentUser.getBooks() != null && currentUser.getBooks().isEmpty()) {
				books = new ArrayList<Book>();
				books.add(currentBook);				
			} else {
				books = currentUser.getBooks();
				books.add(currentBook);
			}
			SuperDao superDao = new SuperDao();
			currentUser.setBooks(books);
			superDao.insertOrUpdate(currentUser);
		}
	}
	
	private void setDataToFxml(List<User> memberList) {
		if(!mainContainerVBox.getChildren().contains(memberDetailVBox)) {
			mainContainerVBox.getChildren().add(memberDetailVBox);	
		}
		User user = memberList.get(0);
		currentUser = user;
		firstNameLabel.setText(user.getFirstName());
		lastNameLabel.setText(user.getLastName());
		emailLabel.setText(user.getEmaiId());
	}

	private void clearValidation() {
		validateMemberValidationLabel.setText("");		
	}
	
	/**
	 * @return true if search member are valid
	 */
		private boolean isValidMember() {
			clearValidation();
			if(memberSearchTextField.getText().trim().isEmpty()) {
				validateMemberValidationLabel.setText("Please enter Member First Name.");
				return false;
			}
			return true;
		}
	
	@Override
	public Node getScreen() {
		return super.getScreen();
	}

	public void configureAndLoadController(Book book, BookMasterPageConroller bookMasterPageConroller) {
		this.bookMasterPageConroller = bookMasterPageConroller;
		this.currentBook = book;
	}

	public void showActionMessage() {
		clearValidation();
		validateMemberValidationLabel.setText("Member Added Sucessfully...!!!");
		addMemberButton.setVisible(false);
		memberSearchTextField.setText("");
		memberSearchTextField.setFocusTraversable(true);
	}
}
