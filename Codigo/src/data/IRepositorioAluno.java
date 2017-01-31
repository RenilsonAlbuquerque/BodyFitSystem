package data;

import java.util.ArrayList;

import beans.Aluno;
import beans.Treino;
import exceptions.ConexaoBancoException;

public interface IRepositorioAluno extends InterfaceCRUD<Aluno,String>{
	
	public boolean existe(String cpf) throws ConexaoBancoException;
	public void cadastrarRotinaTreino(Aluno aluno);
}
