package db;

import java.sql.Connection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import formdata.MessageForm;
import helper.Utility;

public class DBHandler {
	private String dbName;
	private String dbUsername;
	private String dbPassword;
	private Connection dbConnection;
	private Statement statement;
	private ResultSet resultSet;
	private ResultSetMetaData resultSetMeatData;

	private String dbServerURL;
	private String dbURL;
	private String selectQuery;
	private String dbDriver;
	private String serverTimeZone = "?serverTimezone=";
	private String[] acceptedImageSuffixes;
	private ResourceBundle resourceBundle;

	private static String resourceDir, tempImageDir, tempImageDirAbsolute;
	private File tempImageDirAbsoluteFileObj;
	private String key;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public DBHandler() {

		try {
			resourceBundle = ResourceBundle.getBundle("db.settings", new Locale(""));
			dbServerURL = resourceBundle.getString("db_server_url").trim();
			dbUsername = resourceBundle.getString("user_name").trim();
			dbPassword = resourceBundle.getString("pass_word").trim();

			serverTimeZone = serverTimeZone + resourceBundle.getString("server_time_zone").trim();

			dbDriver = resourceBundle.getString("db_driver").trim();
			selectQuery = resourceBundle.getString("select_query") + " ";
			dbURL = dbServerURL + serverTimeZone;

			acceptedImageSuffixes = resourceBundle.getString("accepted_image_suffixes").trim().split(" ");
			key = resourceBundle.getString("encryption_key");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void setResourceDir(String resourceDir) {
		DBHandler.resourceDir = resourceDir;
	}

	public static void setTempImageDirAbsolute(String tempImageDirAbsolute) {
		DBHandler.tempImageDirAbsolute = tempImageDirAbsolute;

	}

	public static void setTempImageDir(String tempImageDir) {
		DBHandler.tempImageDir = tempImageDir + File.separator;

	}

	public String openConnection() {

		try {

			Class.forName(dbDriver);

			// Here we CREATE connection to the database
			dbConnection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		}

		return "";

	}

	public String closeConnection() {

		try {
			// Here we close all open streams
			if (statement != null)
				statement.close();
			if (dbConnection != null)
				dbConnection.close();
		} catch (SQLException sqlexc) {
			return "Exception occurred while closing database connection!";
		}

		return "";

	}

	public String getDataTable(String dbTableName) {

		String query = selectQuery + dbTableName;

		// Here we define SQL query to decrypt the aes_decrypted column and then convert
		// it from HEX to char variable
		query = "SELECT * FROM " + dbTableName;
		StringBuilder queryResult = new StringBuilder();
		queryResult.append("<h2>Results for '" + query + "':</h2>");
		queryResult.append("<TABLE style='border:1px solid black;'><tr>");

//        queryResult.append(openConnection());
		System.out.println(openConnection());

		// Here we CREATE the statement object for executing SQL commands.
		try {

			statement = dbConnection.createStatement();

			// Here we execute the SQL query and save the results to a ResultSet
			// object
			resultSet = statement.executeQuery(query);

			// Here we get the metadata of the query results
			resultSetMeatData = resultSet.getMetaData();
			// Here we calculate the number of columns
			int columns = resultSetMeatData.getColumnCount();
			// Here we print column names in TABLE header cells
			// Pay attention that the column index starts with 1
			System.out.println(columns);
			for (int i = 1; i <= columns; i++) {
				queryResult.append("<th> " + resultSetMeatData.getColumnName(i) + "</th>");

			}
			queryResult.append("</tr>");
			queryResult.append("<tr>");
			for (int i = 1; i <= columns; i++) {
				queryResult.append("<th> " + resultSetMeatData.getColumnTypeName(i) + "</th>");
			}
			queryResult.append("</tr>");

			while (resultSet.next()) {
				queryResult.append("<tr>");
				// Here we print the value of each column
				for (int i = 1; i <= columns; i++) {
					if (resultSet.getObject(i) != null)
						queryResult.append("<td>" + resultSet.getObject(i).toString() + "</td>");
					else
						queryResult.append("<td>---</td>");
				}
				/*
				 * out.println("<td>" + resultSet.getString(1)+"</td>"); out.println("<td>" +
				 * resultSet.getInt(2)+"</td>"); out.println("<td>" +
				 * resultSet.getInt(3)+"</td>");
				 */
				queryResult.append("</tr>");
			}
			queryResult.append("</TABLE>");

		} catch (SQLException e) {
			queryResult.append(e.getMessage());

		} finally {
			queryResult.append(closeConnection());
		}

		return queryResult.toString();
	}

	public String createImageTable(String tableName) {

		String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName
				+ " (NAME VARCHAR(200), MESSAGE VARCHAR(255), DATE VARCHAR(50), IMAGENAME VARCHAR(20), IMAGE MEDIUMBLOB)";

		StringBuilder queryResult = new StringBuilder();
		queryResult.append("<h2>Results for '" + createTableQuery + "':</h2>");
		queryResult.append(openConnection());

		try {

			statement = dbConnection.createStatement();
			statement.executeUpdate(createTableQuery);

			queryResult.append("<p> Table '" + tableName + "' was created successfully! ");
		} catch (SQLException e) {
			queryResult.append("<p>" + e.getMessage() + "</p>");
		} finally {
			queryResult.append(closeConnection());
		}

		return queryResult.toString();
	}

	public String WriteImageToDB(String dbTableName, Object ...data ) {

		// Here we make sure that the image TABLE is created in the database
		createImageTable(dbTableName);

		StringBuilder queryResult = new StringBuilder();
		String insertImageCommand = "INSERT INTO " + dbTableName + "(NAME,MESSAGE,DATE,IMAGENAME,IMAGE) VALUES(?,?,?,?,?)";

		queryResult.append("<h2>Results for '" + insertImageCommand + "':</h2>");
		int updatedRows = 0;

		try {
			queryResult.append(openConnection());

			// Here we initialise the preparedStatement object
			PreparedStatement preparedStatement = dbConnection.prepareStatement(insertImageCommand);

				preparedStatement.setString(1, (String) data[0]);
				preparedStatement.setString(2, (String) data[1]);
				preparedStatement.setString(3, (String) data[2]);
				preparedStatement.setString(4, (String) data[3]);
				preparedStatement.setBinaryStream(5, (InputStream) data[4]);
		
				int counter = preparedStatement.executeUpdate();
				// Here we close the file input stream.
//				fileInputStream.close();

//				if (counter == 0)
//					queryResult.append("<p>" + imageFiles[i].getName() + " data was not uploaded sucessfully!");
//				else
//					updatedRows++;

//			}
		} catch (SQLException e) {
			queryResult.append("<p>" + e.getMessage() + "</p>");
		} finally {
			queryResult.append(closeConnection());

			queryResult.append("<p>" + updatedRows + " row(s) were updated successfully in " + dbTableName + ".</p>");
		}
		return queryResult.toString();

	}

	public ArrayList<MessageForm> readImageFromDB(String dbTableName) {
		
		ArrayList<MessageForm> messageList = new ArrayList<MessageForm>();
		StringBuilder queryResult = new StringBuilder();

		String imageQuery = "SELECT * FROM " + dbTableName;

		tempImageDirAbsoluteFileObj = new File(tempImageDirAbsolute);
		if (tempImageDirAbsoluteFileObj.exists()) {
			tempImageDirAbsoluteFileObj.delete();

		}

		tempImageDirAbsoluteFileObj.mkdirs();

		// In the following we read data FROM the database.
		try {
			openConnection();
			statement = dbConnection.createStatement();
			ResultSet resultSet = statement.executeQuery(imageQuery);
			File destinationFile = null;
			InputStream inputStream = null;
			FileOutputStream fileOutputStream = null;
			
			while (resultSet.next()) {
				String name = resultSet.getString(1);
				String message= resultSet.getString(2);
				Date date= resultSet.getDate(3);
				String imageName= resultSet.getString(4);
				destinationFile = new File(tempImageDirAbsolute + imageName);
				fileOutputStream = new FileOutputStream(destinationFile);
				inputStream = resultSet.getBinaryStream(5);
				byte[] imageBuffer = new byte[inputStream.available()];
				inputStream.read(imageBuffer);
				fileOutputStream.write(imageBuffer);
				fileOutputStream.close();
				MessageForm messageForm = new MessageForm();
				messageForm.setFirstName(name);
				messageForm.setContentMessage(message);
				messageForm.setDate(formatter.format(date));
				messageForm.setImage(imageName);
				messageList.add(messageForm);

			}
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeConnection();

		}
		
		return messageList;
	

	}

	public String getDbServerURL() {
		return dbServerURL;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbUsername() {
		return dbUsername;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
}
