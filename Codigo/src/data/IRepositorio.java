package data;

import java.util.ArrayList;

import exceptions.CRUDException;
import exceptions.ConexaoBancoException;

public interface IRepositorio<I> {
	
	public boolean existe(String cpf) throws ConexaoBancoException;
	public void cadastrar(I objeto)throws ConexaoBancoException,CRUDException;
	public void remover(I objeto) throws ConexaoBancoException,CRUDException ;
	public void atualizar(I objeto) throws ConexaoBancoException,CRUDException;
	public ArrayList<I> listar()throws ConexaoBancoException,CRUDException;

}
