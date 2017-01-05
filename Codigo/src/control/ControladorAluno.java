package control;

import java.util.ArrayList;

import beans.Aluno;
import data.AlunoDao;
import data.IRepositorioAluno;
import data.IRepositorioUsuario;
import data.UsuarioDao;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;
import exceptions.NegocioException;

public class ControladorAluno {
	
private IRepositorioAluno repositorio;
private IRepositorioUsuario usuario;
	
	public ControladorAluno(){
		this.repositorio = new AlunoDao();
		this.usuario = new UsuarioDao();
	}
	public boolean existe(String cpf) throws ConexaoBancoException {
		return this.repositorio.existe(cpf);
	}
	
	public void cadastrar(Aluno aluno) throws NegocioException, ConexaoBancoException, CRUDException{
		if(usuario.existe(aluno.getCpf())){
			this.repositorio.cadastrar(aluno);
		}else
			throw new NegocioException("CPF inválido");
		
	}
	public void atualizar(Aluno aluno) throws NegocioException, ConexaoBancoException, CRUDException{
		if(usuario.existe(aluno.getCpf())){
			this.repositorio.atualizar(aluno);
		}else
			throw new NegocioException("CPF inválido");
	}
	
	public ArrayList<Aluno> listar() throws ConexaoBancoException, CRUDException, NegocioException{
		if(!(repositorio.listar().isEmpty())){
			return this.repositorio.listar();
		}
		else
			throw new NegocioException("Não existem Alunos cadastrados");
	}
	public void remover(Aluno aluno) throws ConexaoBancoException, CRUDException{
		repositorio.remover(aluno);
	}
	

}
