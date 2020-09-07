package main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HallDAO;
import dao.MovieDAO;
import dao.ProjectionDAO;
import model.Projection;

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
					System.out.println("m postoji!!!!!!!");
				}else {
					System.out.println("movie nije nadjen!!!!!!!");
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
				//hallId = Integer.parseInt(hall);
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
		doGet(request, response);
	}

}
