package csci201_project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/newpost")
public class newpost extends HttpServlet{


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		synchronized(this){
			Connection conn = null;
			PreparedStatement ps = null;
			PrintWriter out = response.getWriter();  
			String url       = "jdbc:mysql://127.0.0.1:3306/sctea";
			String sqluser   = "root";
			String sqlpwd    = "password";
			Statement stat = null;

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(url, sqluser, sqlpwd);
				stat = conn.createStatement();

				Statement mstat = null;
				mstat = conn.createStatement();

				ResultSet rs = stat.executeQuery("SELECT * FROM scteatest.post");

				ArrayList<post> postlist=new ArrayList<post>();
				while(rs.next())
				{
					post post = new post(rs.getInt("postID"),  rs.getString("userID"), rs.getString("content"), rs.getString("fakeName"), rs.getString("timestamp"), rs.getInt("upvoteNum"), rs.getString("title"));
					postlist.add(post);
				}
				request.getSession().setAttribute("loggedin", "false");
				request.setAttribute("postlist", postlist);
				RequestDispatcher dispachter = request.getRequestDispatcher("/landingpageFresh.jsp");
				dispachter.forward (request, response);

			}catch(SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}