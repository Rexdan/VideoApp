package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utility.Stages;

public class MainViewController
{
	private Stage stage;
	
	@FXML
	private Button uploadFiles;
	
	@FXML
	private Button goToMedia;
	
	@FXML
	private void selectedUploadFiles(ActionEvent event) throws IOException
	{
		Stages.goToUploads(stage);
	}
	
	@FXML
	private void selectedGoToMedia(ActionEvent event) throws IOException
	{
		Stages.goToCategories(stage);
	}
	
	public void start(Stage stage)
	{
		this.stage = stage;
		stage.setTitle("Main Menu");
	}
}
