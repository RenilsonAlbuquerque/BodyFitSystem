package data;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;

import exceptions.ConexaoFTPException;



public class FTPConnectionFactory {
	
	private static FTPConnectionFactory instance = null;
	private FTPClient conexao;
	
	private static String URL = "192.168.0.8";
	private static int porta = 21;
	private static String login = "Cliente";
	private static String senha = "12345";
	
	private FTPConnectionFactory() throws ConexaoFTPException{
		this.initConection();
	}
	
	public static FTPConnectionFactory getInstance() throws ConexaoFTPException{
		if(instance == null){
			instance = new FTPConnectionFactory();
		}
		return instance;
	}
	
	public void initConection() throws ConexaoFTPException{
		try{
			this.conexao = new FTPClient();
			this.conexao.connect(URL,porta);
			this.conexao.login(login, senha);
		}catch(IOException e){
			throw new ConexaoFTPException("Erro ao conectar ao servidor FTP");
		}		
	}
	
	public InputStream retrieveImage(String caminho) throws ConexaoFTPException{
		if(this.conexao == null)
			this.initConection();
		try{
			this.conexao.changeWorkingDirectory("/imagens Usuarios/");
			return conexao.retrieveFileStream(caminho);
			
		}catch(IOException e){
			throw new ConexaoFTPException("Erro ao ler o arquivo");
		}
		finally{
			this.closeConection();
		}
	}
	
	public void closeConection(){
		try {
			this.conexao.logout();
			this.conexao.disconnect();
		} catch (IOException e) {
		
		}
	}
	
}
