package org.thb.webshop.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thb.webshop.util.DataBaseUtil;

/**
 * Servlet implementation class AutoCompleteServlet. The Servlet is responsible for updating the datalist
 * field of the search page. The search page implements an auto complete function.
 * @author Manuel Raddatz
 *
 */
public class AutoCompleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AutoCompleteServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String searchRequest = request.getParameter("search");
		
		DataBaseUtil dbUtil;
		try {
			dbUtil = new DataBaseUtil(request.getServletContext().getInitParameter("mysql-driver"),
					request.getServletContext().getInitParameter("mysql-url"), 
					request.getServletContext().getInitParameter("mysql-user"), 
					request.getServletContext().getInitParameter("mysql-password"));
		} catch (ClassNotFoundException e1) {
			throw new ServletException(e1.getMessage());
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		Set<String> matchingItems = null;
		try {
			matchingItems = dbUtil.getItemsFromRequest(searchRequest);
		} catch (SQLException e) {
			throw new ServletException(e.getMessage());
		}
		
		request.setAttribute("suggestions",matchingItems);
        RequestDispatcher rd = request.getRequestDispatcher("search.jsp");
        rd.forward(request, response);		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
