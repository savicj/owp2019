package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ERole;
import model.Movie;
import model.Projection;
import model.Ticket;
import model.User;
import dao.MovieDAO;
import dao.ProjectionDAO;
import dao.TicketDAO;
import dao.UserDAO;

public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//try {
			String movieID = request.getParameter("movieid");
			int id;
			if(movieID == null || movieID == "") {
				
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
			}else {
				id = Integer.parseInt(movieID);
				Movie movie = MovieDAO.get(id);
				
				Map<String, Object> data = new LinkedHashMap<>();
				data.put("movie", movie);
				
				request.setAttribute("data", data);
				request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			}
			
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
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
			
			String action = request.getParameter("action");
			switch(action) {
			case "add": {
				String name = request.getParameter("movie");
				Movie movie = MovieDAO.findByName(name);
				if(movie != null) {
					throw new Exception ("Movie with this name already exist.");
				}
				
				String director = request.getParameter("directors");
				ArrayList<String> directors = new ArrayList<String>();
				try {
					for(String d : director.split(",")) {
						directors.add(d);
					}
				}catch (Exception e){	
					System.out.println("Directors parameter doesn't contain ','.");
					directors.add(director);
				}
				String actor = request.getParameter("actors");
				ArrayList<String> actors = new ArrayList<String>();
				try {
					for(String d : actor.split(",")) {
						actors.add(d);
					}
				}catch (Exception e){	
					System.out.println("Actors parameter doesn't contain ','.");	
					actors.add(actor);
				}
				
				String genre = request.getParameter("genres");
				ArrayList<String> genres = new ArrayList<String>();
				try {
					for(String d : genre.split(",")) {
						genres.add(d);
					}
				}catch (Exception e){	
					System.out.println("Genres parameter doesn't contain ','.");	
					genres.add(genre);
				}
				String dur = request.getParameter("duration");
				int duration;
				if(dur != null && dur != "") {
					duration = Integer.parseInt(dur);
					if(duration <= 0) {
						duration = 30;
						System.out.println("Duration can't be less than 30.");
					}
				}else{
					duration = 30;
					System.out.println("Duration is null or empty, default 30.");					
				}
				String distributor = request.getParameter("distributor");
				String country = request.getParameter("country");
				String y = request.getParameter("year");
				int year;
				if(y != null && y != "") {
					year = Integer.parseInt(y);
					if(year <= 1950) {
						year = 2020;
						System.out.println("Year can't be less then 1950.");
					}
				}else{
					year = 2020;
					System.out.println("Year is null or empty, default 2020.");					
				}
				String overview = request.getParameter("overview");
				
				
				Movie newMovie = new Movie(name, directors, actors, genres, duration, distributor, country, year, overview);
				MovieDAO.add(newMovie);
				request.getRequestDispatcher("./SuccessServlet").forward(request, response);						
				break;
				
			}
			case "delete": {
				int id;
				String moveiId = request.getParameter("id");
				if(moveiId != null && moveiId != "") {
					id = Integer.parseInt(moveiId);
					Movie m = MovieDAO.get(id);
					if(m == null || m.isDeleted() == true) {
						throw new Exception("No such projection");
					}else {
						List<Projection> projection = ProjectionDAO.findByMovie(m);
						System.out.println(projection);
						if(projection.isEmpty()) {
							System.out.println("trebalo bi da je obrisano skroz");
							MovieDAO.delete(id);
						}else {
							
							m.setDeleted(true);
							MovieDAO.update(m);
							System.out.println("trebalo bi da je apdejtovano deleted");
						}
					}
				}
				request.getRequestDispatcher("./SuccessServlet").forward(request, response);
				break;
				
				
			}
			}
		} catch (Exception e) {
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
		}
		
		
	}

}
