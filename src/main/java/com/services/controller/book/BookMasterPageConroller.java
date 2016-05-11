package com.services.controller.book;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

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
import com.services.welcome.controller.MasterPageConroller;

/**
 * This is MasterPage Of Book In Which You can search book, delete book
 * issue book to particular used and also add book
 */
@Controller
public class BookMasterPageConroller  extends SuperScreen{
	
	private Logger logger = Logger.getLogger(getClass());

	// ======================= FXML Variable
	@FXML
	protected AnchorPane root;
	@FXML
	protected TextField bookIsbnNoSearchTextField;
	@FXML
	protected Button addBookButton;
	@FXML
	protected TableView<Book> bookDetailTableView;
	@FXML
	protected TableColumn<Book, String> isbnNoTableColumn;
	@FXML
	protected TableColumn<Book, String> bookNameTableColumn;
	@FXML
	protected TableColumn<Book, Integer> bookPriceTableColumn;
	@FXML
	protected TableColumn<Book, Integer> bookCopiesTableColumn;
	@FXML
	protected TableColumn<Book, Integer> bookIssueCopiesTableColumn;
	@FXML
	protected TableColumn<Book, Node> bookActionTableColumn;
	
	@FXML
	protected Label sucessMessageLabel;
	
	// ======================= Internal Variable
	public Stage createPopupWindow, issuePopupWindow;

	// ======================= FXML Controller
	private MasterPageConroller masterPageConroller;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);
		clearAndSetField();
	}

	// ======================= FXML Method
	
	@FXML
	private void addBookButton_OnAction() {
		try {
			AddBookController addBookController = FXMLUtils.getController(AddBookController.class);
			addBookController.configureAndLoadController(this, null);
			createPopupWindow = DialogUtils.createPopupWindow(addBookController.getScreen(), "Add Book");
			createPopupWindow.show();
		} catch (Exception exception) {
			logger.error(exception.getMessage(), exception);
		}
	}
	
	@FXML
	private void searchBookButton_OnAction() {
		bookDetailTableView.getItems().clear();
		if(!bookIsbnNoSearchTextField.getText().trim().isEmpty()) {
			searchBookDeail();
		} else {
			loadBookDetail();
		}
	}

	private void searchBookDeail() {
		bookDetailTableView.setItems(FXCollections.observableArrayList(getSearchDetailUser()));
	}
	
	private List<Book> getSearchDetailUser() {
		BookDao superDao = new BookDao();
		List<Book> findAllUser = superDao.findAllBook();
		
		List<Book> searchUsers = new ArrayList<>();
		for(Book user : findAllUser) {
			if(user.getIsbn().equals(bookIsbnNoSearchTextField.getText())) {
				searchUsers.add(user);
			}
		}
		return searchUsers;
	}

	// ======================= Internal Method
	
	public void loadBookDetail() {
			BookDao bookDao = new BookDao();
			List<Book> findAllBook = bookDao.findAllBook();
			bookDetailTableView.setItems(FXCollections.observableArrayList(findAllBook));
	}
	
	private void clearAndSetField() {
		initializeTableColumn();
	}
	
	private void initializeTableColumn() {
		isbnNoTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().getIsbn());
			}
		});
		
		bookNameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().getTitle());
			}
		});
		
		bookCopiesTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Book, Integer> param) {
				return new ReadOnlyObjectWrapper<Integer>(MyUtils.getInteger(param.getValue().getNoOfCopies()));
			}
		});
		
		bookIssueCopiesTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Book, Integer> param) {
				return new ReadOnlyObjectWrapper<Integer>(MyUtils.getInteger(param.getValue().getNoOfIssueCopies()));
			}
		});
		
		bookPriceTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Book, Integer> param) {
				return new ReadOnlyObjectWrapper<Integer>(MyUtils.getInteger(param.getValue().getBookPrice()));
			}
		});
		
		bookNameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().getTitle());
			}
		});
		
		
		bookActionTableColumn
			.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, Node>, ObservableValue<Node>>() {
				@Override
				public ObservableValue<Node> call(final CellDataFeatures<Book, Node> param) {
					HBox box = new HBox();
					box.setSpacing(5);
					box.setAlignment(Pos.CENTER);
					
					Button updateBook = new Button("Update");
					updateBook.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							updateBook(param.getValue());
						}
					});
					box.getChildren().add(updateBook);
					
					Button deleteBook = new Button("Delete");
					deleteBook.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							deleteBook(param.getValue());
						}
					});
					box.getChildren().add(deleteBook);
					
					Button issueBook = new Button("Issue");
					issueBook.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							issueBook(param.getValue());
						}
					});
					box.getChildren().add(issueBook);
					
					Button userDetailButton = new Button("User Detail");
					userDetailButton.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							userDetail(param.getValue());
						}
					});
					box.getChildren().add(userDetailButton);
					
					return new ReadOnlyObjectWrapper<Node>(box);
				}
			});
		
	}
	
	private void userDetail(Book book) {
		try {
			List<User> users = book.getUsers();
			//if(users != null && !users.isEmpty()) {
				BookUserDetailViewController bookUserDetailViewController = FXMLUtils.
						getController(BookUserDetailViewController.class);
				bookUserDetailViewController.configureAndLoadController(users);
				Stage createPopupWindow = DialogUtils.createPopupWindow(bookUserDetailViewController.getScreen(), 
						"Book User Detail");
				createPopupWindow.show();
			//}			
		}catch(Exception exception) {
			logger.error(exception.getMessage(), exception);
		}
	}
	
	private void updateBook(Book book) {
		try {
			AddBookController addBookController = FXMLUtils.getController(AddBookController.class);
			addBookController.configureAndLoadController(this, book);
			createPopupWindow = DialogUtils.createPopupWindow(addBookController.getScreen(), "Add Book");
			createPopupWindow.show();
		} catch (Exception exception) {
			logger.error(exception.getMessage(), exception);
		}
	}
	
	private void deleteBook(Book deleteBook) {
		Alert createAlertPopupWindow = DialogUtils.createAlertPopupWindow(AlertType.CONFIRMATION, "Delete Book", "Delete Book", "Are You Sure Want to remove this book ?");
		if(createAlertPopupWindow.getResult() == ButtonType.OK) {
			SuperDao superDao = new SuperDao();
			superDao.delete(deleteBook);
			searchBookButton_OnAction();
		}
	}
	
	private void issueBook(Book book) {
		try {
			IssueBookController issueBookController = FXMLUtils.getController(IssueBookController.class);
			issueBookController.configureAndLoadController(book, this);
			issuePopupWindow = DialogUtils.createPopupWindow(issueBookController.getScreen(), "Issue Book : " +book.getTitle() );
			issuePopupWindow.show();
		} catch(Exception exception) {
			logger.error(exception.getMessage(), exception);
		}
	}
	
	public void displaySucessMessage(Book currentBook, User currentUser) {
		sucessMessageLabel.setText(currentBook.getTitle() + " Book Sucessfully Issue To " +
					currentUser.getFirstName() + " " + currentUser.getLastName() + ".");
		
	}
	
	@Override
	public Node getScreen() {
		return super.getScreen();
	}

	public void configureAndLoadConroller(MasterPageConroller masterPageConroller) {
		this.masterPageConroller = masterPageConroller;
	}
	
	public void reloadMyScreen() {
		searchBookButton_OnAction();
	}

}
