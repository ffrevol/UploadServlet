package com.ffrevol.gui.server;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadServer extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DESTINATION_FILE_PATH = "prov.xml";
	private Dumper dumper;
	
	public UploadServer()
	{		
		dumper = DefaultDumper();
	}

	public UploadServer(Map<String, Object> param)
	{
		initParameter(param);
	}

	private void initParameter(Map<String, Object> param)
	{		
		Object obj = param.get("dumper");
		if(obj == null)
		{
			dumper = DefaultDumper();
			return;
		}
		dumper = (Dumper) obj;
	}

	private Dumper DefaultDumper()
	{
		//String realPath = this.getServletContext().getRealPath(DESTINATION_FILE_PATH);
		System.out.println("Save input to " + DESTINATION_FILE_PATH);
		return new DumperToFile(DESTINATION_FILE_PATH);
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		PrintWriter out = resp.getWriter();
		FileReader prov = new FileReader(DESTINATION_FILE_PATH);
		char[] cbuf = new char[128];		
		while(prov.read(cbuf) > 0)
		{	 
			out.print(cbuf);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{		
		InputStream in = req.getInputStream();	
		dumper.Save(in);
		resp.setStatus(200);
	}
}
