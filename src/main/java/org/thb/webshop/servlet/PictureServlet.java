package org.thb.webshop.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thb.webshop.util.DataBaseUtil;

/**
 * Servlet implementation class PictureServlet. The Servlet is responsible retrieving
 * the picture from database for the given item id. 
 * @author Manuel Raddatz
 *
 */
public class PictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PictureServlet() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		DataBaseUtil dbUtil;
		try {
			dbUtil = new DataBaseUtil(request.getServletContext().getInitParameter("mysql-driver"),
					request.getServletContext().getInitParameter("mysql-url"), 
					request.getServletContext().getInitParameter("mysql-user"), 
					request.getServletContext().getInitParameter("mysql-password"));
		} catch (ClassNotFoundException e) {
			throw new ServletException(e.getCause());
		} catch (SQLException e) {
			throw new ServletException(e.getCause());
		}
		
		String itemId = request.getParameter("id");
			
		String selectPictureSQL =  "select picture from securitywebshopdb.item where id = ?";
			
		PreparedStatement stmt = null;
		try {
			stmt = dbUtil.getCon().prepareStatement(selectPictureSQL);
			stmt.setLong(1, Long.valueOf(itemId));
			
			ResultSet result = stmt.executeQuery();
				
			while(result.next()){
				Blob blob = result.getBlob("picture");
				response.reset();
				
				int arrayLength = (int) blob.length();
				response.setHeader("Content-Length", String.valueOf(arrayLength));
				
				InputStream in = blob.getBinaryStream();
								
				byte[] buffer = new byte[256];
				
				ServletOutputStream outStream = response.getOutputStream();
				
				while((arrayLength = in.read(buffer))!=-1){
					outStream.write(buffer, 0, arrayLength);
				}
				in.close();
				outStream.flush();
				blob = null;
			}
			stmt.close();
			dbUtil.getCon().close();
		} catch (SQLException e) {
			throw new ServletException(e.getCause());
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
