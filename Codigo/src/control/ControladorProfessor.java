package control;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Professor;
import beans.Usuario;
import data.DBConnectionFactory;
import data.InterfaceCRUD;
import data.ProfessorDAO;
import data.UsuarioDAO;
import exceptions.NegocioException;

public class ControladorProfessor {
		
	private static ControladorProfessor instance;	
	private InterfaceCRUD<Professor,String> repositorio;
	private InterfaceCRUD<Usuario,String> usuario;
	
	private ControladorProfessor(){
		this.repositorio = ProfessorDAO.getInstance();
		this.usuario = new UsuarioDAO();
		//this.controladorUsuario = ControladorUsuario.getInstance();
	}
	
	public static ControladorProfessor getInstance(){
		if(instance == null)
			instance = new ControladorProfessor();
		return instance;
	}
	/*
	public void cadastrar(Professor professor) throws NegocioException{
		try {
			
			if(!this.repositorio.existe(professor.getCpf())){
					
				if(!this.usuario.existe(professor.getCpf())){
					this.usuario.cadastrar(professor);
					this.repositorio.cadastrar(professor);
				}else{
					
					Usuario u = usuario.buscar(professor.getCpf());
					u.getPerfis().add(PerfisEnum.professor);
					this.usuario.atualizar(u);
					this.repositorio.cadastrar(professor);
				}
				DBConnectionFactory.getInstance().getConnection().commit();
				
			}else{			
				try {
					DBConnectionFactory.getInstance().getConnection().rollback();
				} catch (SQLException e1) {}
				throw new NegocioException("O professor já está cadastrado");
				
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
			for(Professor p : repositorio.listar()){
				Usuario u = this.usuario.buscar(p.getCpf());
				p.setCpf(u.getCpf());
				p.setNome(u.getNome());
				p.setSenha(u.getSenha());
				p.setCaminhoFoto(u.getCaminhoFoto());
				p.setPerfis(u.getPerfis());
				resultado.add(p);
			}
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
