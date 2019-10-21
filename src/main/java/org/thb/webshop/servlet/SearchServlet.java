package org.thb.webshop.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thb.webshop.model.Item;
import org.thb.webshop.util.DataBaseUtil;

/**
 * Servlet implementation class SearchServlet. The Servlet is responsible for returning the items for
 * the given search request.
 * @author Manuel Raddatz
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
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
		
		try {
			String searchText = request.getParameter("search");
			
			List<Item> items = dbUtil.findItemByText(searchText);
			
			request.getSession().setAttribute("items", items);
			request.getRequestDispatcher("search.jsp").forward(request, response);

		} catch (SQLException e) {
			System.out.println("Error searching Items: "+e.getMessage());
			System.out.println("Error searching Items: "+e.getLocalizedMessage());
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
