package data;

import java.util.ArrayList;

import exceptions.CRUDException;
import exceptions.ConexaoBancoException;

public interface InterfaceCRUD<T> {
	
	public void cadastrar(T objeto)throws ConexaoBancoException,CRUDException;
	public void remover(T objeto) throws ConexaoBancoException,CRUDException ;
	public void atualizar(T objeto) throws ConexaoBancoException,CRUDException;
	public ArrayList<T> listar()throws ConexaoBancoException,CRUDException;
}
