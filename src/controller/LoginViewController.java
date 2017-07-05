package controller;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import utility.Stages;

public class LoginViewController {
	
	private Stage primaryStage;
	
	@FXML
	private Button login;
	@FXML
	private Button cancel;
	@FXML
	private TextField uname;
	@FXML
	private PasswordField pass;
	@FXML
	private Label error1, error2;
	
	private static FTPClient client;

	public void start(Stage primaryStage) 
	{
		this.error1.setVisible(false);
		this.error2.setVisible(false);
		this.primaryStage = primaryStage;
	}
	
	@FXML
	private void handleLoginClick(ActionEvent event) throws SocketException, IOException{

		handleLoadingUsers();
	}
	@FXML
	private void handleEnterLogin(KeyEvent event) throws SocketException, IOException
	{
		if (event.getCode().equals(KeyCode.ENTER))
			handleLoadingUsers();
	}	
	@FXML
	private void handleEnterExit(KeyEvent event) throws SocketException, IOException
	{
		if (event.getCode().equals(KeyCode.ENTER))
			handleExit();
	}
	@FXML //exits program
	private void handleCancelClick(ActionEvent event){

    		handleExit();
	}
	
	private void handleLoadingUsers() throws SocketException, IOException
	{
		String userName = uname.getText();
		String passWord = pass.getText();
		client = new FTPClient();
		client.connect("rexdan.duckdns.org", 64050);
		boolean isConnected = client.login(userName, passWord);
		
		if(!isConnected)
		{
			error1.setVisible(true);
			error2.setVisible(true);
		}
		else
		{
			Main.loadData(client);
			Stages.goToMain(primaryStage);
		}
	}
	
	private void handleExit()
	{
		primaryStage.close();
	}
}