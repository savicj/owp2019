package main;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ERole;
import model.Movie;
import model.User;
import dao.MovieDAO;
import dao.UserDAO;

public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String name = request.getParameter("movieFilter");
			if(name == null || name == "")
				name = "%";
			String genre = request.getParameter("genreFilter");
			if(genre == null || genre == "")
				genre = "%";
			int durationFrom;
			String durationFromFilter = request.getParameter("minDurationFilter");
			if(durationFromFilter == null || durationFromFilter == "")
				durationFromFilter = "0";
			durationFrom = Integer.parseInt(durationFromFilter);
			int durationTo;
			String durationToFilter = request.getParameter("maxDurationFilter");
			if(durationToFilter == null || durationToFilter == "")
				durationToFilter = "500";
			durationTo = Integer.parseInt(durationToFilter);
			String distributor = request.getParameter("distributorFilter");
			if(distributor == null || distributor == "")
				distributor = "%";
			String country = request.getParameter("countryFilter");
			if(country == null || country == "")
				country = "%";
			String yearFromFilter= request.getParameter("fromYearFilter");
			int yearFrom;
			if(yearFromFilter == null || yearFromFilter == "") 
				yearFromFilter = "0";
			yearFrom = Integer.parseInt(yearFromFilter);
			String yearToFilter= request.getParameter("toYearFilter");
			int yearTo;
			if(yearToFilter == null || yearToFilter == "") 
				yearToFilter = "2020";
			yearTo = Integer.parseInt(yearToFilter);
			System.out.println(name +" "+ genre +" "+ durationFrom +" "+ durationTo+" "+ distributor+" "+ country+" "+ yearFrom+" "+ yearTo);

			
			List<Movie> movies = MovieDAO.getAll(name, genre, durationFrom, durationTo, distributor, country, yearFrom, yearTo);
			System.out.println(movies + " filmovi");
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("movies", movies);

			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUserName = (String) request.getSession().getAttribute("loggedInUserName");
		if (loggedInUserName == null) {
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		
		User loggedInUser = UserDAO.findByUsername(loggedInUserName);
		if (loggedInUser == null) {
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		
		if (loggedInUser.getRole() != ERole.ADMIN|| loggedInUser.isDeleted() == true) {
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
	}

}
