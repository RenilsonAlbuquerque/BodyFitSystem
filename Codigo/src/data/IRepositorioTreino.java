package data;

import java.util.ArrayList;

import beans.Treino;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;

public interface IRepositorioTreino<T> extends InterfaceCRUD<T,Integer>{
	public boolean existe(int codigo) throws ConexaoBancoException;
	public boolean existe(int codigo, String cpf) throws ConexaoBancoException;
	public void cadastrar(T objeto,String cpfProf)throws ConexaoBancoException,CRUDException;
	public void remover(T objeto,String cpfProf) throws ConexaoBancoException,CRUDException;
	public void atualizar(T objeto,String cpfProf) throws ConexaoBancoException,CRUDException;
	public ArrayList<T> listar(String cpfProf)throws ConexaoBancoException,CRUDException;
	public ArrayList<Treino> rotinaDeTreinos(String cpf)throws ConexaoBancoException,CRUDException;
}
