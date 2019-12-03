package inORout;

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
 * Servlet implementation class AddRemFav
 */
@WebServlet("/AddRemFav")
public class AddRemFav extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddRemFav() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		/* Pass in restaurant or recipe id */
		String username = (String) request.getSession().getAttribute("Current User");
		String restaurant = request.getParameter("restaurant");
		String recipe = request.getParameter("recipe");
		String next = "/Detail.jsp";
		
		String error = "";

		//if user not logged in
		if (username == null) {
			error += "Login to add to Favorites!";
		}
		else {
			
			Connection conn = null;
			PreparedStatement st = null;
			PreparedStatement lookup = null;
			ResultSet favSet = null;
			Boolean inFav = false;
			try {
				conn = DriverManager.getConnection("jdbc:mysql://google/foodapp?cloudSqlInstance=groupproject-258805:us-central1:project201&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=anthonyuser&password=wQHL223i4LJhEuCl1");
				
				if (restaurant != null) {
					//check if in favorites
					lookup = conn.prepareStatement("SELECT * FROM Restaurant WHERE restaurantID=? AND userID=(SELECT userID FROM User WHERE username=?)");
		    		lookup.setString(1, restaurant);
		    		lookup.setString(2, username);
		    		favSet = lookup.executeQuery();
		    		if (favSet.next()) {
		    			inFav = true;
		    		}
					
		    		//add to favorites
		    		if (!inFav) {
		    			st = conn.prepareStatement("INSERT INTO Restaurant (userID, restaurantID) VALUES((SELECT userID FROM User WHERE username=?),?)");
						st.setString(1, username);
						st.setString(2, restaurant);
						st.executeUpdate();
		    		}
		    		//remove from favorites
		    		else if (inFav) {
		    			st = conn.prepareStatement("DELETE FROM Restaurant (userID, restaurantID) VALUES((SELECT userID FROM User WHERE username=?),?)");
						st.setString(1, username);
						st.setString(2, restaurant);
						st.executeUpdate();
		    		}
					
				}
				else {
					//check if in favorites
					lookup = conn.prepareStatement("SELECT * FROM Recipe WHERE restaurantID=? AND userID=(SELECT userID FROM User WHERE username=?)");
		    		lookup.setString(1, recipe);
		    		lookup.setString(2, username);
		    		favSet = lookup.executeQuery();
		    		if (favSet.next()) {
		    			inFav = true;
		    		}
					
		    		//add to favorites
		    		if (!inFav) {
		    			st = conn.prepareStatement("INSERT INTO Recipe (userID, restaurantID) VALUES((SELECT userID FROM User WHERE username=?),?)");
						st.setString(1, username);
						st.setString(2, recipe);
						st.executeUpdate();
		    		}
		    		//remove from favorites
		    		else if (inFav) {
		    			st = conn.prepareStatement("DELETE FROM Recipe (userID, restaurantID) VALUES((SELECT userID FROM User WHERE username=?),?)");
						st.setString(1, username);
						st.setString(2, recipe);
						st.executeUpdate();
		    		}
		    		
				}
				
			}
			catch (SQLException sqle) {
				System.out.println(sqle.getMessage());
			}
			
			request.setAttribute("inFav", inFav);
		}
		
		request.setAttribute("fav-error", error);
		
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
