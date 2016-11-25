package data;

import exceptions.ConexaoBancoException;

public interface IRepositorioUsuario {
	
	public boolean autenticar(String login,String senha)throws ConexaoBancoException;


}
