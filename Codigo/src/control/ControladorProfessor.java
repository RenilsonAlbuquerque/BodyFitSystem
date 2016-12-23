package control;

import java.util.ArrayList;

import beans.Aluno;
import beans.Professor;
import data.IRepositorioFuncionarios;
import data.IRepositorioUsuario;
import data.ProfessorDao;
import data.UsuarioDao;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;
import exceptions.NegocioException;

public class ControladorProfessor {
		
	private IRepositorioFuncionarios<Professor> repositorio;
	private IRepositorioUsuario usuario;
	
	public ControladorProfessor(){
		this.repositorio = new ProfessorDao();
		this.usuario = new UsuarioDao();
	}
	
	public void cadastrar(Professor professor) throws NegocioException, ConexaoBancoException, CRUDException{
		if(usuario.existe(professor.getCpf())){
			this.repositorio.cadastrar(professor);
		}else
			throw new NegocioException("CPF inválido");
		
	}
	
	public void atualizar(Professor professor) throws NegocioException, ConexaoBancoException, CRUDException{
		if(usuario.existe(professor.getCpf())){
			this.repositorio.atualizar(professor);
		}else
			throw new NegocioException("CPF inválido");
	}
	public ArrayList<Professor> listar() throws ConexaoBancoException, CRUDException, NegocioException{
		if(!(repositorio.listar().isEmpty())){
			return this.repositorio.listar();
		}
		else
			throw new NegocioException("Não existem professores cadastrados");
	}
	public void remover(Professor professor) throws ConexaoBancoException, CRUDException, NegocioException{
		if(this.repositorio.existe(professor.getCpf())){
			this.repositorio.remover(professor);
		}else
			throw new NegocioException("O professor não existe");
		
	}
}
