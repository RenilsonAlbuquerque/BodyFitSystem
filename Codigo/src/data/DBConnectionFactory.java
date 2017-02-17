package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exceptions.ConexaoBancoException;



public class DBConnectionFactory {
	
	private static DBConnectionFactory instance;
	private Connection conexao;
	
	
    private static final String URL = "jdbc:mysql://localhost:3303/academia?autoReconnect=true&useSSL=false";
    private static final String USER = "usuario";
    private static final String PASSWORD = "usuario54321";
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    
	private DBConnectionFactory() throws SQLException{
		setConnection();
	}
	
	public static DBConnectionFactory getInstance() throws SQLException{
		if(instance == null){
			instance = new DBConnectionFactory();
		}
		return instance;
		
	}
    private void setConnection()throws SQLException {
    	
		if (this.conexao == null) {
			try {
				Class.forName(DRIVER_CLASS).newInstance();
				conexao = DriverManager.getConnection(URL, USER, PASSWORD);
				conexao.setAutoCommit(false);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
			
    }
    
	public Connection getConnection() throws SQLException{
		

		this.setConnection();
		this.conexao.setAutoCommit(false);
		return conexao;
	}
	
	public void closeConnetion() throws ConexaoBancoException{
		try{
			this.conexao.close();
		}
		catch (SQLException e) {
			throw new ConexaoBancoException();
		}
		
	}

}
