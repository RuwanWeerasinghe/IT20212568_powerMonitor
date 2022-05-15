package model;

import java.sql.*;

public class Power
{	
	//A common method to connect to the DB
	private Connection connect()
	{
		Connection con = null;
	
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			//Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", "root12345");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return con;
	}
	
	
	public String readPower()
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for reading."; 
			}
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Meter No</th><th>Meter Reading</th>" +
			"<th>Units</th>" +
			"<th>Reading Date</th>" +
			"<th>Update</th><th>Remove</th></tr>";
			
			String query = "select * from power_monitor";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next())
			{
				String monitorId = Integer.toString(rs.getInt("monitorId"));
				String meterNo = rs.getString("meterNo");
				String meterReading = rs.getString("meterReading");
				String units = Integer.toString(rs.getInt("units"));
				String readingDate = rs.getString("readingDate");
				
				// Add into the html table
				//output += "<tr><td><input id ='hidItemIDUpdate' name ='hidItemIDUpdate' type='hidden' value='" + monitorId + " '>"	+ Name + "</td>";
				
				output += "<tr><td>" + meterNo + "</td>";
				output += "<td>" + meterReading + "</td>";
				output += "<td>" + units + "</td>";
				output += "<td>" + readingDate + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' id ='" + monitorId + " ' type='button' value='Update' class=' btnUpdate btn btn-secondary'></td><td>"
				 		+ "<input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-UserId='"+ monitorId + " '>" + "</td></tr>";  
			}
			
			con.close();
			
			// Complete the html table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the  power_monitors.";
			System.err.println(e.getMessage());
		}
		
		return output;
		
	}
	
	public String insertPower(String meterNo, String meterReading, String units, String readingDate)
	{
		String output = "";
	
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for inserting."; 
			}
		
			
			// create a prepared statement
			String query = " insert into power_monitor "
						+ "(`monitorId`,`meterNo`, `meterReading` ,`units`,`readingDate`)"
						+ " values (?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, meterNo);
			preparedStmt.setString(3, meterReading);
			preparedStmt.setInt(4, Integer.parseInt(units));
			preparedStmt.setString(5, readingDate);			
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			//output = "Inserted successfully";
			String newPower = readPower();
			output = "{\"status\":\"success\", \"data\": \"" + newPower + "\"}";
		}
		catch (Exception e)
		{
			//output = "Error while inserting the Facility power_monitor.";
			//System.err.println(e.getMessage());
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the  power_monitor.\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	public String updatePower(int monitorId, String meterNo, String meterReading, String units, String readingDate)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for updating."; 
			}
			
			// create a prepared statement
			String query = "UPDATE power_monitor SET meterNo=?,meterReading=?,units=?,readingDate=?"
			+ "WHERE monitorId=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, meterNo);
			preparedStmt.setString(2, meterReading);
			preparedStmt.setInt(3, Integer.parseInt(units));
			preparedStmt.setString(4, readingDate);
			preparedStmt.setInt(5, monitorId);
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			//output = "Updated successfully";
			String newPower = readPower();
			output = "{\"status\":\"success\", \"data\": \"" +newPower + "\"}";
		}
		catch (Exception e)
		{
			//output = "Error while updating the Facility power_monitor.";
			//System.err.println(e.getMessage());
			output = "{\"status\":\"error\", \"data\":\"Error while updating the Facility power_monitor.\"}";
			System.err.println(e.getMessage()); 
		}
		
		return output;	
	}
	
	public String deletePower(int monitorId)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for deleting."; 
			}
			
			// create a prepared statement
			String query = "delete from power_monitor where monitorId=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			//preparedStmt.setInt(1, Integer.parseInt(monitorId)); 
			preparedStmt.setInt(1, monitorId); 
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			//output = "Deleted successfully";
			String newPower = readPower();
			output = "{\"status\":\"success\", \"data\": \"" + newPower + "\"}";
		}
		catch (Exception e)
		{
			//output = "Error while deleting the Facility power_monitor";
			//System.err.println(e.getMessage());
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the  power_monitor.\"}"; 
			System.err.println(e.getMessage()); 
		}
		
		return output;
		
	}
}
		

