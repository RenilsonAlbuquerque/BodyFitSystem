package data;

import exceptions.ConexaoBancoException;

public interface IRepositorioFuncionarios<T> extends InterfaceCRUD<T>{
	
	public boolean existe(String cpf) throws ConexaoBancoException;
	
}
