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

@WebServlet("/registerauth")
public class RegisterAuthServlet extends HttpServlet {
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
			request.setAttribute("Error", "emailError");
			request.getRequestDispatcher("/register.jsp").include(request, response);
			return;
		} 
		else if(request.getParameter("password").isEmpty())
		{
			request.setAttribute("Error", "PasswordError");
			request.getRequestDispatcher("/register.jsp").include(request, response);
			return;
		}
		else {
			userEmail = request.getParameter("email");
			userPwd = request.getParameter("password");
		}

		String sql = "SELECT * FROM scteatest.User WHERE email = '" + userEmail + "'";

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
				if (userEmail.equals(rs.getString("email"))) {
					request.setAttribute("Error", "emailinDB");
					request.getRequestDispatcher("/login.jsp").include(request, response);
					return;
				}
			} 
			else {
				String insertSql = "INSERT INTO scteatest.USER (email, password) VALUES ('" + userEmail + "', '" + 
						userPwd + "');";
				st.executeUpdate(insertSql);
				// go home, but to the User version
				request.getSession().setAttribute("loggedin", "true");
				request.getRequestDispatcher("/landingpage.jsp").forward(request, response);
			}

			st.close();
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}