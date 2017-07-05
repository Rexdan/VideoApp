package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Book;
import model.Movie;
import model.Season;
import model.Show;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import controller.LoginViewController;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
	
	private static ArrayList<Movie> movies;
	private static ArrayList<Show> shows;
	private static ArrayList<Book> books;
	private static final String ftpRootPath = "ftp://rexdan.duckdns.org:64050/";
	public static final String moviesPath = "/media/HDD/Movies";
	public static final String musicPath = "/media/HDD/Music";
	public static final String showsPath = "/media/HDD/Shows";
	public static final String booksPath = "/media/HDD/Books";
	private static FTPClient ftpClient;

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(getClass().getResource("/view/LoginView.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Scene scene = new Scene(root);
		LoginViewController controller = loader.getController();
		controller.start(primaryStage);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void loadData(FTPClient client) throws IOException
	{
		ftpClient = client;
		if(ftpClient.getLocalAddress().equals("127.0.0.1"))
    	{
			ftpClient.enterLocalActiveMode();
    		System.out.println("We're local.");
    	}
    	else
    	{
    		ftpClient.enterLocalPassiveMode();
    	}
    	int reply = ftpClient.getReplyCode();
    	if(!FTPReply.isPositiveCompletion(reply))
    	{
    		System.out.println("Connection failed.");
    		return;
    	}
    	
    	/*This is useful for when working with OTHER ftp servers. Work In Progress.*/
    	//String path = ftpClient.printWorkingDirectory();
    	
    	FTPFile[] Fmovies = ftpClient.listFiles(moviesPath);
    	FTPFile[] Fshows = ftpClient.listFiles(showsPath);
    	FTPFile[] Fseasons;
    	FTPFile[] Fbooks = ftpClient.listFiles(booksPath);
    	
    	String toAdd = new String();

        for(int i = 0; i < Fmovies.length; i++)
        {
        	toAdd = Fmovies[i].getName();
        	String toMovie = ftpRootPath.concat("Movies/");
        	toMovie = toMovie.concat(toAdd);
        	movies.add(new Movie(toAdd, toMovie));
        }
        
        for(int i = 0; i < Fshows.length; i++)
        {
        	toAdd = Fshows[i].getName();
        	String toShow = ftpRootPath.concat("Shows/");
        	toShow = toShow.concat(toAdd);
        	String toAddSeasons = showsPath.concat("/" + toAdd);
        	Fseasons = ftpClient.listFiles(toAddSeasons);
        	ArrayList<Season> seasons = new ArrayList<Season>();
        	for(int j = 0; j < Fseasons.length; j++)
        	{
        		String toSeason = toShow;
        		String season = Fseasons[j].getName();
        		toSeason = toSeason.concat("/" + season);
        		seasons.add(new Season(toAdd,season, toSeason));
        	}
        	shows.add(new Show(toAdd, seasons, toShow));
        }
        
        for(int i = 0; i < Fbooks.length; i++)
        {
        	toAdd = Fbooks[i].getName();
        	String toBook = ftpRootPath.concat("Books/");
        	toBook = toBook.concat(toAdd);
        	books.add(new Book(toAdd, toBook));
        }
	}
	
	public static ArrayList<Movie> getMovies()
	{
		return movies;
	}
	
	public static ArrayList<Show> getShows()
	{
		return shows;
	}
	
	public static ArrayList<Book> getBooks()
	{
		return books;
	}
	
	public static FTPClient getClient()
	{
		return ftpClient;
	}
	
	public static void main(String[] args) {
		/*Needed to initialize everything. Null Pointer Exception if not done!*/
		movies = new ArrayList<Movie>();
		shows = new ArrayList<Show>();
		books = new ArrayList<Book>();
		launch(args);
	}
}
