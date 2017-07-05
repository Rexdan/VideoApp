package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Book;
import model.Video;

public class StringMod
{
	public static String getTitle(String input)
	{
		int end = input.lastIndexOf('.');
    	input = input.substring(0,end);
    	return input;
	}
	
	public static String getFTPpath(String input)
	{
		return input.replaceAll(" ", "%20");
	}
	
	public static String getNativePath(String input)
	{
		return input;
	}
	
	public static ObservableList<String> getAllNames(ObservableList<Object> input)
	{
		ObservableList<String> result = FXCollections.observableArrayList();
		
		if(input instanceof ObservableList<?>)
		{
			for(int i = 0; i < input.size(); i++)
			{
				if(((ObservableList<?>)input).get(i) instanceof Video)
				{
					Video v = (Video)input.get(i);
					result.add(v.toString());
				}
				if(((ObservableList<?>)input).get(i) instanceof Book)
				{
					Book b = (Book)input.get(i);
					result.add(b.toString());
				}
			}	
		}
		return result;
	}
}
