package com.services.controller.book;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import com.services.common.utils.Controller;
import com.services.model.User;
import com.services.ui.SuperScreen;

/**
 * This controller class used to display detail of user that have
 * issue book
 */
@Controller
public class BookUserDetailViewController extends SuperScreen {

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
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);
		clearAndSetField();
	}

	// ======================= Internal Method

	private void clearAndSetField() {
		initializeTableColumn();
	}
	
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
	}

	
	private void setDataToFXML(List<User> users) {
		if(users != null && !users.isEmpty()) {
			userDetailTableView.setItems(FXCollections.observableArrayList(users));
		}	
	}
	
	@Override
	public Node getScreen() {
		return super.getScreen();
	}

	public void configureAndLoadController(List<User> users) {
		setDataToFXML(users);
	}

}
