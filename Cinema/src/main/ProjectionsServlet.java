package main;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HallDAO;
import dao.MovieDAO;
import dao.ProjectionDAO;
import dao.UserDAO;
import model.EProjectionType;
import model.ERole;
import model.Hall;
import model.Movie;
import model.Projection;
import model.User;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProjectionsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		try {
			String movie = request.getParameter("movieFilter");
			int m; 
			if(movie != null && movie != "") {
				if(MovieDAO.findByName(movie) != null) {
					m = MovieDAO.findByName(movie).getId();
					//System.out.println("m postoji!!!!!!!");
				}else {
					//System.out.println("movie nije nadjen!!!!!!!");
					m = -1;
				}
			}else {
				m = -1;
			}
			
			
			String dateFrom = request.getParameter("dateFromFilter");
			String dateTo = request.getParameter("dateToFilter");	
			String minPrice = request.getParameter("minPriceFilter");
			double min;
			if(minPrice != null && minPrice != "") {
				min = Double.parseDouble(minPrice);
			}else {
				min = 0.00;
			}
			String maxPrice = request.getParameter("maxPriceFilter");
			double max;
			if(maxPrice != null && maxPrice != "") {
				max = Double.parseDouble(maxPrice);
			}else {
				max = 10000.00;
			}
			String hall = request.getParameter("hallFilter");
			int hallId;
			if(hall != null && hall != "") {
				hallId = HallDAO.findByName(hall).getId();
			}else {
				hallId = -1;
			}
			
			String projType = request.getParameter("projTypeFilter");
			if(projType != null && projType != "") {
				switch (projType) {
				case "2D":
					projType = "twodim";
				case "3D":
					projType = "threedim";
				case "4D":
					projType = "fourdim";
				}
			}
			
			
			List<Projection> projections = ProjectionDAO.getAll(m, dateFrom, dateTo, min, max, hallId, projType);
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("projections", projections);
				
		
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
			
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
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
			
			if (loggedInUser.getRole() != ERole.ADMIN || loggedInUser.isDeleted() == true) {
				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
				return;
			}
			String action = request.getParameter("action");
			switch (action) {
				case "add": {
					Movie movie = MovieDAO.findByName(request.getParameter("movie"));
					if(movie == null)
						throw new Exception("Wanted movie doesn't exist");
					EProjectionType pt = EProjectionType.valueOf(request.getParameter("projectionType"));
					if(pt == null)
						throw new Exception("The projection type doesn't exist");
					Hall h = HallDAO.findByName(request.getParameter("hall"));
					if(h == null)
						throw new Exception("Wanted hall doesn't exist");
					Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getParameter("datetime"));
					if(d == null)
						throw new Exception("Please enter date");
					User u = loggedInUser;
					if(u == null)
						throw new Exception("The User doesn't exist");
					double p = Double.parseDouble(request.getParameter("price"));
					if(p <= 0)
						throw new Exception("Price must be higher than 0");
					boolean deleted = false;
					
					Projection projection = new Projection(movie, pt, h, d, p, u, deleted);
					ProjectionDAO.add(projection);
					break;
				}
				case "update": {
					int id = Integer.getInteger(request.getParameter("id"));
					Movie movie = MovieDAO.findByName(request.getParameter("movie"));
					if(movie == null)
						throw new Exception("Wanted movie doesn't exist");
					EProjectionType pt = EProjectionType.valueOf(request.getParameter("projectionType"));
					if(pt == null)
						throw new Exception("The projection type doesn't exist");
					Hall h = HallDAO.findByName(request.getParameter("hall"));
					if(h == null)
						throw new Exception("Wanted hall doesn't exist");
					Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getParameter("datetime"));
					if(d == null)
						throw new Exception("Please enter date");
					User u = loggedInUser;
					if(u == null)
						throw new Exception("The User doesn't exist");
					double p = Double.parseDouble(request.getParameter("price"));
					if(p <= 0)
						throw new Exception("Price must be higher than 0");
					boolean deleted = false;
					
					
					Projection projection = new Projection(id, movie, pt, h, d, p, u, deleted);
					ProjectionDAO.add(projection);
					break;
				}
				case "delete": {
					int id = Integer.getInteger(request.getParameter("id"));
					Projection p = ProjectionDAO.get(id);
					if(p == null || p.isDeleted() == true) {
						throw new Exception("No such projection");
					}
					ProjectionDAO.delete(p);
					break;
				}
			}
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
		}

	}

}
