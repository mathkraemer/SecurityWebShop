package org.thb.webshop.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * 
 * @author Manuel Raddatz
 *
 */
public class UploadService implements Runnable  {
		
	private AsyncContext asyncContext;

	/**
	 * @param outStream
	 * @param inStream
	 */
	public UploadService(AsyncContext asyncContext) {
		super();
		this.asyncContext = asyncContext;
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		HttpServletRequest request = (HttpServletRequest) asyncContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
		
		OutputStream outStream = null;
		InputStream inStream = null;
		Part part = null;
		
		PrintWriter out = null;
		
		try {
			part = request.getPart("picture");
			outStream = new FileOutputStream(new File(part.getSubmittedFileName()));
			inStream = part.getInputStream();
			out = response.getWriter();
			
			byte[] b = new byte[1024];
			int i=0;
			while((i = inStream.read(b))!=-1){
				outStream.write(b, 0, i);
			}
			
			outStream.flush();
			out.write("true");
			
			asyncContext.complete();
		} catch (IOException e) {
			out.write("false");
		} catch (ServletException e) {
			out.write("false");
		}finally {
			try {
				outStream.close();
				inStream.close();
			} catch (IOException e) {
				out.write("false");
			}
		}	
	}

	int testme(String ts){
		int len = 0;
		if (ts != null){
			len = ts.length();
		}
		return len;
	}
}
