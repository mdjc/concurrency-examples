package concurrency_inpractice_book.threadlocal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Be aware there's no way to close or free resources with threadLocal
 * http://stackoverflow.com/questions/1700212/how-to-force-a-java-thread-to-close-a-thread-local-database-connection
 * http://stackoverflow.com/questions/6466283/threadlocal-resource 
 * @author Mirna
 *
 */
public class ConnectionDispenser {
	 static String DB_URL = "jdbc:mysql://localhost/mydatabase";
	 
	 private ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {
		 protected Connection initialValue() {
			try {
				return DriverManager.getConnection(DB_URL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return null;
		 };
	 };
	 
	 public Connection getConnection() {
		 return connectionHolder.get();
	 }
}
