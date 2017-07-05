package model;

import java.util.ArrayList;

public class Show 
{
	private String show;
	private String showPath;
	
	private ArrayList<Season> seasons;
	
	public Show(String show)
	{
		this.show = show;
	}
	
	public Show(String show, ArrayList<Season> seasons)
	{
		this.show = show;
		this.seasons = seasons;
	}
	
	public Show(String show, ArrayList<Season> seasons, String showPath)
	{
		this.show = show;
		this.seasons = seasons;
		this.showPath = showPath.replaceAll(" ", "%20");
	}
	
	public void addSeasons(ArrayList<Season> seasons)
	{
		this.seasons = seasons;
	}
	
	public void addSeason(Season season)
	{
		this.seasons.add(season);
	}
	
	public String getShowPath()
	{
		return this.showPath;
	}
	
	public ArrayList<Season> getSeasons()
	{
		return this.seasons;
	}
	
	public String toString()
	{
		return this.show;
	}
}
