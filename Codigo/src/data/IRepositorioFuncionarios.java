package data;

import exceptions.CRUDException;
import exceptions.ConexaoBancoException;

public interface IRepositorioFuncionarios<T> extends InterfaceCRUD<T>{
	
	public boolean existe(String cpf) throws ConexaoBancoException;
	public T buscar(String cpf)throws ConexaoBancoException,CRUDException;
	
}
