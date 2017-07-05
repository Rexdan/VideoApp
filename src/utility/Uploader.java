package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import application.Main;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Book;

public class Uploader implements Runnable
{
	Stage stage;
	private ArrayList<Object> droppedItems;
	File file;
    InputStream inputStream;
    OutputStream outputStream;
    
    public Uploader(Stage stage, ArrayList<Object> droppedItems)
    {
    	this.stage = stage;
		this.droppedItems = droppedItems;
    }
	
	public void upload() throws Exception
	{
		if(droppedItems.get(0) instanceof Book)
		{
			Main.getClient().changeWorkingDirectory(Main.booksPath);
	    	/*The below will open up a popup window. This is what we want along with a progress indicator...*/
	    	final Stage dialog = new Stage();
	        dialog.initModality(Modality.NONE);
	        dialog.initOwner(stage);
	        VBox dialogVbox = new VBox(20);
	        Scene dialogScene = new Scene(dialogVbox, 300, 200);
	        final Label label = new Label("Upload Progress");
	        final ProgressBar progressBar = new ProgressBar(0);
	        dialogVbox.setSpacing(5);
	        dialogVbox.setAlignment(Pos.CENTER);
	        dialogVbox.getChildren().addAll(label, progressBar);
	        dialog.setScene(dialogScene);
            dialog.show();
	    	
	    	for(int i = 0 ; i < droppedItems.size(); i++)
	    	{
	    		Book book = (Book)droppedItems.get(i);
	    		file = new File(book.getBookPath());
	    		inputStream = new FileInputStream(file);
	    		outputStream = Main.getClient().storeFileStream(droppedItems.get(i).toString().concat(".pdf"));
	            byte[] bytesIn = new byte[(int) file.length()];
	            Integer read = 0;
	            while ((read = inputStream.read(bytesIn)) != -1) 
	            {
	                outputStream.write(bytesIn, 0, read);
	                progressBar.setProgress(read/bytesIn.length);
	            }
	            inputStream.close();
	            outputStream.close();
	    	}
	    	dialog.close();
		}
	}
	
	@Override
	public void run() 
	{
		try
		{
			upload();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
