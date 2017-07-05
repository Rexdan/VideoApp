package model;

import utility.StringMod;

public class Video 
{
	private String videoPath;
	private String video;
	
	public Video(String video, String videoPath)
	{
    	this.video = StringMod.getTitle(video);
    	if(videoPath.contains("ftp://"))
    		this.videoPath = StringMod.getFTPpath(videoPath);
    	else
    		this.videoPath = StringMod.getNativePath(videoPath);
	}
	
	public Video(String video)
	{
		this.video = video;
	}
	
	public String getVideoPath()
	{
		return this.videoPath;
	}
	
	public void setVideoPath(String videoPath)
	{
		this.videoPath = videoPath;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Video)
		{
			Video v = (Video)o;
			if(v.toString().equals(this.video))
				return true;
		}
		return false;
	}
	
	public String toString()
	{
		return this.video;
	}
}
