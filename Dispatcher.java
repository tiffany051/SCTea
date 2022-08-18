package csci201_project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/Dispatcher")
public class Dispatcher extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (email.contentEquals("") || password.contentEquals("")) { // later will also need to check that the user exists in our database
			out.println("<h4> Empty paramters are not allowed </h4>");
			request.getRequestDispatcher("login.html").include(request, response);
		} else {
			request.getRequestDispatcher("Submitted").forward(request, response);
		}
	}

}
