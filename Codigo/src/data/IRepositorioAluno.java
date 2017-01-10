package data;

import beans.Aluno;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;

public interface IRepositorioAluno extends InterfaceCRUD<Aluno>{
	
	public boolean existe(String cpf) throws ConexaoBancoException;
	public void cadastrarRotinaTreino(Aluno aluno);
	public Aluno buscar(String cpf)throws ConexaoBancoException,CRUDException;
}
