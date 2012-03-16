package communication;

import java.sql.Connection;

import data.manager.DataDB;

public class DBCommunication {
	Connection conn = DataDB.getConnInstance();
	
	public String Query(String sql){
	
		return DataDB.runQuery(conn, sql);
	}
	public String CheckExistance(String sql){
		
		return DataDB.CheckExistence(conn, sql);
	}
	public void addService(int shost, String sensorType, String plataform){
		DataDB.insertService(conn, shost, sensorType, plataform);
	}
	public void addData(int shost, String sensorType, int data){
		DataDB.insertData(conn, shost, sensorType, data);
		
	}
	
}
