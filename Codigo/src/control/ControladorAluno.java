package control;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Aluno;
import beans.Usuario;
import data.AlunoDao;
import data.DBConnectionFactory;
import data.InterfaceCRUD;
import data.UsuarioDao;
import exceptions.NegocioException;

public class ControladorAluno {
	
private static ControladorAluno instance;	
private InterfaceCRUD<Aluno,String> repositorio;
private InterfaceCRUD<Usuario,String> usuario;
private ControladorUsuario controladorUsuario;
	
	private ControladorAluno(){
		this.repositorio = AlunoDao.getInstance();
		this.usuario = new UsuarioDao();
		this.controladorUsuario = new ControladorUsuario();
	}
	
	public static ControladorAluno getInstance(){
		if(instance == null)
			instance = new ControladorAluno();
		return instance;
	}
	
	public void cadastrar(Aluno aluno) throws NegocioException{
		try {
			if(usuario.existe(aluno.getCpf())){
				
				this.repositorio.cadastrar(aluno);
				DBConnectionFactory.getInstance().getConnection().commit();
				
			}else{
				this.controladorUsuario.cadastrar(aluno);
			}
				
		} catch (SQLException e) {
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
		
	}
	public void atualizar(Aluno aluno) throws NegocioException{
		try {
			if (this.repositorio.existe(aluno.getCpf())) {
				this.repositorio.atualizar(aluno);
				DBConnectionFactory.getInstance().getConnection().commit();
			} else
				throw new NegocioException("O aluno n�o est� cadastrado no sistema");
		} catch (SQLException e) {
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
	}
	
	public Aluno buscar(String cpf) throws NegocioException{
		try{
			return this.repositorio.buscar(cpf);
		}catch(SQLException e){
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	public ArrayList<Aluno> listar() throws NegocioException{
		ArrayList<Aluno> resultado = new ArrayList<Aluno>();
		try {
			resultado = repositorio.listar();
		} catch (SQLException e) {
			throw new NegocioException(e.getMessage());
		}
		 
		if(!(resultado.isEmpty())){
			return resultado;
		}
		else
			throw new NegocioException("N�o existem alunos cadastrados");
	}
	public void remover(Aluno aluno) throws NegocioException{
		try{
			if(this.repositorio.existe(aluno.getCpf())){
				repositorio.remover(aluno);
				DBConnectionFactory.getInstance().getConnection().commit();
			}
			else{
				throw new NegocioException("O aluno n�o est� cadaastrado no sistema");
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
