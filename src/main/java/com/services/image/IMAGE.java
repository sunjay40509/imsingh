package com.services.image;

import javafx.scene.image.Image;

public class IMAGE {
	public static Image getImage(String name) {
		return new Image(IMAGE.class.getResourceAsStream(name));
	}
}
