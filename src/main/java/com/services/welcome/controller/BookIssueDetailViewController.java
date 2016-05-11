package com.services.welcome.controller;

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


import org.apache.log4j.Logger;

import com.services.common.utils.Controller;
import com.services.common.utils.MyUtils;
import com.services.model.Book;
import com.services.ui.SuperScreen;

@Controller
public class BookIssueDetailViewController extends SuperScreen {

	private Logger logger = Logger.getLogger(getClass());

	// ======================= FXML Variable
	@FXML
	protected AnchorPane root;
	
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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);
		clearAndSetField();
	}

	// ======================= Internal Variable

	// ======================= FXML Controller

	// ======================= FXML Method

	// ======================= Internal Method
	
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
	}
	
	private void setDataToFXML(List<Book> books) {
		if(books != null && !books.isEmpty()) {
			bookDetailTableView.setItems(FXCollections.observableArrayList(books));			
		}
	}

	@Override
	public Node getScreen() {
		return super.getScreen();
	}

	public void configureAndLoadController(List<Book> books) {
		setDataToFXML(books);
	}

}
