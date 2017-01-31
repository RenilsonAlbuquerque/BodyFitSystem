package control;

import java.util.ArrayList;

import beans.Exercicio;
import data.ExercicioDao;
import data.IRepositorioExercicio;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;
import exceptions.NegocioException;

public class ControladorExercicio {
		
	public IRepositorioExercicio<Exercicio> repositorio;
	
	public ControladorExercicio(){
		this.repositorio = new ExercicioDao();
	}
	
	public void cadastrarPadrao(Exercicio exercicio) throws ConexaoBancoException, CRUDException, NegocioException{
		if(!(this.repositorio.existe(exercicio.getCodigo()))){
			this.repositorio.cadastrar(exercicio);
		}else
			throw new NegocioException("O exercício já está cadastrado");
	}
	public void cadastrar(Exercicio exercicio,String cpfProf) throws ConexaoBancoException, CRUDException, NegocioException{
		if(!(this.repositorio.existe(exercicio.getCodigo()))){
			this.repositorio.cadastrar(exercicio, cpfProf);
		}
		else
			throw new NegocioException("O exercício já está cadastrado");
	}
	public void alterar(Exercicio exercicio) throws ConexaoBancoException, CRUDException, NegocioException{
		if(this.repositorio.existe(exercicio.getCodigo())){
			this.repositorio.atualizar(exercicio);
		}
		else
			throw new NegocioException("O exercício não existe");
	}
	public void alterar(Exercicio exercicio,String cpf) throws ConexaoBancoException, NegocioException{
		if(this.repositorio.existe(exercicio.getCodigo(),cpf)){
			this.alterar(exercicio, cpf);
		}
		else
			throw new NegocioException("O exercício não existe");
	}
	public ArrayList<Exercicio> listar() throws ConexaoBancoException, CRUDException, NegocioException{
		ArrayList<Exercicio> exercicio = this.repositorio.listar();
		if(!(exercicio.isEmpty()))
			return exercicio;
		else
			throw new NegocioException("Não existem exercícios padrão cadastrados");
	}
	public ArrayList<Exercicio> listar(int codigo) throws ConexaoBancoException, CRUDException, NegocioException{
		ArrayList<Exercicio> exercicio = this.repositorio.listar(codigo);
		if(!(exercicio.isEmpty()))
			return exercicio;
		else
			throw new NegocioException("Não existem exercícios padrão cadastrados");
	}
	public ArrayList<Exercicio> listar(String cpf) throws ConexaoBancoException, CRUDException, NegocioException{
		ArrayList<Exercicio> exercicio = this.repositorio.listar(cpf); 
		if(!(exercicio.isEmpty())){
			return exercicio;
		}
		else
			throw new NegocioException("Não existem exercicios desse professor cadastrados");
	}
	public void remover(Exercicio exercicio) throws ConexaoBancoException, CRUDException, NegocioException{
		if(this.repositorio.existe(exercicio.getCodigo())){
			this.repositorio.remover(exercicio);
		}
		else
			throw new NegocioException("O exercício não existe");
	}
	public void remover(Exercicio exercicio, String cpf) throws ConexaoBancoException, CRUDException, NegocioException{
		if(this.repositorio.existe(exercicio.getCodigo())){
			this.repositorio.remover(exercicio,cpf);
		}
		else
			throw new NegocioException("O exercício não existe");
	}
}
