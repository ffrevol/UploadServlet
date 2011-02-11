package com.ffrevol.gui.server;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

public class ServletTest
{
	private HttpServletRequest request;
	private HttpServletResponse response;
	private UploadServer servlet;

	@Before
	public void setUp()
	{
		 request = mock(HttpServletRequest.class);
		 response = mock(HttpServletResponse.class);	  
	}
	
	@Test
    public void doGet() throws Exception {
		
    	PipedOutputStream out = new PipedOutputStream();
    	PipedInputStream in = new PipedInputStream(out);
    	PrintWriter writer = new PrintWriter(out);
   	 	servlet = new UploadServer();
    	when(response.getWriter()).thenReturn(writer);    	
        servlet.doGet(request, response);
        writer.close();        
        BufferedReader d = new BufferedReader(new InputStreamReader(in));
        String val = d.readLine();
        assertTrue(val, val.startsWith("<PROV"));
    }
	
	
	@Test
	public void checkPostContent() throws Exception
	{	
		Map<String, Object> param = new HashMap<String, Object>();	
		OutputStreamDumper out = new OutputStreamDumper();
		param.put("dumper", out);
		servlet = new UploadServer(param);
		when(request.getInputStream()).thenReturn(createServletInputStream("<PROVISIONING>input text value</PROVISIONING>") );
		servlet.doPost(request, response);		
		BufferedReader d = new BufferedReader(new InputStreamReader(out.input()));
        String val = d.readLine();
        assertTrue(val, val.startsWith("<PROVISIONING>input"));
        verify(response).setStatus(200);
	}
	
	@Test
	public void checkPostFileCreation() throws Exception
	{	
		Map<String, Object> param = new HashMap<String, Object>();
		DumperToFile out = new DumperToFile("prov.xml");
		param.put("dumper", out);		
		servlet = new UploadServer(param);
		when(request.getInputStream()).thenReturn(createServletInputStream("<PROVISIONING>input text value</PROVISIONING>") );
		servlet.doPost(request, response);		
		File file = new File("prov.xml");
		assertTrue(file.getAbsolutePath(),file.canRead());
	}
	
	public ServletInputStream createServletInputStream(String
			object) throws Exception {   
			         final InputStream bais = new ByteArrayInputStream(object.getBytes());

			         return new ServletInputStream() {
			             @Override
			             public int read() throws IOException {			            	 
			                 return bais.read();
			             }
			         };
			     } 
	
}
