package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exceptions.ConexaoBancoException;



public class ConnectionFactory {
	
	private static ConnectionFactory instance;
	private Connection conexao;
	
	
    private static final String URL = "jdbc:mysql://localhost:3303/academia";
    private static final String USER = "root";
    private static final String PASSWORD = "bancodedados2016.2";
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    
	private ConnectionFactory() throws ConexaoBancoException {
		setConnection();
	}
	
	public static ConnectionFactory getInstance() throws ConexaoBancoException{
		if(instance == null){
			instance = new ConnectionFactory();
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
