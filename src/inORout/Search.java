package inORout;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import inORout.Business;
import inORout.Recipe;
import inORout.ResultsEdamamCall;
import inORout.ResultsYelpCall;


@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Vector<String> ids;
	Vector<String> keys;
	int numkeys;
	Vector<Business> YelpResults;
	Vector<Recipe> EdamamResults;
	String YelpBearerId;
	HttpServletRequest req;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String[] optionArray = request.getParameterValues("option");
		String option = null;
		if (optionArray != null) {
			option = String.join(",", optionArray);
		}
		String price = request.getParameter("price");
		String sortby = request.getParameter("sort");
		
		String searchTerm = request.getParameter("searchTerm");
		req = request;
		if (searchTerm == null || searchTerm.equals("")) {
			request.setAttribute("error", "No search term entered!");
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/HomePage.jsp");
			try {
				dispatch.forward(request, response);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}

		// read in config file with API keys
		YelpBearerId = "";
		try {
			FileReader fr = new FileReader(getServletContext().getRealPath("/WEB-INF/config.txt"));
			Properties p = new Properties();
			p.load(fr);

			YelpBearerId = p.getProperty("Yelp");
			ids = new Vector<String>();
			keys = new Vector<String>();
			keys.add(p.getProperty("EdamamKey"));
			keys.add(p.getProperty("EdamamKey2"));
			keys.add(p.getProperty("EdamamKey3"));
			keys.add(p.getProperty("EdamamKey4"));
			keys.add(p.getProperty("EdamamKey5"));
			keys.add(p.getProperty("EdamamKey6"));
			keys.add(p.getProperty("EdamamKey7"));
			keys.add(p.getProperty("EdamamKey8"));
			keys.add(p.getProperty("EdamamKey9"));
			keys.add(p.getProperty("EdamamKey10"));
			keys.add(p.getProperty("EdamamKey11"));
			ids.add(p.getProperty("EdamamId"));
			ids.add(p.getProperty("EdamamId2"));
			ids.add(p.getProperty("EdamamId3"));
			ids.add(p.getProperty("EdamamId4"));
			ids.add(p.getProperty("EdamamId5"));
			ids.add(p.getProperty("EdamamId6"));
			ids.add(p.getProperty("EdamamId7"));
			ids.add(p.getProperty("EdamamId8"));
			ids.add(p.getProperty("EdamamId9"));
			ids.add(p.getProperty("EdamamId10"));
			ids.add(p.getProperty("EdamamId11"));
			numkeys = keys.size();
		}
		catch (FileNotFoundException e) {
			System.out.println("Make sure you have a config.txt file!");
			response.sendError(500);
			return;
		}

		YelpResults = new Vector<Business>();
		EdamamResults = new Vector<Recipe>();

		
		// YELP API CALL
		ResultsYelpCall yc = new ResultsYelpCall(this, searchTerm, option, price, sortby);

		// EDAMAM API CALL
		ResultsEdamamCall ec = new ResultsEdamamCall(this, searchTerm, option);

		// pass search result arrays back
		try {
			yc.join();
			ec.join();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		Business[] YResults = new Business[YelpResults.size()];
		for (int i = 0; i < YelpResults.size(); i++) {
			YResults[i] = YelpResults.get(i);
		}
		Recipe[] EResults = new Recipe[EdamamResults.size()];
		for (int i = 0; i < EdamamResults.size(); i++) {
			EResults[i] = EdamamResults.get(i);
		}
		request.setAttribute("YelpResults", YResults);
		request.setAttribute("EdamamResults", EResults);
		request.setAttribute("searchType", request.getParameter("searchType"));
		request.setAttribute("searchTerm", searchTerm);
		System.out.println(request.getParameter("searchType"));
		System.out.println(request.getServletContext());
		// send it back
		// CHANGE THIS TO CORRECT PAGE FOR REDIRECT
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/SearchResults.jsp");
		try {
			dispatch.forward(request, response);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
			
	}

}