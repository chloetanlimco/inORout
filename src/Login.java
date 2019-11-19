

import java.io.*;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/* Front End change these values based on forms */
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		String next = "/Profile.jsp";
		HttpSession session = request.getSession();
		
		String error = "";
		
		//setting up JDBC
		Connection conn = null;
		PreparedStatement ps= null;
		ResultSet rs= null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://google/foodapp?cloudSqlInstance=groupproject-258805:us-central1:project201&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=anthonyuser&password=wQHL223i4LJhEuCl1");
			
			//check if user exists
			ps = conn.prepareStatement("SELECT * FROM Users WHERE username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			//if user doesn't already exist
			if (!rs.next()) {
				error += "This user does not exist.";
				next = "/Login.jsp";
			}
			
			//else check if password matches
			else {
				String check = rs.getString("password");
				//if wrong password
				if (!check.contentEquals(password)) {
					error += "Incorrect password.";
					next = "/Login.jsp";
				}
				//else login
				else {
					session.setAttribute("Current user", username);
					request.setAttribute("error", "Successful login!");
				}
			}
		} 
		catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} 
		finally {
			try {
				if (rs!= null) {
					rs.close();
				}
				if (ps!= null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} 
			catch (SQLException sqle) {
				System.out.println(sqle.getMessage());
			}
		}
		
		/* Front End grab this value to display error */ 
		request.setAttribute("login-error", error);
		
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(next);
		
		try {
			dispatch.forward(request, response);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch (ServletException e) {
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
