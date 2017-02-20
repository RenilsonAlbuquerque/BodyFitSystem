package control;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Exercicio;
import data.DBConnectionFactory;
import data.ExercicioDAO;
import data.IAtividadesDao;
import exceptions.NegocioException;

public class ControladorExercicio {
		
	private static ControladorExercicio instance;
	public IAtividadesDao<Exercicio> repositorio;
	
	private ControladorExercicio(){
		this.repositorio = ExercicioDAO.getInstance();
	}
	
	public static ControladorExercicio getInstance(){
		if(instance == null)
			instance = new ControladorExercicio();
		return instance;
	}
	
	public void cadastrar(Exercicio exercicio) throws NegocioException{
		try {
			if (!(this.repositorio.existe(exercicio.getCodigo()))) {
				 int resultado = this.repositorio.cadastrar(exercicio);
				 if(resultado > 0)
					 DBConnectionFactory.getInstance().getConnection().commit();
				 else
					 throw new NegocioException("O exercício já está cadastrado");
			} else
				throw new NegocioException("O exercício já está cadastrado");
		} catch (SQLException e) {
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
	}
	public void alterar(Exercicio exercicio) throws  NegocioException{
		try{
			if(this.repositorio.existe(exercicio.getCodigo())){
				this.repositorio.atualizar(exercicio);
				DBConnectionFactory.getInstance().getConnection().commit();
			}
			else
				throw new NegocioException("O exercício não existe");
		}catch(SQLException e){
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
	}
	public ArrayList<Exercicio> listarPadrao() throws NegocioException{
		ArrayList<Exercicio> resultado = new ArrayList<Exercicio>();
		try {
			for(Exercicio e : repositorio.listar()){
				if(e.isPadrao())
					resultado.add(e);
			}
		} catch (SQLException e) {
			throw new NegocioException(e.getMessage());
		}
		 
		if(!(resultado.isEmpty())){
			return resultado;
		}
		else
			throw new NegocioException("Não existem Exercícios Padrão cadastrados");
	}
	public ArrayList<Exercicio> listar() throws NegocioException{
		ArrayList<Exercicio> resultado = new ArrayList<Exercicio>();
		try {
			for(Exercicio e : repositorio.listar()){
					resultado.add(e);
			}
		} catch (SQLException e) {
			throw new NegocioException(e.getMessage());
		}
		 
		if(!(resultado.isEmpty())){
			return resultado;
		}
		else
			throw new NegocioException("Não existem Exercícios Padrão cadastrados");
	}
	public ArrayList<Exercicio> listar(String cpf) throws NegocioException{
		ArrayList<Exercicio> resultado = new ArrayList<Exercicio>();
		try {
			for(Exercicio e : repositorio.listar()){
				if(e.getCpfProfessor().equals(cpf) && !e.isPadrao())
					resultado.add(e);
			}
		} catch (SQLException e) {
			throw new NegocioException(e.getMessage());
		}
		 
		if(!(resultado.isEmpty())){
			return resultado;
		}
		else
			throw new NegocioException("Não existem Exercícios desse professor cadastrados");
	}
	
	
	public void remover(Exercicio exercicio) throws  NegocioException{
		try{
			if(this.repositorio.existe(exercicio.getCodigo())){
				repositorio.remover(exercicio);
				DBConnectionFactory.getInstance().getConnection().commit();
			}
			else{
				throw new NegocioException("O exercício não está cadastrado no sistema");
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
