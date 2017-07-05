package utility;

import java.io.IOException;
import controller.CategoriesViewController;
import controller.MainViewController;
import controller.OneViewController;
import controller.UploadViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Stages 
{	
	public static void goToMovies(Stage stage) throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Stages.class.getResource("/view/OneView.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Scene scene = new Scene(root);
		OneViewController controller = loader.getController();
		controller.start(stage);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void goToMain(Stage stage) throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Stages.class.getResource("/view/MainView.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Scene scene = new Scene(root);
		MainViewController controller = loader.getController();
		controller.start(stage);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void goToUploads(Stage stage) throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Stages.class.getResource("/view/UploadView.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Scene scene = new Scene(root);
		UploadViewController controller = loader.getController();
		controller.start(stage);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void goToShows(Stage stage) throws IOException{goToMovies(stage);}
	
	public static void goToBooks(Stage stage) throws IOException{goToMovies(stage);}
	
	public static void goToCategories(Stage stage)throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Stages.class.getResource("/view/CategoriesView.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Scene scene = new Scene(root);
		CategoriesViewController controller = loader.getController();
		controller.start(stage);
		stage.setScene(scene);
		stage.show();
	}
}
