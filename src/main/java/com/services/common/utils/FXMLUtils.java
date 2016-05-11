/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.common.utils;

import org.apache.log4j.Logger;

import javafx.fxml.FXMLLoader;

/**
 * Common FXML related utils.
 * 
 * @author shreyas.dave
 */
public class FXMLUtils {

	static Logger logger = Logger.getLogger(FXMLUtils.class);

	public static <T extends Object> T getController(Class<T> klass) throws Exception {

		try {
			Controller fxmlurl = klass.getAnnotation(Controller.class);
			String URL = fxmlurl.URL();

			if (URL.isEmpty()) {
				URL = klass.getSimpleName() + ".fxml";
			}
			
			logger.debug(URL);
			
			FXMLLoader loader = new FXMLLoader(klass.getResource(URL));
			loader.load();

			return loader.getController();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

}
