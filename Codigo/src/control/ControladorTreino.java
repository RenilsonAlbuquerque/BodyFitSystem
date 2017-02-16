package control;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Exercicio;
import beans.Treino;
import beans.TreinoExercicio;
import data.DBConnectionFactory;
import data.ExercicioDao;
import data.IRelacionamento;
import data.ITreinoDao;
import data.InterfaceCRUD;
import data.TreinoDao;
import data.TreinoExercicioDAO;
import exceptions.NegocioException;

public class ControladorTreino {
	private static ControladorTreino instance;
	
	public ITreinoDao repositorioTreinos;
	public IRelacionamento<TreinoExercicio> treinosExercicios;
	public InterfaceCRUD<Exercicio,Integer> repositorioExercicio;
	
	private ControladorTreino(){
		this.repositorioTreinos = TreinoDao.getInstance();
		this.treinosExercicios = TreinoExercicioDAO.getInstance();
		this.repositorioExercicio = new ExercicioDao();
	}
	
	public static ControladorTreino getInstance(){
		if(instance == null)
			instance = new ControladorTreino();
		return instance;
	}
	
	
	public void cadastrar(Treino treino) throws NegocioException{
		try {
			if (!(this.repositorioTreinos.existe(treino.getCodigo()))) {
				int codigo = this.repositorioTreinos.cadastrar(treino);
	
				for(Exercicio e: treino.getExerciciosArray()){
					this.treinosExercicios.inserir(new TreinoExercicio(codigo,e.getCodigo()));
				}
				DBConnectionFactory.getInstance().getConnection().commit();
			}else
				throw new NegocioException("O treino já está cadastrado");
		} catch (SQLException e) {
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
	}
	public void alterar(Treino treino) throws NegocioException{
		try{
			if(this.repositorioTreinos.existe(treino.getCodigo())){
				this.repositorioTreinos.atualizar(treino);
				
				
				for(Exercicio e: treino.getExerciciosArray()){
					if(!this.contains(this.treinosExercicios.listar(treino.getCodigo()), e)){
						this.treinosExercicios.inserir(new TreinoExercicio(treino.getCodigo(),e.getCodigo()));
					}
				}
				
				for(TreinoExercicio t : this.treinosExercicios.listar(treino.getCodigo())){
					if(!this.contains(treino.getExerciciosArray(), t)){
						this.treinosExercicios.remover(t);
					}
				}
				DBConnectionFactory.getInstance().getConnection().commit();
			}
			else
				throw new NegocioException("O treino não existe");
		}catch(SQLException e){
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
	}

	public ArrayList<Treino> listarPadrao() throws  NegocioException{
		ArrayList<Treino> resultado = new ArrayList<Treino>();
		try {
			for(Treino t : repositorioTreinos.listar()){
				if(t.isPadrao()){
					resultado.add(t);
					for(Exercicio e: repositorioExercicio.listar()){
						if(this.contains( this.treinosExercicios.listar(t.getCodigo()),e)){
							t.getExerciciosArray().add(e);
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new NegocioException(e.getMessage());
		}
		 
		if(!(resultado.isEmpty())){
			return resultado;
		}
		else
			throw new NegocioException("Não existem Treinos Padrão cadastrados");
	}
	public ArrayList<Treino> listar(String cpf) throws NegocioException{
		ArrayList<Treino> resultado = new ArrayList<Treino>();
		try {
			for(Treino t : repositorioTreinos.listar()){
				if(t.getCpfProfessor().equals(cpf)){
					resultado.add(t);
					for(Exercicio e: repositorioExercicio.listar()){
						if(this.contains( this.treinosExercicios.listar(t.getCodigo()),e)){
							t.getExerciciosArray().add(e);
						}
					}
				}
			}
			
		} catch (SQLException e) {
			throw new NegocioException(e.getMessage());
		}
		 
		if(!(resultado.isEmpty())){
			return resultado;
		}
		else
			throw new NegocioException("Não existem Treinos desse professor cadastrados");
	}
	
	
	public void remover(Treino treino) throws NegocioException{
		try{
			System.out.println(treino.getCodigo());
			if(this.repositorioTreinos.existe(treino.getCodigo())){
				repositorioTreinos.remover(treino);
				for(Exercicio e: treino.getExerciciosArray()){
					this.treinosExercicios.remover(new TreinoExercicio(treino.getCodigo(),e.getCodigo()));
				}
				DBConnectionFactory.getInstance().getConnection().commit();
			}
			else{
				throw new NegocioException("O treino não está cadastrado no sistema");
			}
			
		}
		catch(SQLException e){
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
	}
	
	
	private boolean contains(ArrayList<TreinoExercicio> lista,Exercicio e){
		for(TreinoExercicio te : lista){
			if(e.getCodigo() == te.getCodigoExercicio()){
				return true;
			}
		}
		return false;
	}
	
	
	private boolean contains(ArrayList<Exercicio> lista,TreinoExercicio t){
		for(Exercicio e : lista){
			if(t.getCodigoExercicio() == e.getCodigo()){
				return true;
			}
		}
		return false;
	}
}
