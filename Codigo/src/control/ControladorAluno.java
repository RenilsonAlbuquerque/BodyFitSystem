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
	
	private ControladorAluno(){
		this.repositorio = AlunoDao.getInstance();
		this.usuario = new UsuarioDao();
	}
	
	public static ControladorAluno getInstance(){
		if(instance == null)
			instance = new ControladorAluno();
		return instance;
	}
	
	public void atualizar(Aluno aluno) throws NegocioException{
		try {
			if (this.repositorio.existe(aluno.getCpf())) {
				this.repositorio.atualizar(aluno);
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
	
	public Aluno buscar(String cpf) throws NegocioException{
		try{
			return this.repositorio.buscar(cpf);
		}catch(SQLException e){
			throw new NegocioException(e.getMessage());
		}
		
	}

	public ArrayList<Aluno> listar(String cpf) throws NegocioException {
		ArrayList<Aluno> resultado = new ArrayList<Aluno>();
		try {

			for (Aluno a : repositorio.listar()) {
				if(a.getCpfProfessor().equals(cpf)) {
					Usuario u = this.usuario.buscar(a.getCpf());
					a.setCpf(u.getCpf());
					a.setNome(u.getNome());
					a.setSenha(u.getSenha());
					a.setPerfis(u.getPerfis());
					a.setCaminhoFoto(u.getCaminhoFoto());
					resultado.add(a);
				}
			}
		} catch (SQLException e) {
			throw new NegocioException(e.getMessage());
		}

		if (!(resultado.isEmpty())) {
			return resultado;
		} else
			throw new NegocioException("Você não possui alunos cadastrados");
	}

	public ArrayList<Aluno> listar() throws NegocioException{
		ArrayList<Aluno> resultado = new ArrayList<Aluno>();
		try {
			
			for(Aluno a : repositorio.listar()){
				Usuario u = this.usuario.buscar(a.getCpf());
				a.setCpf(u.getCpf());
				a.setNome(u.getNome());
				a.setSenha(u.getSenha());
				a.setPerfis(u.getPerfis());
				a.setCaminhoFoto(u.getCaminhoFoto());
				resultado.add(a);
			}
		} catch (SQLException e) {
			throw new NegocioException(e.getMessage());
		}
		 
		if(!(resultado.isEmpty())){
			return resultado;
		}
		else
			throw new NegocioException("Não existem alunos cadastrados");
	}
	public void remover(Aluno aluno) throws NegocioException{
		try{
			if(this.repositorio.existe(aluno.getCpf())){
				repositorio.remover(aluno);
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
