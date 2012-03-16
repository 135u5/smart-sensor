package data.manager;
/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Classe responsavel por manipular e manter o banco de dados.
 *
 * @version 1.0 01 Jan 2012
 * @author  José Renato da Silva Júnior
 **/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;



public class DataDB 
{
	private static String dbName = "Sensor_Data"; // Nome do banco de dados
	private static String dbURL = "jdbc:derby:/home/Databases/"+dbName+";create=true;"; // String para criação do banco de dados

	// jdbc Connection
	private static Connection conn = null;
	private static Statement stmt = null;
	private static PreparedStatement psInsert;

	/** Cria e retorna uma conexao com o banco de dados
	 * */
	public static Connection getConnInstance()
	{

		if (conn == null)
		{
			conn = createConnection();
			//deleteTable();
		}
		return conn;
	}

	/** Deleta as tabelas do banco de dados
	 * */
	@SuppressWarnings("unused")
	private static void deleteTable() {
		// TODO Auto-generated method stub

		try {
			stmt = conn.createStatement();
			stmt.execute("DROP TABLE "+"Data_READ");
			stmt.execute("DROP TABLE "+"Services");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** Cria uma conexao com o banco de dados
	 * */
	private static Connection createConnection()
	{
		try
		{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance(); // Carrega o driver do banco de dados embutido
			//Get a connection
			conn = DriverManager.getConnection(dbURL); // Cria a conexão

			System.out.println("Connected to database " + dbName);


			if (! check(conn)) // Verifica se o banco existe
			{  
				System.out.println (" . . . . creating tables");
				createTable(); // Caso não exista, cria o banco e as tabelas
			}
		}
		catch (Exception except)
		{
			except.printStackTrace();
		}
		return conn;
	}

	/** Checa a existencia do banco de dados
	 * */	
	public static boolean check (Connection conTst ) throws SQLException {
		try {
			Statement s = conTst.createStatement();
			s.execute("update DATA_READ set SENSOR_ID = -1, SENSOR_TYPE = 'TEST ENTRY', DATA = -100 where 1=3");
		}  catch (SQLException sqle) {
			String theError = (sqle).getSQLState();

			/** Se a tabela existir e retornado -  WARNING 02000: No row was found
			 * */
			if (theError.equals("42X05"))   // Tabela não existe
			{  return false;
			}  else if (theError.equals("42X14") || theError.equals("42821"))  {
				System.out.println("Check: Incorrect table definition. Drop table DATA_READ and rerun this program");
				throw sqle;   
			} else { 
				System.out.println("Check: Unhandled SQLException" );
				throw sqle; 
			}
		}

		return true;
	}  

	/** Cria as tabelas do banco de dados
	 * */
	private static void createTable()
	{
		String createStringData = "CREATE TABLE DATA_READ  "
			+  "(SENSOR_ID INT NOT NULL, "
			+  "SENSOR_TYPE VARCHAR(32) NOT NULL, "
			+  " DATA INT NOT NULL) " ;
		String createStringServices = "CREATE TABLE SERVICES  "
			+  "(SENSOR_ID INT NOT NULL, "
			+  "SENSOR_TYPE VARCHAR(32) NOT NULL, "
			+  "PLATAFORM VARCHAR(32) NOT NULL)";
		try {
			stmt = conn.createStatement();
			stmt.execute(createStringData);
			stmt.execute(createStringServices);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/** Insere o dado sensoreado na tabela DATA_READ
	 * */
	public static void insertData(Connection conn, int sensorId, String sensorType, int dataRead){      

		try {
			psInsert = conn.prepareStatement("insert into DATA_READ(SENSOR_ID, SENSOR_TYPE, DATA) values (?, ?, ?)");
			psInsert.setInt(1,sensorId);
			psInsert.setString(2,sensorType);
			psInsert.setInt(3,dataRead);
			psInsert.executeUpdate();
			psInsert.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** Insere os servicos oferecidos pelos motes na tabela SERVICES
	 * */
	public static void insertService(Connection conn, int sensorId, String sensorType, String plataform){
		try {
			psInsert = conn.prepareStatement("insert into SERVICES(SENSOR_ID, SENSOR_TYPE, PLATAFORM) values (?, ?, ?)");
			psInsert.setInt(1,sensorId);
			psInsert.setString(2,sensorType);
			psInsert.setString(3, plataform);
			psInsert.executeUpdate();
			psInsert.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** Executa a SQL passada no banco de dados e retorna uma string com a resposta
	 * */
	public static String runQuery(Connection conn, String sql)
	{
		String result = "";
		try
		{
			stmt = conn.createStatement();

			ResultSet results = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = results.getMetaData();
			int numberCols = rsmd.getColumnCount();

			for (int i=1; i<=numberCols; i++)
			{                

				result+=(rsmd.getColumnLabel(i)+"\t\t");  
			}
			result+=("\n");
			while(results.next())
			{

				for (int i = 1; i <= numberCols; i++)
				{
					result+=(results.getString(i)+"\t\t\t");

				}
				result+=("\n");
			}
			results.close();
			stmt.close();
		}
		catch (SQLException sqlExcept)
		{
			sqlExcept.printStackTrace();
		}
		//        System.out.println("Resultado: \n"+result);
		return result;
	}
	public static String CheckExistence(Connection conn, String sql)
	{
		String result = "";
		try
		{
			stmt = conn.createStatement();

			ResultSet results = stmt.executeQuery(sql);
			while(results.next())
			{
					result+=(results.getString(1));
			}
			results.close();
			stmt.close();
		}
		catch (SQLException sqlExcept)
		{
			sqlExcept.printStackTrace();
		}
//		System.out.println("Resultado: \n"+result);
		return result;
	}

	/** Fecha conexao com o banco de dados
	 * */
	public void shutdown()
	{
		try
		{
			if (stmt != null)
			{
				stmt.close();
			}
			if (conn != null)
			{
				DriverManager.getConnection(dbURL + ";shutdown=true");
				conn.close();
			}           
		}
		catch (SQLException sqlExcept)
		{

		}

	}
//		public static void main(String[] args) {
//	
//			Connection conn = getConnInstance();
//			deleteTable();
//			createTable();
//	
//		}

}