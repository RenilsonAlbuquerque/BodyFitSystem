package control;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Professor;
import beans.Usuario;
import data.DBConnectionFactory;
import data.InterfaceCRUD;
import data.ProfessorDao;
import data.UsuarioDao;
import exceptions.NegocioException;

public class ControladorProfessor {
		
	private static ControladorProfessor instance;	
	private InterfaceCRUD<Professor,String> repositorio;
	private InterfaceCRUD<Usuario,String> usuario;
	private ControladorUsuario controladorUsuario;
	
	private ControladorProfessor(){
		this.repositorio = ProfessorDao.getInstance();
		this.usuario = new UsuarioDao();
		this.controladorUsuario = new ControladorUsuario();
	}
	
	public static ControladorProfessor getInstance(){
		if(instance == null)
			instance = new ControladorProfessor();
		return instance;
	}
	
	public void cadastrar(Professor professor) throws NegocioException{
		try {
			if(usuario.existe(professor.getCpf())){
				
				this.repositorio.cadastrar(professor);
				DBConnectionFactory.getInstance().getConnection().commit();
				
			}else{
				this.controladorUsuario.cadastrar(professor);
			}
				
		} catch (SQLException e) {
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
		
	}
	/*
	public boolean existe(String cpf) throws ConexaoBancoException{
		return this.repositorio.existe(cpf);
	}
	*/
	
	public void atualizar(Professor professor) throws NegocioException{
		try {
			if (this.repositorio.existe(professor.getCpf())) {
				this.repositorio.atualizar(professor);
				DBConnectionFactory.getInstance().getConnection().commit();
			} else
				throw new NegocioException("O aluno não está cadastrado no sistema");
		} catch (SQLException e) {
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
	}
	
	public Professor buscar(String cpf) throws NegocioException{
		try{
			return this.repositorio.buscar(cpf);
		}catch(SQLException e){
			throw new NegocioException(e.getMessage());
		}
	}
	
	public ArrayList<Professor> listar() throws NegocioException{
		ArrayList<Professor> resultado = new ArrayList<Professor>();
		try {
			resultado = repositorio.listar();
		} catch (SQLException e) {
			throw new NegocioException(e.getMessage());
		}
		 
		if(!(resultado.isEmpty())){
			return resultado;
		}
		else
			throw new NegocioException("Não existem professores cadastrados");
	}
	public void remover(Professor professor) throws  NegocioException{
		try{
			if(this.repositorio.existe(professor.getCpf())){
				repositorio.remover(professor);
				DBConnectionFactory.getInstance().getConnection().commit();
			}
			else{
				throw new NegocioException("O aluno não está cadaastrado no sistema");
			}
			
		}
		catch(SQLException e){
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
		
	}
}
