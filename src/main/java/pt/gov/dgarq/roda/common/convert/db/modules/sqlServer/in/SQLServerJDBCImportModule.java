package pt.gov.dgarq.roda.common.convert.db.modules.sqlServer.in;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.transaction.util.FileHelper;
import org.apache.log4j.Logger;

import pt.gov.dgarq.roda.common.FileFormat;
import pt.gov.dgarq.roda.common.convert.db.model.data.BinaryCell;
import pt.gov.dgarq.roda.common.convert.db.model.data.Cell;
import pt.gov.dgarq.roda.common.convert.db.model.data.FileItem;
import pt.gov.dgarq.roda.common.convert.db.model.exception.InvalidDataException;
import pt.gov.dgarq.roda.common.convert.db.model.exception.ModuleException;
import pt.gov.dgarq.roda.common.convert.db.model.structure.type.SimpleTypeBinary;
import pt.gov.dgarq.roda.common.convert.db.model.structure.type.Type;
import pt.gov.dgarq.roda.common.convert.db.modules.jdbc.in.JDBCImportModule;
import pt.gov.dgarq.roda.common.convert.db.modules.sqlServer.SQLServerHelper;

import com.microsoft.sqlserver.jdbc.SQLServerConnection;
import com.microsoft.sqlserver.jdbc.SQLServerResultSet;

/**
 * Microsoft SQL Server JDBC import module.
 * 
 * @author Luis Faria
 */
public class SQLServerJDBCImportModule extends JDBCImportModule {

	private final Logger logger = 
			Logger.getLogger(SQLServerJDBCImportModule.class);

	/**
	 * Create a new Microsoft SQL Server import module using the default
	 * instance.
	 * 
	 * @param serverName
	 *            the name (host name) of the server
	 * @param database
	 *            the name of the database we'll be accessing
	 * @param username
	 *            the name of the user to use in the connection
	 * @param password
	 *            the password of the user to use in the connection
	 * @param integratedSecurity
	 *            true to use windows login, false to use SQL Server login
	 * @param encrypt
	 *            true to use encryption in the connection
	 */
	public SQLServerJDBCImportModule(String serverName, String database,
			String username, String password, boolean integratedSecurity,
			boolean encrypt) {
		super("com.microsoft.sqlserver.jdbc.SQLServerDriver",
				"jdbc:sqlserver://" + serverName + ";database=" + database
						+ ";integratedSecurity="
						+ (integratedSecurity ? "true" : "false") + ";encrypt="
						+ (encrypt ? "true" : "false"), new SQLServerHelper());
		
		System.setProperty("java.net.preferIPv6Addresses", "true");

	}

	/**
	 * Create a new Microsoft SQL Server import module using the instance name.
	 * The constructor using the port number is preferred over this to avoid a
	 * round-trip to the server to discover the instance port number.
	 * 
	 * @param serverName
	 *            the name (host name) of the server
	 * @param instanceName
	 *            the name of the instance
	 * @param database
	 *            the name of the database we'll be accessing
	 * @param username
	 *            the name of the user to use in the connection
	 * @param password
	 *            the password of the user to use in the connection
	 * @param integratedSecurity
	 *            true to use windows login, false to use SQL Server login
	 * @param encrypt
	 *            true to use encryption in the connection
	 */
	public SQLServerJDBCImportModule(String serverName, String instanceName,
			String database, String username, String password,
			boolean integratedSecurity, boolean encrypt) {
		super("com.microsoft.sqlserver.jdbc.SQLServerDriver",
				"jdbc:sqlserver://" + serverName + "\\" + instanceName
						+ ";database=" + database + ";user=" + username
						+ ";password=" + password + ";integratedSecurity="
						+ (integratedSecurity ? "true" : "false") + ";encrypt="
						+ (encrypt ? "true" : "false"), new SQLServerHelper());

	}

	/**
	 * Create a new Microsoft SQL Server import module using the port number.
	 * 
	 * @param serverName
	 *            the name (host name) of the server
	 * @param portNumber
	 *            the port number of the server instance, default is 1433
	 * @param database
	 *            the name of the database we'll be accessing
	 * @param username
	 *            the name of the user to use in the connection
	 * @param password
	 *            the password of the user to use in the connection
	 * @param integratedSecurity
	 *            true to use windows login, false to use SQL Server login
	 * @param encrypt
	 *            true to use encryption in the connection
	 */
	public SQLServerJDBCImportModule(String serverName, int portNumber,
			String database, String username, String password,
			boolean integratedSecurity, boolean encrypt) {
		super("com.microsoft.sqlserver.jdbc.SQLServerDriver",
				"jdbc:sqlserver://" + serverName + ":" + portNumber
						+ ";database=" + database + ";user=" + username
						+ ";password=" + password + ";integratedSecurity="
						+ (integratedSecurity ? "true" : "false") + ";encrypt="
						+ (encrypt ? "true" : "false"), new SQLServerHelper());

	}

	protected Statement getStatement() throws SQLException,
			ClassNotFoundException {
		if (statement == null) {
			statement = ((SQLServerConnection) getConnection())
					.createStatement(
							SQLServerResultSet.TYPE_SS_SERVER_CURSOR_FORWARD_ONLY,
							SQLServerResultSet.CONCUR_READ_ONLY);
		}
		return statement;
	}
	
	
	@Override
	protected Set<String> getIgnoredImportedSchemas() {
		Set<String> ignored = new HashSet<String>();
		ignored.add("db_.*");
		ignored.add("sys");
		ignored.add("INFORMATION_SCHEMA");
		return ignored;
	}
	
	protected Type getLongvarbinaryType(String typeName, int columnSize,
			int decimalDigits, int numPrecRadix) {
		Type type; 
		if (typeName.equals("image")) {
			type = new SimpleTypeBinary("MIME", "image");
		} else {
			type = new SimpleTypeBinary();
		}
		type.setSql99TypeName("BINARY LARGE OBJECT");
		return type;
	}
	
	protected Cell convertRawToCell(String tableName, String columnName,
			int columnIndex, int rowIndex, Type cellType, ResultSet rawData)
			throws SQLException, InvalidDataException, ClassNotFoundException,
			ModuleException {
		Cell cell;
		String id = tableName + "." + columnName + "." + rowIndex;
		if (cellType instanceof SimpleTypeBinary) {
			InputStream input = rawData.getBinaryStream(columnName);
			if (input != null) {
				logger.debug("SQL ServerbinaryStream: " + columnName);
				FileItem fileItem = new FileItem();
				try {
					FileHelper.copy(input, fileItem.getOutputStream());
					// List<FileFormat> formats = FileFormatHelper.getInstance()
					// .identify(fileItem);
					// logger.debug("cell '" + id + "' has formats " + formats);
					List<FileFormat> formats = new Vector<FileFormat>();
					cell = new BinaryCell(id, fileItem, formats);
				} catch (IOException e) {
					throw new ModuleException("Error getting binary stream", e);
				}
			} else {
				cell = new BinaryCell(id, null);
			}

		} else {
			cell = super.convertRawToCell(tableName, columnName, columnIndex,
					rowIndex, cellType, rawData);
		}
		return cell;
	}

	@Override
	protected String processTriggerEvent(String string) {
		char[] charArray = string.toCharArray();
		
		String res = "INSTEAD OF";		
		if (charArray[0] == '1') {
			res = "AFTER";
		}
		return res;
	}

	@Override
	protected String processActionTime(String string) {
		char[] charArray = string.toCharArray();
		
		String res = "";
		if (charArray[0] == '1') {
			res = "INSERT";
		} 
		
		if (charArray[1] == '1') {
			if (!res.equals("")) {
				res += " OR ";
			}
			res = "UPDATE";
		}
		
		if (charArray[2] == '1') {
			if (!res.equals("")) {
				res += " OR ";
			}
			res = "DELETE";
		}
		return res;		
	}	
}
