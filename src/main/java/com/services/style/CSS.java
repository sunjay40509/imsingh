package com.services.style;

public class CSS {

	public static String getStyleSheet(String name) {
		return CSS.class.getResource(name).toExternalForm();
	}

}
