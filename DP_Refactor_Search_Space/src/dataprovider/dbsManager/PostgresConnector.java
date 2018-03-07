package dataprovider.dbsManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//REFACTOR - Lazy Class
  //SMELL: #SmellType(Lazy Class)
 public class PostgresConnector {

	private Connection connection;
	private Statement statement;

	public PostgresConnector() throws SQLException, ClassNotFoundException {
		createConnection();
	}

	//REFACTOR - Feature Envy
  //SMELL: #SmellType(Feature Envy)
 private void createConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/refactor", "postgres", "admin");
		statement = connection.createStatement();
	}

	public Statement getStatement() {
		return statement;
	}
}
