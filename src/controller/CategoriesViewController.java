package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import utility.Stages;

public class CategoriesViewController 
{
	private Stage stage;
	
	private static String type;
	
	@FXML
	private Button movies;
	
	@FXML
	private Button shows;
	
	@FXML
	private Button books;
	
	@FXML
	private Button music;
	
	@FXML
	private Button goBack;
	
	@FXML
	private MenuItem quit;
	
	@FXML
	private void selectedMovies(ActionEvent event) throws IOException
	{
		type = "movie";
		this.stage.setTitle("Movies");
		Stages.goToMovies(stage);
	}
	
	@FXML
	private void selectedShows(ActionEvent event) throws IOException
	{
		type = "show";
		this.stage.setTitle("Shows");
		Stages.goToShows(stage);
	}
	
	@FXML
	private void selectedBooks(ActionEvent event) throws IOException
	{
		type = "book";
		this.stage.setTitle("Books");
		Stages.goToBooks(stage);
	}
	
	@FXML
	private void selectedMusic(ActionEvent event)
	{
		
	}
	
	@FXML
	private void goBackToMainMenu(ActionEvent event) throws IOException
	{
		Stages.goToMain(stage);
	}
	
	@FXML
	private void selectedQuit(ActionEvent event)
	{
		stage.close();
	}
	
	public static String getType()
	{
		return type;
	}
	
	public void start(Stage stage)
	{
		this.stage = stage;
		stage.setTitle("Categories");
	}
}
