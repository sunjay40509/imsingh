package com.services.welcome.controller;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.apache.log4j.Logger;

import com.services.common.dao.SuperDao;
import com.services.common.utils.Controller;
import com.services.common.utils.DialogUtils;
import com.services.common.utils.FXMLUtils;
import com.services.model.Book;
import com.services.model.User;
import com.services.ui.SuperScreen;


@Controller
public class UserMasterPageConroller extends SuperScreen {

	private Logger logger = Logger.getLogger(getClass());

	// ======================= FXML Variable
	@FXML
	protected AnchorPane root;

	@FXML
	protected TableView<User> userDetailTableView;
	@FXML
	protected TableColumn<User, String> userNameTableColumn;
	@FXML
	protected TableColumn<User, String> userFirstNameColumn;
	@FXML
	protected TableColumn<User, String> userLastNameColumn;
	@FXML
	protected TableColumn<User, String> emailIdTableColumn;
	@FXML
	protected TableColumn<User, Node> actionTableColumn;

	@FXML
	protected Button addUserButton;
	
	@FXML
	protected TextField searchByLastNameTextField;
	@FXML
	protected Button searchUserButton;

	// ======================= Internal Variable
	public Stage createPopupWindow;

	// ======================= FXML Controller
	private MasterPageConroller masterPageConroller;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);
		initializeTableColumn();
	}

	// ======================= FXML Method

	@FXML
	private void addUserButton_OnAction() {
		try {
			SignUpConroller signUpConroller = FXMLUtils.getController(SignUpConroller.class);
			signUpConroller.configureAndLoadController(this, null);
			createPopupWindow = DialogUtils.createPopupWindow(signUpConroller.getScreen(), "Add User");
			createPopupWindow.show();
		} catch (Exception exception) {
			logger.error(exception.getMessage(), exception);
		}
	}

	
	@FXML
	public void searchUserButton_OnAction() {
		userDetailTableView.getItems().clear();
		if(!searchByLastNameTextField.getText().trim().isEmpty()) {
			searchUserDeail();
		} else {
			loadUserDetailIntoTable();
		}
	}
	
	// ======================= Internal Method

	private void initializeTableColumn() {
		userNameTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<User, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getUserName());
					}
				});

		userFirstNameColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<User, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getFirstName());
					}
				});

		userLastNameColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<User, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getLastName());
					}
				});

		emailIdTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<User, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getEmaiId());
					}
				});

		actionTableColumn
				.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, Node>, ObservableValue<Node>>() {
					@Override
					public ObservableValue<Node> call(final CellDataFeatures<User, Node> param) {

						HBox box = new HBox();
						box.setSpacing(5);
						box.setAlignment(Pos.CENTER);
						
						Button updateUser = new Button("Update");
						updateUser.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								updateUser(param.getValue());
							}
						});
						box.getChildren().add(updateUser);
						

						Button deleteUser = new Button("Delete");
						deleteUser.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								deleteUser(param.getValue());
							}
						});
						box.getChildren().add(deleteUser);
						
						Button bookIssueDetailButton = new Button("Book Issue Detail");
						bookIssueDetailButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								bookIssueDetailButton(param.getValue());
							}
						});
						box.getChildren().add(bookIssueDetailButton);
						
						return new ReadOnlyObjectWrapper<Node>(box);
					}
				});

	}
	
	private void bookIssueDetailButton(User user) {
		try {
			List<Book> books = user.getBooks();
			//if(books != null && !books.isEmpty()) {
				//System.out.println(" >>> Book Detail :: " + JSONUtils.prettyPrinter(books));
				BookIssueDetailViewController bookIssueDetailViewController = FXMLUtils.
						getController(BookIssueDetailViewController.class);
				bookIssueDetailViewController.configureAndLoadController(books);
				Stage createPopupWindow = DialogUtils.createPopupWindow(bookIssueDetailViewController.getScreen(), 
						"Book Issue Detail");
				createPopupWindow.show();
			//}			
		}catch(Exception exception) {
			logger.error(exception.getMessage(), exception);
		}
	}

	private void deleteUser(User deleteUser) {
		
		Alert createAlertPopupWindow = DialogUtils.createAlertPopupWindow(AlertType.CONFIRMATION, "Delete User", "Delete User", 
				"Are You Sure Want to remove this user ?");
		if(createAlertPopupWindow.getResult() == ButtonType.OK) {
			SuperDao superDao = new SuperDao();
			superDao.delete(deleteUser);
			searchUserButton_OnAction();
		}
	}
	
	private void updateUser(User user) {
		try {
			SignUpConroller signUpConroller = FXMLUtils.getController(SignUpConroller.class);
			signUpConroller.configureAndLoadController(this, user);
			createPopupWindow = DialogUtils.createPopupWindow(signUpConroller.getScreen(), "Update User");
			createPopupWindow.show();
		} catch (Exception exception) {
			logger.error(exception.getMessage(), exception);
		}
	}
	
	public void loadUserDetailIntoTable() {
		SuperDao superDao = new SuperDao();
		List<User> findAllUser = superDao.findAllUser();
		userDetailTableView.setItems(FXCollections.observableArrayList(findAllUser));
	}
	
	private void searchUserDeail() {
		userDetailTableView.setItems(FXCollections.observableArrayList(getSearchDetailUser()));
	}
	
	private List<User> getSearchDetailUser() {
		SuperDao superDao = new SuperDao();
		List<User> findAllUser = superDao.findAllUser();
		
		List<User> searchUsers = new ArrayList<>();
		for(User user : findAllUser) {
			if(user.getLastName().equals(searchByLastNameTextField.getText())) {
				searchUsers.add(user);
			}
		}
		return searchUsers;
	}

	@Override
	public Node getScreen() {
		return super.getScreen();
	}

	public void configureAndLoadConroller(MasterPageConroller masterPageConroller) {
		this.masterPageConroller = masterPageConroller;
	}

}
