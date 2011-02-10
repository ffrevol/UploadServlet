package com.ffrevol.gui.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class OutputStreamDumper implements Dumper
{

	private PipedOutputStream out ;
	private PipedInputStream in ;
	
	public PipedInputStream input()
	{
		return in;
	}

	@Override
	public void Save(InputStream input) throws IOException
	{	
		out = new PipedOutputStream();
    	in = new PipedInputStream(out);
		int readed;
		byte[] b = new byte[512];
		while((readed = input.read(b )) > 0)
		{	
			out.write(b, 0, readed);			
		}
		out.close();
	}

}
