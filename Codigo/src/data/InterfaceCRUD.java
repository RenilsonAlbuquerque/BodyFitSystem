package data;

import java.util.ArrayList;

import exceptions.CRUDException;
import exceptions.ConexaoBancoException;

public interface InterfaceCRUD<T,K> {
	
	public T buscar(K chave)throws ConexaoBancoException,CRUDException;
	public void cadastrar(T objeto)throws ConexaoBancoException,CRUDException;
	public void remover(T objeto) throws ConexaoBancoException,CRUDException ;
	public void atualizar(T objeto) throws ConexaoBancoException,CRUDException;
	public ArrayList<T> listar()throws ConexaoBancoException,CRUDException;
	
}
