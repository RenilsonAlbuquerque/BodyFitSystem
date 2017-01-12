package control;

import java.util.ArrayList;

import beans.Aluno;
import beans.Professor;
import beans.Usuario;
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
			throw new NegocioException("CPF inv�lido");
		
	}
	public boolean existe(String cpf) throws ConexaoBancoException{
		return this.repositorio.existe(cpf);
	}
	
	public void atualizar(Professor professor) throws NegocioException, ConexaoBancoException, CRUDException{
		if(usuario.existe(professor.getCpf())){
			this.repositorio.atualizar(professor);
		}else
			throw new NegocioException("CPF inv�lido");
	}
	public Professor buscar(String cpf) throws ConexaoBancoException, CRUDException{
		Professor professor = this.repositorio.buscar(cpf);
		Usuario usuario = this.usuario.buscar(cpf);
		professor.setNome(usuario.getNome());
		professor.setSenha(usuario.getSenha());
		professor.setCaminhoFoto(usuario.getCaminhoFoto());
		return professor;
	}
	public ArrayList<Professor> listar() throws ConexaoBancoException, CRUDException, NegocioException{
		if(!(repositorio.listar().isEmpty())){
			return this.repositorio.listar();
		}
		else
			throw new NegocioException("N�o existem professores cadastrados");
	}
	public void remover(Professor professor) throws ConexaoBancoException, CRUDException, NegocioException{
		if(this.repositorio.existe(professor.getCpf())){
			this.repositorio.remover(professor);
		}else
			throw new NegocioException("O professor n�o existe");
	}
}
