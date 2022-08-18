package csci201_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class createDB {
	public static void main(String[] args) throws ClassNotFoundException {
		// Defines the JDBC URL. As you can see, we are not specifying
		// the database name in the URL.
		String url = "jdbc:mysql://127.0.0.1:3306/sctea";

		// Defines username and password to connect to database server.
		String username = "root";
		String password = "password";

		// SQL command to create a database in MySQL.
		String sql = "CREATE DATABASE IF NOT EXISTS scteatest";
//		Connection conn = DriverManager.getConnection(String.format("jdbc:mysql://localhost/NameOfDatabase?user=%s&password=%s", sqlUsername, sqlPassword));
		System.out.print("aaa");
		try {Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn = DriverManager.getConnection(String.format("jdbc:mysql://127.0.0.1:3306/sctea?user=%s&password=%s", username, password));
				PreparedStatement stmt = conn.prepareStatement(sql);
			System.out.print("bbb");

			stmt.execute();
			System.out.print("ccc");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// SQL command to create tables
		String createUser = "CREATE TABLE `scteatest`.`user` (\n"
				+ "  `userID` INT NOT NULL AUTO_INCREMENT,\n"
				+ "  `password` VARCHAR(45) NULL,\n"
				+ "  `email` VARCHAR(45) NULL,\n"
				+ "  PRIMARY KEY (`userID`));";
		String createPost = "CREATE TABLE `scteatest`.`post` (\n"
				+ "  `postID` INT NOT NULL AUTO_INCREMENT,\n"
				+ "  `userID` VARCHAR(45) NULL,\n"
				+ "  `fakeName` VARCHAR(45) NULL,\n"
				+ "  `content` VARCHAR(45) NOT NULL,\n"
				+ "  `title` VARCHAR(45) NOT NULL,\n"
				+ "  `upvoteNum` INT NULL DEFAULT 0,\n"
				+ "  `timestamp` VARCHAR(45) NULL DEFAULT 'CURRENT_TIMESTAMP',\n"
				+ "  PRIMARY KEY (`postID`));";

		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement stmt = conn.prepareStatement(createUser)) {

			stmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement stmt = conn.prepareStatement(createPost)) {

			stmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}