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
import java.util.Random;

import com.github.javafaker.Faker;
import com.github.javafaker.FunnyName;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/postServlet")
public class postServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  


		PreparedStatement ps = null;

		String url       = "jdbc:mysql://127.0.0.1:3306/sctea";
		String elissaPwd = "password";
		String user = "root";
		// create a connection to the database
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager
					.getConnection(String.format("jdbc:mysql://127.0.0.1:3306/sctea?user=%s&password=%s", user, elissaPwd));

			Statement stat = null;
			stat = conn.createStatement();

			//		https://stackoverflow.com/questions/4221825/passing-a-variable-from-one-servlet-to-another-servlet
			//		request.setAttribute("array", array);
			//		request.getRequestDispatcher("/servleturl").include(request, response);

			String title=request.getParameter("title");  
			String content=request.getParameter("content");    

			if(title.isEmpty() && content.isEmpty())
			{
				request.setAttribute("Error", "bothEmpty");
				RequestDispatcher dispachter = request.getRequestDispatcher("/post.jsp");
				dispachter.forward (request, response);
				return;
			}
			else if(content.isEmpty()){
				request.setAttribute("Error", "contentEmpty");
				RequestDispatcher dispachter = request.getRequestDispatcher("/post.jsp");
				dispachter.forward (request, response);
				return;
			}
			else if(title.isEmpty())
			{
				request.setAttribute("Error", "titleEmpty");
				RequestDispatcher dispachter = request.getRequestDispatcher("/post.jsp");
				dispachter.forward (request, response);
				return;
			}

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Unable to create post");
				e.printStackTrace();
			}

			String email = (String) request.getSession().getAttribute("email2");
			// query the database to get the userID




			String sql = "Select userID from scteatest.user Where email='" + email+"'";
			ResultSet rs = stat.executeQuery(sql);
			String userID = null;
			if (rs.next())
			{
				userID = rs.getString("userID");
				//		    	System.out.println(userID);
			}
			else {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Please login/register');");
				out.println("</script>");
				RequestDispatcher dispachter = request.getRequestDispatcher("/login.html");
				dispachter.forward (request, response);
			}



			Faker faker = new Faker(new Random(24));
			ps = conn.prepareStatement("INSERT INTO scteatest.post (title, content, userID, fakeName) VALUES (?, ?, ?, ?)");


			String fakeName = faker.funnyName().name();
			ps.setString(1, title);
			ps.setString(2, content);
			ps.setString(3, userID);
			ps.setString(4, fakeName);
			int i = ps.executeUpdate();

			ResultSet rs1 = stat.executeQuery("SELECT * FROM scteatest.post");
			ArrayList<post> postlist=new ArrayList<post>();
			while(rs1.next())
			{
				post post = new post(rs1.getInt("postID"), rs1.getString("userID"), rs1.getString("content"), rs1.getString("fakeName"), rs1.getString("timestamp"), rs1.getInt("upvoteNum"), rs1.getString("title"));
				postlist.add(post);
			}
			request.setAttribute("postlist", postlist);

			RequestDispatcher dispachter = request.getRequestDispatcher("/landingpage.jsp");
			dispachter.forward (request, response);


			stat.close();
			conn.close();



			if(i > 0) {
				//                out.println("Thank you for posting!");


			}

		} catch(SQLException sqle) {
			System.out.println(sqle.getMessage());
			//response.getWriter().write(sqle.getMessage());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}

}  




