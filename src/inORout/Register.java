package inORout;

import java.io.File;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmpass = request.getParameter("confirmpass");
		String next = "/Register.jsp";

		String error = "";
		if (!password.contentEquals(confirmpass)) {
			error += "The passwords do not match. ";
		} 
		// if username already taken
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			DriverManager.setLoginTimeout(2);
			conn = DriverManager.getConnection("jdbc:mysql://google/foodapp?cloudSqlInstance=groupproject-258805:us-central1:project201&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=anthonyuser&password=wQHL223i4LJhEuCl1");
			st = conn.prepareStatement("SELECT * from User WHERE username=?");
			st.setString(1, username);
			rs = st.executeQuery();
			while (rs.next()) {
				if (username.contentEquals(rs.getString("username"))) {
					error += "This username is already taken.";
				}
			}
			
			// no error
			if (error.contentEquals("")) {
				st.execute("INSERT INTO User (username, password) VALUES (\"" + username + "\" , \"" + password + "\")" );
				// log in user
				request.getSession().setAttribute("Current User", username);
				
				next = "/HomePage.jsp";
			}
		}
		catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
			

		request.setAttribute("error", error);
		
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(next);
		
		try {
			dispatch.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}