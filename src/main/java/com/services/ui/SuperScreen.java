package com.services.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;

public class SuperScreen implements SuperScreenInterface {

	@FXML
	protected Node root;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// root.getStyleClass().add("background");
	}

	@Override
	public Node getScreen() {
		return root;
	}

	@Override
	public void setupScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void block() {
	}

	@Override
	public boolean isValid() {
		return true;
	}

}
