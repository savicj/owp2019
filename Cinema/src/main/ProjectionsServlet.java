package main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import dao.ProjectionDAO;
import model.Projection;
import model.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//@SuppressWarnings("serial")
public class ProjectionsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String loggedInUser = (String) request.getSession().getAttribute("loggedInUser");
//		if(loggedInUser == null) {
//			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
//			return;
//		}
//		
//		try {
//			User loggedUser = UserDAO.findByUsername(loggedInUser);
//			if(loggedUser == null) {
//				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
//				return;
//			}
//			
		try {
			
			List<Projection> projections = ProjectionDAO.getAll();
			System.out.println(projections);
			
			
//			Map<String, Object> data = new LinkedHashMap<>();
//			data.put("projections", projections);
//			
//			
//			request.setAttribute("data", data);
//			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
			
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
