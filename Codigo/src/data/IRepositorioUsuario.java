package data;

import beans.Usuario;
import exceptions.ConexaoBancoException;

public interface IRepositorioUsuario extends InterfaceCRUD<Usuario,String>{
	
	public boolean autenticar(String login,String senha)throws ConexaoBancoException;
	public boolean existe(String cpf) throws ConexaoBancoException;


}
