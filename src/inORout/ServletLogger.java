package inORout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import inORout.Business;
import inORout.Recipe;

/**
 * Servlet implementation class Search
 */
@WebServlet("/ServletLogger")
public class ServletLogger extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("hi");
		String logChoice = request.getParameter("logChoice");
		if(logChoice.equals("Login"))
		{
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/Login.jsp");
			try {
				dispatch.forward(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
		else if(logChoice.equals("Signup"))
		{
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/Register.jsp");
			try {
				dispatch.forward(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
		else if(logChoice.equals("Profile"))
		{
			RequestDispatcher rd = request.getRequestDispatcher("ProfileHelper");

			try {
				rd.forward(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
		else if(logChoice.equals("Signout"))
		{
			request.getSession().setAttribute("Current user", null);
			RequestDispatcher dispatch = request.getRequestDispatcher("/HomePage.jsp");
			try {
				dispatch.forward(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
		
	}
}