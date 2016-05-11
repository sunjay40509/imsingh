package com.services.ui;

import javafx.fxml.Initializable;
import javafx.scene.Node;


public interface SuperScreenInterface extends Initializable {
	
	
	/**
	 * @return
	 * 	Returns the super parent node.
	 */
	public Node getScreen();
	/**
	 *	 
	 */
	public void setupScreen();
	public boolean isValid();
	
	public void block();
	
}
