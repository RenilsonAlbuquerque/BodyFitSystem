package control;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Exercicio;
import beans.Treino;
import beans.TreinoExercicio;
import data.DBConnectionFactory;
import data.ExercicioDAO;
import data.IAtividadesDao;
import data.IRelacionamento;
import data.TreinoDAO;
import data.TreinoExercicioDAO;
import exceptions.NegocioException;

public class ControladorTreino {
	private static ControladorTreino instance;
	
	private IAtividadesDao<Treino> repositorioTreinos;
	private IRelacionamento<TreinoExercicio,Integer> treinosExercicios;
	private IAtividadesDao<Exercicio> repositorioExercicio;
	
	
	private ControladorTreino(){
		this.repositorioTreinos = TreinoDAO.getInstance();
		this.treinosExercicios = TreinoExercicioDAO.getInstance();
		this.repositorioExercicio = ExercicioDAO.getInstance();
		
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
				throw new NegocioException("O treino j� est� cadastrado");
		} catch (SQLException e) {
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			if(e.getErrorCode() == 1062)
				throw new NegocioException("Voc� n�o pode cadastrar o mesmo \n exercicio repetidamente no treino");
			if(e.getErrorCode() == 1406)
				throw new NegocioException("Digite um nome menor para o treino");
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
				throw new NegocioException("O treino n�o existe");
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
			throw new NegocioException("N�o existem Treinos Padr�o cadastrados");
	}

	public ArrayList<Treino> listar() throws NegocioException {
		ArrayList<Treino> resultado = new ArrayList<Treino>();
		try {
			for (Treino t : repositorioTreinos.listar()) {

				resultado.add(t);
				for (Exercicio e : repositorioExercicio.listar()) {
					if (this.contains(this.treinosExercicios.listar(t.getCodigo()), e)) {
						t.getExerciciosArray().add(e);
					}
				}

			}
		} catch (SQLException e) {
			throw new NegocioException(e.getMessage());
		}

		if (!(resultado.isEmpty())) {
			return resultado;
		} else
			throw new NegocioException("N�o existem Treinos cadastrados");
	}
	public ArrayList<Treino> listar(String cpf) throws NegocioException{
		ArrayList<Treino> resultado = new ArrayList<Treino>();
		try {
			for(Treino t : repositorioTreinos.listar()){
				if(t.getCpfProfessor().equals(cpf) && t.isPadrao() == false){
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
			throw new NegocioException("N�o existem Treinos desse professor cadastrados");
	}

	
	public void remover(Treino treino) throws NegocioException{
		try{
			if(this.repositorioTreinos.existe(treino.getCodigo())){
				repositorioTreinos.remover(treino);
				for(Exercicio e: treino.getExerciciosArray()){
					this.treinosExercicios.remover(new TreinoExercicio(treino.getCodigo(),e.getCodigo()));
				}
				DBConnectionFactory.getInstance().getConnection().commit();
			}
			else{
				throw new NegocioException("O treino n�o est� cadastrado no sistema");
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
