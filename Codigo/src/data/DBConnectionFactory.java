package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exceptions.ConexaoBancoException;



public class DBConnectionFactory {
	
	private static DBConnectionFactory instance;
	private Connection conexao;
	
	
    private static final String URL = "jdbc:mysql://localhost:3303/academia?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "bancodedados2016.2";
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    
	private DBConnectionFactory() throws ConexaoBancoException {
		setConnection();
	}
	
	public static DBConnectionFactory getInstance() throws ConexaoBancoException{
		if(instance == null){
			instance = new DBConnectionFactory();
		}
		return instance;
		
	}
    private void setConnection() throws ConexaoBancoException{
    	
			try {
				Class.forName(DRIVER_CLASS).newInstance();
				conexao =  DriverManager.getConnection(URL,USER,PASSWORD);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			} 
			catch(SQLException e){
				throw new ConexaoBancoException();
			}
    }
    
	public Connection getConnection() throws ConexaoBancoException{
		
		this.setConnection();
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
