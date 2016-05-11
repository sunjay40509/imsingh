package com.services.controller.book;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import com.services.common.dao.BookDao;
import com.services.common.utils.Controller;
import com.services.common.utils.MyUtils;
import com.services.model.Book;
import com.services.ui.SuperScreen;

/**
 * This page used to add book into system.
 */
@Controller
public class AddBookController extends SuperScreen {

	// ======================= FXML Variable
	@FXML
	protected AnchorPane root;
	
	@FXML
	protected TextField isbnNoTextField;
	
	@FXML
	protected TextField titleTextField;
	
	@FXML
	protected TextField noOfCopiesTextField;
	
	@FXML
	protected TextField bookPriceTextField;
	
	@FXML
	protected Label isbnValidationLabel;
	@FXML
	protected Label titleValidationLabel;
	@FXML
	protected Label noOfCopiesValidationLabel;
	@FXML
	protected Label bookPriceValidationLabel;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);
		clearAndSetField();
	}

	// ======================= Internal Variable
	public Stage createPopupWindow, authorStage;
	private  Book currentBook;

	// ======================= FXML Controller
	private BookMasterPageConroller bookMasterPageConroller;

	private boolean isEditMode = false;

	// ======================= FXML Method
	
	@FXML
	private void saveBookButton_OnAction() {
		if(isValidBook() && isAddDuplicateBook()) {
			BookDao bookDao = new BookDao();
			bookDao.insertOrUpdate(getBookDetail());
			bookMasterPageConroller.createPopupWindow.hide();
			bookMasterPageConroller.loadBookDetail();
		}
	}
	
	@FXML
	private void cancelButton_OnAction() {
		bookMasterPageConroller.createPopupWindow.hide();
	}

	// ======================= Internal Method
	
	private void clearValidation() {
		isbnValidationLabel.setText("");
		titleValidationLabel.setText("");
		noOfCopiesValidationLabel.setText("");
		bookPriceValidationLabel.setText("");
	}
	
	/**
	 * @return true if all criteria are satified
	 */
		private boolean isValidBook() {
			clearValidation();
			boolean isValid = true;
			
			if(isbnNoTextField.getText().trim().isEmpty()) {
				isValid = false;
				isbnValidationLabel.setText("Please enter ISBN No.");
			}
			
			if(titleTextField.getText().trim().isEmpty()) {
				isValid = false;
				titleValidationLabel.setText("Please enter book title.");
			}
			
			if(bookPriceTextField.getText().trim().isEmpty()) {
				isValid = false;
				bookPriceValidationLabel.setText("Please enter book price.");
			}
			
			if(noOfCopiesTextField.getText().trim().isEmpty()) {
				isValid = false;
				noOfCopiesValidationLabel.setText("Please enter no of book copies.");
			}
			
			return isValid;
		}
	
	/**
	 * @return true if same isbn no exits
	 */
		private boolean isAddDuplicateBook() {
			clearValidation();
			BookDao bookDao = new BookDao();
			List<Book> books = bookDao.findAllBook();
			for(Book book : books) {
				if(currentBook != null && currentBook.getIsbn().
						equals(isbnNoTextField.getText())) {
					continue;
				} else if(isbnNoTextField.getText().equalsIgnoreCase(book.getIsbn())) {
					isbnValidationLabel.setText("ISBN No already exits.");
					return false;
				}
			}
			return true;
		}
	
	/**
	 * @return book detail object
	 */
		public Book getBookDetail() {
			Book book = new Book();
			
			book.setIsbn(isbnNoTextField.getText());
			book.setTitle(titleTextField.getText());
			book.setBookPrice(MyUtils.getIntegerFromString(bookPriceTextField.getText()));
			book.setNoOfCopies(MyUtils.getIntegerFromString(noOfCopiesTextField.getText()));
			//if(!isEditMode) {
				book.setNoOfIssueCopies(book.getNoOfCopies());			
			//}
			return book;
		}
		
		private void clearAndSetField() {
		}

	
	/**
	 *  This method set book detail
	 */
		private void setDataToFXML() {
			if(currentBook != null) {
				isEditMode = true;
				isbnNoTextField.setText(currentBook.getIsbn());
				isbnNoTextField.setDisable(true);
				titleTextField.setText(currentBook.getTitle());
				bookPriceTextField.setText(MyUtils.getStringValue(currentBook.getBookPrice()));
				noOfCopiesTextField.setText(MyUtils.getStringValue(currentBook.getNoOfCopies()));
			}		
		}

	@Override
	public Node getScreen() {
		return super.getScreen();
	}

	public void configureAndLoadController(BookMasterPageConroller bookMasterPageConroller, Book currentBook) {
		this.currentBook = currentBook;
		this.bookMasterPageConroller = bookMasterPageConroller;
		setDataToFXML();
	}
}
