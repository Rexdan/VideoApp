package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.net.ftp.FTP;

import application.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Book;
import model.Movie;
import model.Show;
import model.Song;
import model.Video;
import utility.Stages;
import utility.StringMod;

public class UploadViewController extends Thread
{
	private ObservableList<String> mediaItems;
	private ObservableList<Object> droppedMovieItems;
	private ObservableList<Object> droppedShowItems;
	private ObservableList<Object> droppedMusicItems;
	private ObservableList<Object> droppedBookItems;
	private ArrayList<Movie> droppedMovies;
	private ArrayList<Show> droppedShows;
	private ArrayList<Song> droppedSongs;
	private ArrayList<Book> droppedBooks;
	private ArrayList<Object> droppedItems;
	
	private String mediaType;
	
	private static int read = 0;
	private static int length = 1;
	private int offSet = 0;
	private int currentRead = 0;
	private static ReentrantLock outputStreamLock, inputStreamLock, offSetLock, ftpConnectLock;
	final Stage dialog = new Stage();
	private boolean closedInput;
	private boolean closedOutput;
	private int uploading = 0;
	
	private static Stage stage;
	Task<Void> task;

	@FXML
	private Button upload;
	
	@FXML
	private Button goBack;
	
	@FXML
	private ListView<String> mediaList;
	
	@FXML
	private ListView<String> droppedList;
	
	@FXML
	private void goBackToMain() throws IOException
	{
		Stages.goToMain(stage);
	}
	
	@FXML
	private void draggingFile(DragEvent event)
	{
		Dragboard board = event.getDragboard();
		mediaType = (String) mediaList.getSelectionModel().getSelectedItem();
		if(board.hasFiles())
		{
			List<File> files = event.getDragboard().getFiles();
			String ext = "";
			if(mediaType.equals("Movies"))
			{
				/*Allowing for multiple files to be dragged into the screen.*/
				for(int i = 0; i< files.size(); i++)
	            {
	                ext = getExtension(files.get(i).getName());
	                if(!ext.equals("mkv"))
	                {
	                	event.consume();
	                	return;
	                }
	            }
				event.acceptTransferModes(TransferMode.COPY);
			}
			
			if(mediaType.equals("Books"))
			{
				/*Allowing for multiple files to be dragged into the screen.*/
				for(int i = 0; i< files.size(); i++)
	            {
	                ext = getExtension(files.get(i).getName());
	                if(!ext.equals("pdf"))
	                {
	                	event.consume();
	                	return;
	                }
	            }
				event.acceptTransferModes(TransferMode.COPY);
			}
		}
        event.consume();
	}
	
	@FXML
	private void draggedFile(DragEvent event)
	{
		final Dragboard db = event.getDragboard();
		boolean success = false;
		
		if(mediaType.equals("Movies"))
		{
			droppedMovieItems = FXCollections.observableArrayList();
			List<File> files = db.getFiles();

			for(int i = 0; i< files.size(); i++)
            {
				droppedMovies.add(new Movie(files.get(i).getName(), files.get(i).getAbsolutePath()));
            }
			droppedMovieItems.addAll(droppedMovies);
			droppedList.setItems(StringMod.getAllNames(droppedMovieItems));
            success = true;
		}
		
		if(mediaType.equals("Books"))
		{
			droppedBookItems = FXCollections.observableArrayList();
			List<File> files = db.getFiles();
			for(int i = 0; i< files.size(); i++)
            {
				droppedBooks.add(new Book(files.get(i).getName(), files.get(i).getAbsolutePath()));
            }
			droppedBookItems.addAll(droppedBooks);
			droppedList.setItems(StringMod.getAllNames(droppedBookItems));
            success = true;
		}
			
        event.setDropCompleted(success);
        event.consume();
	}

	private String getExtension(String fileName)
	{
        String extension = fileName;
        int i = extension.lastIndexOf('.');
        if (i > 0 && i < extension.length() - 1)
             return extension.substring(i + 1).toLowerCase();
        else return null;
    }
	
	@FXML
	private void selectedMedia(MouseEvent event) throws IOException
	{
		if (event.getClickCount() == 1) 
		{
			String selectedItem = mediaList.getSelectionModel().getSelectedItem();
			if(selectedItem.equals("Movies"))
			{
				droppedList.setItems(StringMod.getAllNames(droppedMovieItems));
			}
			if(selectedItem.equals("Shows"))
			{
				droppedList.setItems(StringMod.getAllNames(droppedShowItems));
			}
			if(selectedItem.equals("Books"))
			{
				droppedList.setItems(StringMod.getAllNames(droppedBookItems));
			}
			if(selectedItem.equals("Music"))
			{
				droppedList.setItems(StringMod.getAllNames(droppedMusicItems));
			}
		}
	}
	
	/*THE FUNCTION BELOW IS IN PROGRESS*/
	
	@FXML
	private void uploadClicked() throws Exception
	{	
		droppedItems = new ArrayList<Object>();
		
		if(!droppedBooks.isEmpty())
		{
			for(int i = 0 ; i < droppedBooks.size(); i++) droppedItems.add(droppedBooks.get(i));
		}
		
		if(!droppedSongs.isEmpty())
		{
			for(int i = 0 ; i < droppedSongs.size(); i++) droppedItems.add(droppedSongs.get(i));
		}
		
		if(!droppedMovies.isEmpty())
		{
			for(int i = 0 ; i < droppedMovies.size(); i++) droppedItems.add(droppedMovies.get(i));
		}
		
		if(!droppedShows.isEmpty())
		{
			for(int i = 0 ; i < droppedShows.size(); i++) droppedItems.add(droppedShows.get(i));
		}
		
		dialog.initModality(Modality.NONE);
		dialog.initOwner(stage);
		dialog.setTitle("Upload Progress");
		VBox dialogVbox = new VBox(20);
		Scene dialogScene = new Scene(dialogVbox, 300, 200);
		final Label totalUploadLabel = new Label("Total Upload Progress");
		final ProgressBar totalUploadProgressBar = new ProgressBar(0);
		/*Need at least the first one...*/
		final Label currentUploadLabel = new Label("Current Upload Progress For: " + droppedItems.get(0).toString());
		final ProgressBar currentUploadProgressBar = new ProgressBar(0);
		
		dialogVbox.setSpacing(5);
		dialogVbox.setAlignment(Pos.CENTER);
		dialogVbox.getChildren().addAll(totalUploadLabel, totalUploadProgressBar, currentUploadLabel, currentUploadProgressBar);
		dialog.setScene(dialogScene);
		dialog.show();
		
		/*Starting up the workers. Will create both JavaFX Application Threads and Background Daemon Threads.*/
		
		for(int i = 0; i < droppedItems.size(); i++)
		{
			startWorker(droppedItems.get(i), totalUploadProgressBar, currentUploadProgressBar, currentUploadLabel);
		}
	}
	
	public void startWorker(Object droppedItem, ProgressBar totalUploadProgressBar, ProgressBar currentUploadProgressBar, Label currentUploadLabel) throws InterruptedException
	{
		//if(Platform.isFxApplicationThread()) System.out.println("Java FX Application Thread.");
		//else System.out.println("Not a Java FX Application Thread.");
    	//long threadId = Thread.currentThread().getId();
        //System.out.println("Thread # " + threadId + " is doing this task");
		
		outputStreamLock.lock();
		closedOutput = false;
		outputStreamLock.unlock();
		inputStreamLock.lock();
		closedInput = false;
		inputStreamLock.unlock();
		
		Runnable task = new Runnable()
		{
			public void run()
			{
				try
				{
					runTask(droppedItem, totalUploadProgressBar, currentUploadProgressBar, currentUploadLabel);
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		/*Run the task in the background thread.*/
		Thread backgroundThread = new Thread(task);
		/*Terminate the thread if the application exits!*/
		backgroundThread.setDaemon(true);
		/*Start it...*/
		backgroundThread.start();
	}
	
	public synchronized void runTask(Object droppedItem, ProgressBar totalUploadProgressBar, ProgressBar currentUploadProgressBar, Label currentUploadLabel) throws Exception
	{
		uploading++;
		File file = null;
		
		if(droppedItem instanceof Book)
		{
			Main.getClient().changeWorkingDirectory(Main.booksPath);   
	    	Book book = (Book)droppedItem;
	    	file = new File(book.getBookPath());
		}
		
		if(droppedItem instanceof Video)
		{
			Video video = (Video)droppedItem;
			if(video instanceof Movie)
				Main.getClient().changeWorkingDirectory(Main.moviesPath);
			else
				Main.getClient().changeWorkingDirectory(Main.showsPath);
	    	file = new File(video.getVideoPath());
		}

    	FileInputStream inputStream = new FileInputStream(file);
    	/*The call below is for something to consider in the future--it allows for simultaneous file uploads.*/
    	//Main.getClient().storeFile(file.getName(), inputStream);
    	
    	/*if(Platform.isFxApplicationThread()) System.out.println("Java FX Application Thread.");
		else System.out.println("Not a Java FX Application Thread.");
		long threadId = Thread.currentThread().getId();
		System.out.println("Thread # " + threadId + " is doing this task");*/

    	OutputStream outputStream = Main.getClient().storeFileStream(droppedItem.toString().concat(".pdf"));

    	byte[] bytesIn = new byte[(int) file.length()];
    	length = bytesIn.length;
    	read = 0;
    	
    	byte [] bytes = new byte[1024];
    	
    	while ((read = inputStream.read(bytes)) != -1)
    	{
    		currentRead += read;
    		//System.out.println("The file size: " + length + " Our pos in the fd: " + currentRead + " Our offset: " + offSet + " Our read: " + read);
    		Platform.runLater(new Runnable(){
    			private volatile boolean shutdown;
    			
    			public void shutdown()
    			{
    				shutdown = true;
    			}
    			
    			@Override
    			public void run()
    			{
    				while(!shutdown)
    				{
    					/*get the TOTAL progress...*/
	    				currentUploadLabel.setText(droppedItem.toString());
	    				totalUploadProgressBar.setProgress((double)uploading/droppedItems.size());
	    				currentUploadProgressBar.setProgress((double)currentRead/length);
	    				shutdown();
    				}
    				
    			}});
    		outputStream.write(bytes, 0, read);
    		outputStream.flush();
    	}
    	
    	closeInputStream(inputStream);
    	closeOutputStream(outputStream);
    	/*This is so that we may call storeFileStream again.*/
    	Main.getClient().completePendingCommand();
    	
    	/* Closing the dialog box after successful writing.
    	 * Must be done this way because dialog is in a JavaFX Application Thread.*/
    	if(outputStreamClosed() && uploading == droppedItems.size())
    	{
    		Platform.runLater(new Runnable(){

				@Override
				public void run()
				{
					dialog.close();
				}});
    	}
	}
	
	/*The stream functions below may be implemented later for multiple files...*/
	public synchronized void closeOutputStream(OutputStream outputStream) throws IOException 
	{
		outputStream.close();
	    closedOutput = true;
	}
	
	public boolean outputStreamClosed()
	{
		return closedOutput;
	}
	
	public synchronized void closeInputStream(InputStream inputStream) throws IOException 
	{
		inputStream.close();
		closedInput = true;
	}
	
	public boolean inputStreamClosed()
	{
		return closedInput;
	}
	
	@FXML
	private void initialize()
	{
		mediaItems = FXCollections.observableArrayList();
		mediaItems.add("Movies");
		mediaItems.add("Shows");
		mediaItems.add("Books");
		mediaItems.add("Music");
		mediaList.setItems(mediaItems);
		mediaList.getSelectionModel().select(0);
	}
	
	public void start(Stage stage) throws IOException
	{
		UploadViewController.stage = stage;
		stage.setTitle("File Upload");
		Main.getClient().setFileType(FTP.BINARY_FILE_TYPE);
        droppedMovies = new ArrayList<Movie>();
        droppedShows = new ArrayList<Show>();
        droppedSongs = new ArrayList<Song>();
        droppedBooks = new ArrayList<Book>();
        outputStreamLock = new ReentrantLock();
        inputStreamLock = new ReentrantLock();
        //offSetLock = new ReentrantLock();
        //ftpConnectLock = new ReentrantLock();
	}
}
