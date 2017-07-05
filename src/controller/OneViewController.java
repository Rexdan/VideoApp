package controller;

import java.io.IOException;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Book;
import model.Movie;
import model.Show;
import utility.Media;
import utility.Stages;

public class OneViewController 
{
	private String type = CategoriesViewController.getType();
	private ObservableList<Object> objects;
	private Stage stage;
	private static Show selectedShow;
	
	public void start(Stage stage)
	{
		this.stage = stage;
	}
	
	@FXML
	private Button goBack;
	
	@FXML
	private Label selectionTitle;
	
	@FXML
	private Label description;
	
	@FXML
	private ListView<Object> listView;
	
	@FXML
	private void goBackToCategories() throws IOException
	{
		Stages.goToCategories(stage);
	}
	
	@FXML
	private void setOnMouseClicked(MouseEvent event) throws IOException
	{
		if (event.getClickCount() == 2)
		{
			if(type.equals("movie"))
			{
				Movie selectedMovie = (Movie) listView.getSelectionModel().getSelectedItem();
				Media.playVideo(selectedMovie);
			}
			
			if(type.equals("book"))
			{
				Book selectedBook = (Book) listView.getSelectionModel().getSelectedItem();
				Media.openBook(selectedBook);
			}
			
			if(type.equals("show"))
			{
				selectedShow = (Show)listView.getSelectionModel().getSelectedItem();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/DualView.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				Scene scene = new Scene(root);
				DualViewController controller = loader.getController();
				controller.start(stage);
				stage.setScene(scene);
				stage.show();
			}
		}
	}
	
	@FXML
	private void initialize()
	{	
		if(type.equals("movie"))
		{
			selectionTitle.setText("Select a Movie");
			selectionTitle.setCenterShape(true);
			description.setText("(Double Click to Play Movie)");
			description.setCenterShape(true);
			objects = FXCollections.observableArrayList();
			objects.addAll(Main.getMovies());
			listView.setItems(objects);
		}
		
		if(type.equals("book"))
		{
			selectionTitle.setText("Select a Book");
			selectionTitle.setCenterShape(true);
			description.setText("(Double Click to Open Book)");
			description.setCenterShape(true);
			objects = FXCollections.observableArrayList();
			objects.addAll(Main.getBooks());
			listView.setItems(objects);
		}
		
		if(type.equals("show"))
		{
			selectionTitle.setText("Select a Show");
			selectionTitle.setCenterShape(true);
			description.setText("(Double Click to See Episodes)");
			description.setCenterShape(true);
			objects = FXCollections.observableArrayList();
			objects.addAll(Main.getShows());
			listView.setItems(objects);
		}
	}
	
	public static Show getSelectedShow()
	{
		return selectedShow;
	}
}
