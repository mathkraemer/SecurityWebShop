package org.thb.webshop.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.thb.webshop.model.Customer;
import org.thb.webshop.model.Item;
import org.thb.webshop.util.DataBaseUtil;

/**
 * Servlet implementation class SellServlet
 */
public class SellServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SellServlet() {
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
		
		DataBaseUtil dbUtil = null;
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
		
		Part part = request.getPart("picture");
		
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		
		try{
			InputStream inputStream = part.getInputStream();
			int i=0;
			while((i = inputStream.read())!=-1){
				bOut.write(i);
			}
		}catch(Exception e){
			throw new ServletException(e.getCause());
		}
		
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("customer");
		
		if(customer!=null){
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			String price = request.getParameter("price");
			
			Item item = new Item();
			item.setTitle(title);
			item.setDescription(description);
			item.setPrice(Double.valueOf(price));
			item.setSeller_id(customer.getId());
			byte[] pic = bOut.toByteArray();
			item.setPicture(pic);
			
			bOut.flush();
			bOut.close();
			try{
				boolean result = dbUtil.persistItem(item);
				if(result){
					request.setAttribute("msg", "Sie erfolgreich den Artikel mit dem Titel: "+title+" zum Kauf angeboten.");
				}else{
					request.setAttribute("msg", "Das Einstellen des Artikels mit dem Titel "+title+ " ist fehlgeschlagen.");
				}
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.forward(request, response);
			}catch(Exception e){
				throw new ServletException(e.getCause());
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	

}
