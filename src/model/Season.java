package model;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTPFile;

import application.Main;

public class Season 
{
	private String season;
	private String seasonPath;
	private ArrayList<Episode> episodes = new ArrayList<Episode>();
	
	public Season(String show, String season, String seasonPath) throws IOException
	{
		this.season = season;
		this.seasonPath = seasonPath.replaceAll(" ", "%20");
		String mediaPath = "/media/HDD/Shows/";
		mediaPath = mediaPath.concat(show+"/");
		mediaPath = mediaPath.concat(season);
		FTPFile[] Fepisodes = Main.getClient().listFiles(mediaPath);
		for(int i = 0; i < Fepisodes.length; i++)
		{
			String episode = Fepisodes[i].getName();
			String episodePath = seasonPath.concat("/" + episode);
			episodes.add(new Episode(episode, episodePath));
		}
	}
	
	public String getSeason()
	{
		return this.season;
	}
	
	public String getSeasonPath()
	{
		return this.seasonPath;
	}
	
	public ArrayList<Episode> getEpisodes()
	{
		return this.episodes;
	}
	
	public String toString()
	{
		return this.season;
	}
}
