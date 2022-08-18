package csci201_project;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.google.gson.Gson;
//import com.google.gson.JsonSyntaxException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/loginauth")
public class LoginAuthServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String elissaPwd = "password";
	String user = "root";

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userEmail;
		String userPwd;

		if (!validate(request.getParameter("email"))) {
			request.getRequestDispatcher("/login.html").include(request, response);
			return;
		} else {
			userEmail = request.getParameter("email");
			userPwd = request.getParameter("password");
		}

		String sql = "SELECT * FROM scteatest.user WHERE email = '" + userEmail + "'";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager
					.getConnection(String.format("jdbc:mysql://127.0.0.1:3306/sctea?user=%s&password=%s", user, elissaPwd));
			Statement st = conn.createStatement();

			ResultSet rs1 = st.executeQuery("SELECT * FROM scteatest.post");

			ArrayList<post> postlist=new ArrayList<post>();
			while(rs1.next())
			{
				post post = new post(rs1.getInt("postID"), rs1.getString("userID"), rs1.getString("content"), rs1.getString("fakeName"), rs1.getString("timestamp"), rs1.getInt("upvoteNum"), rs1.getString("title"));
				postlist.add(post);
			}
			request.setAttribute("postlist", postlist);

			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				if (userEmail.equals(rs.getString("email")) && userPwd.equals(rs.getString("password"))) {
					request.getSession().setAttribute("email2", userEmail);
					request.getSession().setAttribute("loggedin", "true");
					request.getSession().setAttribute("userID", userEmail); 
					request.getRequestDispatcher("/landingpage.jsp").forward(request, response);
				}
				else {
					request.getRequestDispatcher("/login.html").include(request, response);	
				}
			} 
			else {
				request.getRequestDispatcher("/login.html").include(request, response);	
			}


		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}