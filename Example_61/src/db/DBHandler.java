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
import java.util.Locale;
import java.util.ResourceBundle;
import helper.Utility;

public class DBHandler {
    private String dbName;
    private String dbUsername;
    private String dbPassword;
    private Connection dbConnection;
    private Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData resultSetMeatData;
    
    private  String dbServerURL;
    private String dbURL;
    private String selectQuery;
    private String dbDriver;
    private String serverTimeZone="?serverTimezone=";
    private String[] acceptedImageSuffixes;
    private ResourceBundle resourceBundle;
    
    private static String resourceDir, tempImageDir, tempImageDirAbsolute;
    private File tempImageDirAbsoluteFileObj;
    private String key;
      
      
    public DBHandler(String dbName, String dbUsername, String dbPassword) throws Exception {
    
        this.dbName = dbName;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        
        try {
            
        resourceBundle = ResourceBundle.getBundle("db.settings", new Locale(""));
        
        dbServerURL=resourceBundle.getString("db_server_url").trim();
        
        serverTimeZone = serverTimeZone +  resourceBundle.getString("server_time_zone").trim();
    
        dbDriver=resourceBundle.getString("db_driver").trim();
        selectQuery=resourceBundle.getString("select_query") + " ";
        
        //Here we specify the database full path as well as the time zone in which the query is executed.
        dbURL=dbServerURL + "/" + dbName + serverTimeZone;
        
        
        acceptedImageSuffixes=resourceBundle.getString("accepted_image_suffixes").trim().split(" ");
        key=resourceBundle.getString("encryption_key");
        
        
        }catch (Exception e) {
           throw new Exception();
        }
            
    }
    
    public static void setResourceDir(String resourceDir) {
        DBHandler.resourceDir=resourceDir;
    }
    
    public static void setTempImageDirAbsolute(String tempImageDirAbsolute) {
        DBHandler.tempImageDirAbsolute=tempImageDirAbsolute;
        
    }
    
    public static void setTempImageDir(String tempImageDir) {
        DBHandler.tempImageDir=tempImageDir + File.separator;
    
    }
    
    
    
    
    private String openConnection() {
        
        // For mySQL database the above code would look LIKE this:
        try {
            
            Class.forName(dbDriver);
            
            // Here we CREATE connection to the database
            dbConnection=DriverManager.getConnection(dbURL, dbUsername, dbPassword);
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
            return e.getLocalizedMessage();
        } catch (SQLException e) {
        	e.printStackTrace();
            return e.getLocalizedMessage();
        }
        
        
        return "";
        
    
            
        
    }
    
    private String closeConnection() {
        
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
        
        String query = selectQuery+ dbTableName;
        
        //Here we define SQL query to decrypt the aes_decrypted column and then convert it from HEX to char variable
        query = "SELECT * FROM "+ dbTableName;
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
                queryResult.append("<th> " + resultSetMeatData.getColumnName(i)
                  + "</th>");
              
               }
               queryResult.append("</tr>");
               queryResult.append("<tr>");
               for (int i = 1; i <= columns; i++) {
                   queryResult.append("<th> " + resultSetMeatData.getColumnTypeName(i)
                  + "</th>");
               }
               queryResult.append("</tr>");
               
               while (resultSet.next()) {
                   queryResult.append("<tr>");
                // Here we print the value of each column
                for (int i = 1; i <= columns; i++) {
                 if (resultSet.getObject(i) != null)
                     queryResult.append("<td>" + resultSet.getObject(i).toString()
                    + "</td>");
                 else
                     queryResult.append("<td>---</td>");
                }
                /*
                 * out.println("<td>" + resultSet.getString(1)+"</td>");
                 * out.println("<td>" + resultSet.getInt(2)+"</td>");
                 * out.println("<td>" + resultSet.getInt(3)+"</td>");
                 */
                queryResult.append("</tr>");
               }
               queryResult.append("</TABLE>");
               
        } catch (SQLException e) {
            queryResult.append(e.getMessage());
            
        }finally {
            queryResult.append(closeConnection());
        }
           
        return queryResult.toString();
    }
    
    
    
    
    public String createTable(String tableName) {
    
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName
                + " (ID INTEGER, ENCRYPTED_NAME BLOB, PHONE VARCHAR(20), ENCRYPTED TINYINT(1)    )";
        
        
        StringBuilder queryResult = new StringBuilder();
        queryResult.append("<h2>Results for '" + createTableQuery + "':</h2>");
        queryResult.append(openConnection());
        
           try {
               
               statement = dbConnection.createStatement();
               statement.executeUpdate(createTableQuery);
               
               queryResult.append("<p> Table '"+ tableName + "' was created successfully! ");
           } catch (SQLException e) {
               queryResult.append("<p>" + e.getMessage() + "</p>");
           }finally {
                queryResult.append(closeConnection());
            }
        
        return queryResult.toString();
    }
    
    
    
    public String createImageTable(String tableName) {
        
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName
                + " (NAME VARCHAR(40), IMAGE MEDIUMBLOB, IMAGE_SIZE INTEGER)";
        
        
        StringBuilder queryResult = new StringBuilder();
        queryResult.append("<h2>Results for '" + createTableQuery + "':</h2>");
        queryResult.append(openConnection());
        
           try {
               
               statement = dbConnection.createStatement();
               statement.executeUpdate(createTableQuery);
               
               queryResult.append("<p> Table '"+ tableName + "' was created successfully! ");
           } catch (SQLException e) {
               queryResult.append("<p>" + e.getMessage() + "</p>");
           }finally {
                queryResult.append(closeConnection());
            }
        
        return queryResult.toString();
    }
    
    
    public String insertData(String dbTableName, String tableRowData) {
        
         if (tableRowData == null || tableRowData.isEmpty())
               return Utility.getInvalidDataErrorMessage();
        
        
        StringBuilder queryResult = new StringBuilder();
        String[] columnData = tableRowData.split(";");
              // If enough data is not provided, we print the error message and quit.
              if (columnData.length < 3) {
                  queryResult.append("<p>Enough data was not provided!</p>");
                  return queryResult.toString();
              
              } else {
               
               if(!Utility.validateNumberParameter(columnData[0])) {
               
                   queryResult.append("<p>" + columnData[0] + " is not a valid number!</p>");
                   return queryResult.toString();
               }
               
               if(!Utility.validateTextParameter(columnData[1])) {
                
                   queryResult.append("<p>" + columnData[1] + " is not valid!</p>");
                   return queryResult.toString();
               }
               
               if(!Utility.validatePhoneNumber(columnData[2])){
                
                   queryResult.append("<p>" + columnData[2] + " is not a valid phone number!</p>");
                   return queryResult.toString();
               }
                
               
               
              }
              
              String insertCommand = "INSERT INTO " + dbTableName + " VALUES ("
                + Integer.parseInt(columnData[0])
                + ", AES_ENCRYPT('" + columnData[1] + "', '" + key + "'), '" + columnData[2] + "')";
              
              queryResult.append("<h2>Results for '" + insertCommand + "':</h2>");
                queryResult.append(openConnection());
                
                
                   try {
                       
                       statement = dbConnection.createStatement();
                       statement.executeUpdate(insertCommand);
                       queryResult.append("<p>Data was added to " + dbTableName
                                 + " successfully!");
                   } catch (SQLException e) {
                       queryResult.append("<p>" + e.getMessage() + "</p>");
                   }finally {
                        queryResult.append(closeConnection());
                    }
                
                
                return queryResult.toString();
            
        
    }
    
    
    public String preparedStatementInsertData(String dbTableName, String tableRowData, String encryptData) {
        
         if (tableRowData == null || tableRowData.isEmpty())
               return Utility.getInvalidDataErrorMessage();
        
        
        StringBuilder queryResult = new StringBuilder();
        String[] columnData = tableRowData.split(";");
              // If enough data is not provided, we print the error message and quit.
              if (columnData.length < 3) {
                  queryResult.append("<p>Enough data was not provided!</p>");
                  return queryResult.toString();
              } else {
               
               if(!Utility.validateNumberParameter(columnData[0])) {
               
                   queryResult.append("<p>" + columnData[0] + " is not a valid number!</p>");
                   return queryResult.toString();
               }
               
               if(!Utility.validateTextParameter(columnData[1])) {
                
                   queryResult.append("<p>" + columnData[1] + " is not valid!</p>");
                   return queryResult.toString();
               }
               
               if(!Utility.validatePhoneNumber(columnData[2])) {
                
                   queryResult.append("<p>" + columnData[2] + " is not a valid phone number!</p>");
                   return queryResult.toString();
               }
                
               
               
              }
              
              String insertCommand;
              insertCommand = "INSERT INTO " + dbTableName + " VALUES(?," +
                      "AES_ENCRYPT(?,'" + key + "'),?,?)";
              
              
        /*
         * if(encryptData.equalsIgnoreCase("on")) insertCommand = "INSERT INTO " +
         * dbTableName + " VALUES (?,?" + "AES_ENCRYPT(?,'" + key + "'),?,?)";
         *
         * else insertCommand = "INSERT INTO " + dbTableName + " VALUES (?,?,?,?)";
         */
              
              queryResult.append("<h2>Results for '" + insertCommand + "':</h2>");
                queryResult.append(openConnection());
                
              
            int updatedRows=0;
              try {
                
                  PreparedStatement ps = dbConnection.prepareStatement(insertCommand);
                          
               // Here we set the value for the first parameter
              // ps.setInt(1, Integer.parseInt(columnData[0]));
               ps.setInt(1, Integer.parseInt(columnData[0]));
               
               // Here we set the value for the second parameter
               ps.setString(2,  columnData[1]);
               
        
               // Here we set the value for the second parameter
               ps.setString(3,  columnData[2]);
        
              if(encryptData!=null)
                  ps.setBoolean(4, true);
              else
                ps.setBoolean(4, false);
            
               
    
               // Here we execute the PreparedStatement
               updatedRows = ps.executeUpdate();
               queryResult.append("<p>Data was added to " + dbTableName
                                 + " successfully; number of updated rows=" + updatedRows);
                   } catch (SQLException e) {
                       queryResult.append("<p>" + e.getMessage() + "</p>");
                   }finally {
                        queryResult.append(closeConnection());
                    }
                
                
                return queryResult.toString();
            
        
    }
    
    
    public String preparedStatemenUpdateData(String dbTableName, String tableRowData) {
        
         if (tableRowData == null || tableRowData.isEmpty())
               return Utility.getInvalidDataErrorMessage();
        
        
        StringBuilder queryResult = new StringBuilder();
        String[] columnData = tableRowData.split(";");
              // If enough data is not provided, we print the error message and quit.
              if (columnData.length < 3) {
                  queryResult.append("<p>Enough data was not provided!</p>");
                  return queryResult.toString();
              } else {
               
               if(!Utility.validateNumberParameter(columnData[0])) {
               
                   queryResult.append("<p>" + columnData[0] + " is not a valid number!</p>");
                   return queryResult.toString();
               }
               
               if(!Utility.validateTextParameter(columnData[1])) {
                
                   queryResult.append("<p>" + columnData[1] + " is not valid!</p>");
                   return queryResult.toString();
               }
               
               if(!Utility.validatePhoneNumber(columnData[2])) {
                
                   queryResult.append("<p>" + columnData[2] + " is not a valid phone number!</p>");
                   return queryResult.toString();
               }
                
               
               
              }
              
              String updateCommand = "UPDATE " + dbTableName + " SET ENCRYPTED_NAME=AES_ENCRYPT('?', '" + key + "'),"  + " PHONE=? WHERE ID=?";
              queryResult.append("<h2>Results for '" + updateCommand + "':</h2>");
                queryResult.append(openConnection());
                
              
            int updatedRows=0;
              try {
                  
    
                  
                  PreparedStatement ps = dbConnection.prepareStatement(updateCommand);
               
        
              
               // Here we set the value for the first parameter
               ps.setString(1,  columnData[1]);
               
               // Here we set the value for the third parameter
               ps.setString(2,  columnData[2]);
               
               // Here we set the value for the WHERE condition
               ps.setInt(3, Integer.parseInt(columnData[0]));
               
               
               // Here we execute the PreparedStatement
               updatedRows = ps.executeUpdate();
               
               queryResult.append("<p>Data was added to " + dbTableName
                                 + " successfully; number of updated rows=" + updatedRows);
                   } catch (SQLException e) {
                       queryResult.append("<p>" + e.getMessage() + "</p>");
                   }finally {
                        queryResult.append(closeConnection());
                    }
                
                
                return queryResult.toString();
            
        
    }
    
    //This method is used to check whether an image with a certain name is already
    //in the database or not  
     public String getImageName(String dbTableName, String imageName) {
        
        String query = "SELECT NAME FROM " + dbTableName + " WHERE lower(NAME) LIKE '" + imageName.toLowerCase() + "'";
    //    String query = "SELECT NAME FROM " + dbTableName + " WHERE lower(NAME) LIKE ?";
        
        
        StringBuilder queryResult = new StringBuilder();
        
        //queryResult.append(openConnection());
        
        
          // Here we CREATE the statement object for executing SQL commands.
           try {
               
        
            
            
            
            /*
             * PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
             *
             * preparedStatement.setString(1, imageName.trim());
             *
             *
             * //Here we execute the SQL query and save the results to a ResultSet object
             * resultSet = preparedStatement.executeQuery(query);
             */
            
            
               
            
            
              Statement statement = dbConnection.createStatement();
              resultSet=statement.executeQuery(query);
            
            
            
               
            /*
             * while(resultSet.next()) { queryResult.append(resultSet.getString(1)); }
             */
               
               //Here we return either true or false depending on whether the result SET is empty or not
                return resultSet.next()+"";
               
            //   return queryResult.toString();
                  
               
              
               
        } catch (SQLException e) {
            queryResult.append(e.getMessage());
        }finally {            
            //queryResult.append(closeConnection());
        }
           
        return queryResult.toString();
    }
//    public String WriteImageToDB(String dbTableName) {
//        
//        
//        //Here we make sure that the image TABLE is created in the database
//         createImageTable(dbTableName);
//        
//        StringBuilder queryResult = new StringBuilder();
//        String insertImageCommand = "INSERT INTO " + dbTableName + "(NAME, IMAGE, IMAGE_SIZE) VALUES(?,?,?)";
//    
//              queryResult.append("<h2>Results for '" + insertImageCommand + "':</h2>");
//              int updatedRows=0;
//                
//              try {
//              queryResult.append(openConnection());          
//            
//             // Here we initialise the preparedStatement object
//              PreparedStatement preparedStatement = dbConnection.prepareStatement(insertImageCommand);
//        
//        
//               // Here we get a list of available images under resourceDir
//               // directory. However, FROM the received list we only pick
//               // files with specified suffixes.
//               // String[] imageNames= new File(resourceDir).list(new
//               // imageNameFiletr(acceptedImages));
//               // Here we get the content of the resourceDir as a list of files.
//               File[] imageFiles = new File(resourceDir).listFiles(new ImageNameFiletr(acceptedImageSuffixes));
//               // File image =null;
//               FileInputStream fileInputStream = null;
//               
//               String checkImageNameResult="";
//            
//               for (int i = 0; i < imageFiles.length; i++) {
//                // Here we set the name of the file as the value of the first
//                // column.
//                
//                /*
//                 * checkImageNameResult=getImageName(dbTableName, imageFiles[i].getName());
//                 * //Here we check whether the file name is found FROM the database or not
//                 * //if(checkImageNameResult.equals(imageFiles[i].getName())) {
//                 * if(checkImageNameResult.equals("true")) { queryResult.append("<p>" +
//                 * checkImageNameResult + " is in the database.</p>"); continue;
//                 *
//                 * } else { queryResult.append("<p>" + checkImageNameResult +
//                 * " NOT in the database.</p>"); }
//                 */
//                   
//                
//                 //Here we check whether the file name is found FROM the database or not
//                   checkImageNameResult=getImageName(dbTableName, imageFiles[i].getName());
//                   if(checkImageNameResult.equals("true"))   
//                      continue;
//                
//                   /*
//                 * if(checkImageNameResult.equals("true")) {
//                 *
//                 * queryResult.append("<p>" + imageFiles[i].getName() + " --> " +
//                 * checkImageNameResult + "</p>"); } else queryResult.append("<p>" +
//                 * imageFiles[i].getName() + " --> " + checkImageNameResult + "</p>");
//                 */
//                        
//                   
//                preparedStatement.setString(1, imageFiles[i].getName());
//                // Here we define a file input stream for reading the content of
//                // the file
//                fileInputStream = new FileInputStream(imageFiles[i].getPath());
//                // Here we set the input stream for the file as the value for
//                // the second column.
//                preparedStatement.setBinaryStream(2, (InputStream) fileInputStream, (int) (imageFiles[i].length()));
//                //Here we set the length of the file as the value of the third column.
//                preparedStatement.setLong(3, imageFiles[i].length());
//                // Here we insert data to the TABLE and read the returned value
//                int counter = preparedStatement.executeUpdate();
//                // Here we close the file input stream.
//                fileInputStream.close();
//              
//                if (counter == 0)
//                    queryResult.append("<p>" + imageFiles[i].getName()
//                   + " data was not uploaded sucessfully!");
//                else
//                      updatedRows++;
// 
//              }
//              } catch (SQLException e) {
//                       queryResult.append("<p>" + e.getMessage() + "</p>");
//                   } catch (IOException e) {
//                        queryResult.append("<p>" + e.getMessage() + "</p>");
//        
//                   }finally {
//                        queryResult.append(closeConnection());
//                        
//                          queryResult.append("<p>" + updatedRows
//                                   + " row(s) were updated successfully in " + dbTableName + ".</p>");
//                    }
//                return queryResult.toString();
//            
//        
//    }
    
    
    
    
    public String readImageFromDB(String dbTableName) {
        
        StringBuilder queryResult = new StringBuilder();
        
               String imageQuery = "SELECT * FROM " + dbTableName;
               
               //Here we empty the temporary image directory
               tempImageDirAbsoluteFileObj = new File(tempImageDirAbsolute);
               if(tempImageDirAbsoluteFileObj.exists()) {
                   tempImageDirAbsoluteFileObj.delete();
                   
                   
               }
               
               tempImageDirAbsoluteFileObj.mkdirs();
               
               
        
              queryResult.append("<h2>Results for '" + imageQuery + "':</h2>");
              queryResult.append("<TABLE>");
              queryResult.append("<tr><th>Order</th><th>NAME</th><th>IMAGE</th><th>IMAGE SIZE</th></tr>");
              int imageCounter = 0;
              // In the following we read data FROM the database.
              try {
              queryResult.append(openConnection());    
              statement = dbConnection.createStatement();
               
               ResultSet resultSet = statement.executeQuery(imageQuery);
               File destinationFile = null;
               InputStream inputStream = null;
               FileOutputStream fileOutputStream = null;
               String name;
               
               while (resultSet.next()) {
                // Here we read the value of the first column of the TABLE.
                name = resultSet.getString(1);
                // Here we CREATE a File object, which refers to
                // the name read FROM the first column of the TABLE
                destinationFile = new File(tempImageDirAbsolute + name);
                // Here we prepare a FileOutputStream to write to the
                // destination file.
                fileOutputStream = new FileOutputStream(destinationFile);
                // Here we initialise the inputStream by reading FROM
                // the second column of the TABLE
                inputStream = resultSet.getBinaryStream(2);
                // Here we reserve memory area to read the image
                // content.
                byte[] imageBuffer = new byte[inputStream.available()];
                // Here we read the image data FROM the database to
                // the memory area.
                inputStream.read(imageBuffer);
                // Here write the image data FROM memory to the file.
                fileOutputStream.write(imageBuffer);
                // Here we close the output and input streams.
                fileOutputStream.close();
            
                // Here we read the size of the image FROM the third
                // column of the TABLE.
                long imageSize = resultSet.getLong(3);
                queryResult.append("<tr styel='text-align:center'><td>" + (imageCounter+1) + "</td><td>" + name + "</td>");
                queryResult.append("<td><img src='" + tempImageDir + name
                  + "' alt='Error' width='150' height='150'/></td>");
                queryResult.append("<td>" + imageSize + "</td></tr>");
               
                imageCounter++;
                
               }
               
              } catch (SQLException e) {
                       queryResult.append("<p>" + e.getMessage() + "</p>");
                   } catch (IOException e) {
                queryResult.append("<p>" + e.getMessage() + "</p>");
            }finally {
                        queryResult.append(closeConnection());
                        
                         queryResult.append("<p>" + imageCounter + " images were read FROM '" + dbTableName + "' TABLE.</p>");
                    }
              queryResult.append("</TABLE>");
                return queryResult.toString();
            
        
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
