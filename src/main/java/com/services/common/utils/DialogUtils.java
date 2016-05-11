package com.services.common.utils;

import com.services.Main;
import com.services.image.IMAGE;
import com.services.style.CSS;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogUtils {

	/**
	 * @param content
	 * @param title
	 * @param owner
	 * @return
	 * 
	 * 		demo :
	 * 
	 *         Stage stage = DialogUtils.createPopupWindow(contentnode,"title");
	 *         stage.show()
	 * 
	 */
	public static Stage createPopupWindow(Node content, String title) {
		Stage dialogStage = new Stage();
		dialogStage.setTitle(title);
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(Main.TEMP_STAGE);
		dialogStage.getIcons().add(IMAGE.getImage("cd.png"));
		Scene scene = new Scene((Parent) content);
		scene.getStylesheets().addAll(CSS.getStyleSheet("flip.css"));
		dialogStage.setScene(scene);
		dialogStage.setResizable(false);
		dialogStage.setMaximized(false);
		return dialogStage;
	}

	public static Alert createAlertPopupWindow(AlertType alertType, String title, String headerText,
			String contentText) {
		return createAlertPopupWindow(alertType, title, headerText, contentText, true);
	}

	public static Alert createAlertPopupWindow(AlertType alertType, String title, String headerText, String contentText,
			boolean isShowDialog) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		if (isShowDialog) {
			alert.showAndWait();
		}
		return alert;
	}

	public static Alert createConformAlertPopup(String title, String headerText, String contentText) {

		ButtonType buttonTypeYes = new ButtonType("Yes", ButtonData.YES);
		ButtonType buttonTypeNo = new ButtonType("No", ButtonData.NO);
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);

		return alert;
	}

	public static Stage createPopupWindow(Node content, String title, Stage owner, Stage dialogStage) {
		dialogStage.setTitle(title);
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(owner);
		// dialogStage.getIcons().add(MyApplication.getApplicationIcon());
		Scene scene = new Scene((Parent) content);
		// scene.getStylesheets().addAll(
		// MyApplication.class.getResource("style.css").toExternalForm());
		dialogStage.setScene(scene);
		dialogStage.setResizable(false);
		dialogStage.setMaximized(false);
		return dialogStage;
	}

	public static void showError(String headerText) {
		showError(headerText, null);
	}

	public static void showError(String headerText, Exception e) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(headerText);
		if (e != null) {
			alert.setContentText(e.getMessage());
		}
		alert.showAndWait();
	}

	public static void showMessage(String headerText) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message");
		alert.setContentText(headerText);
		alert.showAndWait();
	}

}
