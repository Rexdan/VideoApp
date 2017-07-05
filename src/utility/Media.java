package utility;

import java.io.File;
import java.io.IOException;
import model.Book;
import model.Season;
import model.Video;

public class Media 
{
	public static void playVideo(Object v) throws IOException
	{
		if(v instanceof Video)
		{
			Video video = (Video)v;
			String toPlay = new String();
			toPlay = "";
			File varTmpDir;
			/*Didn't make use of is64Bit method because it's not reliable. Better to just see if the file exists and hope that user's followed standard installation.*/
			/*Will add other OS later...*/
			if(CheckOS.isWindows())
			{
				toPlay = "\"";
				toPlay = toPlay.concat(video.getVideoPath());
				toPlay = toPlay.concat("\"");
				varTmpDir = new File("C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe");
				String commandSuffix = " --fullscreen --meta-title=\"";
				commandSuffix = commandSuffix.concat(v.toString() + "\" ");
				commandSuffix = commandSuffix.concat(toPlay);
				String command = new String();
				boolean exists = varTmpDir.exists();
				
				if(exists)
					command = command.concat("C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe");
				else
					command = command.concat("C:\\Program Files\\VideoLAN\\VLC\\vlc.exe");
				
				command = command.concat(commandSuffix);
				Runtime.getRuntime().exec(command);
			}
			if(CheckOS.isMac())
			{	
				String command = "//Applications//VLC.app//Contents//MacOS//VLC --fullscreen ";
				toPlay = toPlay.concat(video.getVideoPath());
				command = command.concat(toPlay);
				Runtime.getRuntime().exec(command);
			}
		}
	}
	
	public static void playSeason(Season selectedSeason) throws IOException
	{
		String toPlay = new String();
		File varTmpDir;
		
		if(CheckOS.isWindows())
		{
			varTmpDir = new File("C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe");
			String commandSuffix = " --fullscreen ";
			
			for(int i = 0; i < selectedSeason.getEpisodes().size()-1; i++)
			{
				toPlay = "\"";
				toPlay = toPlay.concat(selectedSeason.getEpisodes().get(i).getVideoPath());
				toPlay = toPlay.concat("\" ");
				commandSuffix = commandSuffix.concat(toPlay);
			}
			
			toPlay = "\"";
			toPlay = toPlay.concat(selectedSeason.getEpisodes().get(selectedSeason.getEpisodes().size()-1).getVideoPath());
			toPlay = toPlay.concat("\"");
			
			commandSuffix = commandSuffix.concat(toPlay);
			String command = new String();
			boolean exists = varTmpDir.exists();
			
			if(exists)
				command = command.concat("C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe");
			else
				command = command.concat("C:\\Program Files\\VideoLAN\\VLC\\vlc.exe");
			command = command.concat(commandSuffix);
			System.out.println(command);
			Runtime.getRuntime().exec(command);
		}
		if(CheckOS.isMac())
		{
			String command = "//Applications//VLC.app//Contents//MacOS//VLC ";
			String commandSuffix = " --fullscreen ";
			for(int i = 0; i < selectedSeason.getEpisodes().size()-1; i++)
			{
				toPlay = toPlay.concat(selectedSeason.getEpisodes().get(i).getVideoPath());
				toPlay = toPlay.concat(" ");
				commandSuffix = commandSuffix.concat(toPlay);
				toPlay = "";
			}
			
			toPlay = toPlay.concat(selectedSeason.getEpisodes().get(selectedSeason.getEpisodes().size()-1).getVideoPath());
			commandSuffix = commandSuffix.concat(toPlay);
			command = command.concat(commandSuffix);
			Runtime.getRuntime().exec(command);
		}
	}
	
	public static void openBook(Book book) throws IOException
	{
		if(CheckOS.isWindows())
		{
			String command = "cmd.exe /C start \"\" /max ";
			command = command.concat("\"" + book.getBookPath() + "\"");
			Runtime.getRuntime().exec(command);
		}
	}
}
