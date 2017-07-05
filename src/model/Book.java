package model;

import utility.StringMod;

public class Book 
{
	private String book;
	private String bookPath;
	
	public Book(String book, String bookPath)
	{
		this.book = StringMod.getTitle(book);
		if(bookPath.contains("ftp://"))
    		this.bookPath = StringMod.getFTPpath(bookPath);
    	else
    		this.bookPath = StringMod.getNativePath(bookPath);
	}
	
	public String getBookPath()
	{
		return this.bookPath;
	}
	
	public String toString()
	{
		return this.book;
	}
}
