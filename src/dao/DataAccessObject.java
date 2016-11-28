package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class DataAccessObject {
	protected Connection getConnection() throws IOException {
		Connection connection = null;
		String user = "";
		String password = "";
		String url = "jdbc:mariadb://localhost:3306/";
		String driver = "org.mariadb.jdbc.Driver";

		try {
			Class.forName(driver).newInstance();
			try {
				connection = DriverManager.getConnection(url, user, password);

			} catch (SQLException e) {
				System.out.println("Connection not working.");
				System.out.println("SQLException: " + e.getMessage());
				System.out.println("SQLState: " + e.getSQLState());
				System.out.println("VendorError: " + e.getErrorCode());
			}
			return connection;
		} catch (Exception e) {
			return null;
		}
	}

	protected static void close(Statement statement, Connection connection) {
		try {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			System.out.println("Statement or connection null.");
		}

	}
}