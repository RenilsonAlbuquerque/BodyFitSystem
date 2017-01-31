package control;

import java.util.ArrayList;

import beans.Treino;
import data.IRepositorioTreino;
import data.TreinoDao;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;
import exceptions.NegocioException;

public class ControladorTreino {
	public IRepositorioTreino<Treino> repositorio;
	
	public ControladorTreino(){
		this.repositorio = new TreinoDao();
	}
	
	public void cadastrarPadrao(Treino treino) throws ConexaoBancoException, CRUDException, NegocioException{
		if(!(this.repositorio.existe(treino.getCodigo()))){
			this.repositorio.cadastrar(treino);
		}else
			throw new NegocioException("O treino já está cadastrado");
	}
	public void cadastrar(Treino treino,String cpfProf) throws ConexaoBancoException, CRUDException, NegocioException{
		if(!(this.repositorio.existe(treino.getCodigo()))){
			this.repositorio.cadastrar(treino, cpfProf);
		}
		else
			throw new NegocioException("O Treino já está cadastrado");
	}
	public void alterar(Treino treino) throws ConexaoBancoException, CRUDException, NegocioException{
		if(this.repositorio.existe(treino.getCodigo())){
			this.repositorio.atualizar(treino);
		}
		else
			throw new NegocioException("O treino não existe");
	}
	public void alterar(Treino treino,String cpf) throws ConexaoBancoException, NegocioException{
		if(this.repositorio.existe(treino.getCodigo(),cpf)){
			this.alterar(treino, cpf);
		}
		else
			throw new NegocioException("O treino não existe");
	}
	public ArrayList<Treino> treinosAluno(String cpf) throws ConexaoBancoException, CRUDException{
		return this.repositorio.rotinaDeTreinos(cpf);
	}
	public ArrayList<Treino> listar() throws ConexaoBancoException, CRUDException, NegocioException{
		ArrayList<Treino> treino = this.repositorio.listar();
		if(!(treino.isEmpty()))
			return treino;
		else
			throw new NegocioException("Não existem treinos padrão cadastrados");
	}
	public ArrayList<Treino> listar(String cpf) throws ConexaoBancoException, CRUDException, NegocioException{
		ArrayList<Treino> treino = this.repositorio.listar(cpf); 
		if(!(treino.isEmpty())){
			return treino;
		}
		else
			throw new NegocioException("Não existem treinos desse professor cadastrados");
	}
	public void remover(Treino treino) throws ConexaoBancoException, CRUDException, NegocioException{
		if(this.repositorio.existe(treino.getCodigo())){
			this.repositorio.remover(treino);
		}
		else
			throw new NegocioException("O treino não existe");
	}
	public void remover(Treino treino, String cpf) throws ConexaoBancoException, CRUDException, NegocioException{
		if(this.repositorio.existe(treino.getCodigo())){
			this.repositorio.remover(treino,cpf);
		}
		else
			throw new NegocioException("O treino não existe");
	}
}
