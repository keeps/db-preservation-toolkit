/**
 * 
 */
package pt.gov.dgarq.roda.common.convert.db.modules.mySql.in;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pt.gov.dgarq.roda.common.convert.db.model.exception.UnknownTypeException;
import pt.gov.dgarq.roda.common.convert.db.model.structure.SchemaStructure;
import pt.gov.dgarq.roda.common.convert.db.model.structure.UserStructure;
import pt.gov.dgarq.roda.common.convert.db.modules.jdbc.in.JDBCImportModule;
import pt.gov.dgarq.roda.common.convert.db.modules.mySql.MySQLHelper;

/**
 * @author Luis Faria
 * 
 */
public class MySQLJDBCImportModule extends JDBCImportModule {
		
	/**
	 * MySQL JDBC import module constructor
	 * 
	 * @param hostname
	 *            the hostname of the MySQL server
	 * @param database
	 *            the name of the database to import from
	 * @param username
	 *            the name of the user to use in connection
	 * @param password
	 *            the password of the user to use in connection
	 */
	public MySQLJDBCImportModule(String hostname, String database,
			String username, String password) {
		super("com.mysql.jdbc.Driver", "jdbc:mysql://" + hostname + "/"
				+ database + "?" + "user=" + username + "&password=" + password, 
				new MySQLHelper());
	}

	/**
	 * MySQL JDBC import module constructor
	 * 
	 * @param hostname
	 *            the hostname of the MySQL server
	 * @param port
	 *            the port that the MySQL server is listening
	 * @param database
	 *            the name of the database to import from
	 * @param username
	 *            the name of the user to use in connection
	 * @param password
	 *            the password of the user to use in connection
	 */
	public MySQLJDBCImportModule(String hostname, int port, String database,
			String username, String password) {
		super("com.mysql.jdbc.Driver", "jdbc:mysql://" + hostname + ":" + port
				+ "/" + database + "?" + "user=" + username + "&password="
				+ password, new MySQLHelper());
	}
	
	protected List<SchemaStructure> getSchemas() 
			throws SQLException, ClassNotFoundException, UnknownTypeException {
		List<SchemaStructure> schemas = new ArrayList<SchemaStructure>();
		String schemaName = getConnection().getCatalog();
		schemas.add(getSchemaStructure(schemaName));
		return schemas;
	}
	
	protected String getReferencedSchema(String s) 
			throws SQLException, ClassNotFoundException {
		return (s == null) ? getConnection().getCatalog() : s;
	}
	
	protected List<UserStructure> getUsers() 
			throws  SQLException, ClassNotFoundException {
		List<UserStructure> users = new ArrayList<UserStructure>();
		ResultSet rs = getStatement().executeQuery(sqlHelper.getUsersSQL(null));
		while (rs.next()) {
			UserStructure user = new UserStructure(
					rs.getString(2) + "@" + rs.getString(1), null);
			users.add(user);
		}
		
		return users;
	}
}
