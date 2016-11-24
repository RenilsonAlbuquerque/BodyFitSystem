package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnectionFactory {
	
	private static ConnectionFactory instance;
	private Connection conexao;
	
	
    private static final String URL = "jdbc:mysql://localhost:3306/academia";
    private static final String USER = "root";
    private static final String PASSWORD = "bancodedados2016.2";
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    
	private ConnectionFactory() throws Exception{
		setConnection();
	}
	
	public static ConnectionFactory getInstance()throws Exception{
		if(instance == null){
			instance = new ConnectionFactory();
		}
		return instance;
		
	}
    private void setConnection()throws Exception{
    	try{
			Class.forName(DRIVER_CLASS).newInstance();
			conexao =  DriverManager.getConnection(URL,USER,PASSWORD);
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    		throw new Exception("Erro na conexão com o banco de dados");
    	}
    }
    
	public Connection getConnection() throws Exception{
		this.setConnection();
		return conexao;	
	}
	
	public void closeConnetion() throws Exception{
		try {
			this.conexao.close();
		} catch (SQLException e) {
			throw new Exception("Erro ao fechar a conexão com o banco de dados"); 
		}
	}

}
