package com.ffrevol.gui.server;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DumperToFile implements Dumper
{

	private final String realPath;

	public DumperToFile(String path)
	{
		this.realPath = path;
	}

	@Override
	public void Save(InputStream in) throws IOException
	{
		BufferedReader read = new BufferedReader(new InputStreamReader(in));
		FileWriter writer = new FileWriter(realPath);
		char[] cbuf = new char[128];
		int readc;
		while((readc = read.read(cbuf)) > 0)
		{	
			System.out.println("print " + readc + " char" + cbuf); 
			writer.write(cbuf);
		}		
		writer.close();

	}

}
