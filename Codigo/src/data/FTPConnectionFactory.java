package data;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.net.ftp.FTPClient;

import exceptions.ConexaoFTPException;



public class FTPConnectionFactory {
	
	private static FTPConnectionFactory instance = null;
	private FTPClient conexao;
	
	private static String URL;
	private static final int porta = 21;
	private static final String login = "Cliente";
	private static final String senha = "12345";
		
	private FTPConnectionFactory() throws ConexaoFTPException{
		try {
			URL = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
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
			this.conexao.changeWorkingDirectory("/Imagens Usuarios/");
			return conexao.retrieveFileStream(caminho);
			
		}catch(IOException e){
			throw new ConexaoFTPException("Erro ao ler o arquivo");
		}
		finally{
			this.closeConection();
		}
	}
	public boolean saveImage(File arquivo,String nome){
		
		FileInputStream inputStream = null;
		boolean resultado = false;
		try {
			this.initConection();
			this.conexao.changeWorkingDirectory("/Imagens Usuarios/");
			inputStream = new FileInputStream(arquivo);
			//this.conexao.setFileTransferMode(FTPClient.ASCII_FILE_TYPE);
			this.conexao.enterLocalPassiveMode();
			resultado = conexao.storeFile(nome,inputStream);
			System.out.println(conexao.getReplyCode());
		} catch (IOException | ConexaoFTPException e) {
			e.printStackTrace();
		}
		finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultado;
		
		
	}
	
	public void closeConection(){
		try {
			
			this.conexao.logout();
			this.conexao.disconnect();
			this.conexao = null;
		} catch (IOException e) {
		
		}
	}
	
	
}
