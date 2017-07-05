package controller;

import java.io.IOException;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Episode;
import model.Season;
import model.Show;
import utility.Media;
import utility.Stages;

public class DualViewController 
{
	private Stage stage;
	private ObservableList<Season> seasons;
	private ObservableList<Episode> episodes;
	private Show selectedShow = OneViewController.getSelectedShow();
	private Season selectedSeason;
	private Episode selectedEpisode;
	
	public void start(Stage stage)
	{
		this.stage = stage;
		stage.setTitle("Seasons and Episodes");
	}
	
	@FXML
	private ListView<Season> seasonsList;
	
	@FXML
	private ListView<Episode> episodesList;
	
	@FXML
	private void initialize()
	{
		seasons = FXCollections.observableArrayList();
		int indexOfSelectedShow = Main.getShows().indexOf(selectedShow);
		seasons.addAll(Main.getShows().get(indexOfSelectedShow).getSeasons());
		seasonsList.setItems(seasons);
	}
	
	@FXML
	private void goBackToShows(ActionEvent event) throws IOException
	{
		Stages.goToShows(stage);
	}
	
	@FXML
	private void selectedSeason(MouseEvent event) throws IOException
	{
		if (event.getClickCount() == 1) 
		{
			episodes = FXCollections.observableArrayList();
			selectedSeason = seasonsList.getSelectionModel().getSelectedItem();
			episodes.addAll(selectedSeason.getEpisodes());
			episodesList.setItems(episodes);
		}
		if (event.getClickCount() == 2) 
		{
			Media.playSeason(selectedSeason);
		}
	}
	
	@FXML
	private void selectedEpisode(MouseEvent event) throws IOException
	{
		if (event.getClickCount() == 2) 
		{
			selectedEpisode = episodesList.getSelectionModel().getSelectedItem();
			Media.playVideo(selectedEpisode);
		}
	}
}
