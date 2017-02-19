package control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import beans.Aluno;
import beans.AlunoExecuta;
import beans.AlunoTreino;
import beans.Treino;
import beans.Usuario;
import data.AlunoDao;
import data.AlunoExecutaDAO;
import data.AlunoTreinoDAO;
import data.DBConnectionFactory;
import data.IRelacionamento;
import data.ITreinoDao;
import data.InterfaceCRUD;
import data.TreinoDao;
import data.UsuarioDao;
import exceptions.NegocioException;

public class ControladorAluno {
	
private static ControladorAluno instance;	
private InterfaceCRUD<Aluno,String> repositorio;
private InterfaceCRUD<Usuario,String> usuario;
private IRelacionamento<AlunoTreino,String> repositorioAlunoTreino;
private ITreinoDao repositorioTreinos;
private IRelacionamento<AlunoExecuta,String> repositorioHistorico;
	
	private ControladorAluno(){
		this.repositorio = AlunoDao.getInstance();
		this.usuario = new UsuarioDao();
		this.repositorioAlunoTreino = AlunoTreinoDAO.getInstance();
		this.repositorioTreinos = TreinoDao.getInstance();
		this.repositorioHistorico = AlunoExecutaDAO.getInstance();
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
			Aluno resultado = this.repositorio.buscar(cpf);
			resultado.setRotinaTreinos(this.getTreinosAluno(cpf));
			resultado.setCodigoTreinoDia(this.codigoTreinoDia(resultado));
			return resultado;
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
	public void salvarRotinaDeTreino(Aluno aluno) throws NegocioException{
		try {
			
			for(AlunoTreino at: this.repositorioAlunoTreino.listar(aluno.getCpf())){
				this.repositorioAlunoTreino.remover(at);
			}
			
			int ordem = 1;
			for(Treino treinos : aluno.getRotinaTreinos() ){
				this.repositorioAlunoTreino.inserir(new AlunoTreino(aluno.getCpf(),treinos.getCodigo(),ordem));
			}
			DBConnectionFactory.getInstance().getConnection().commit();
		} catch (SQLException e) {
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			if(e.getErrorCode() == 1062)
				throw new NegocioException("Você não pode salvar o mesmo \n treino repetidamente no rotina do aluno");
			
		}
	}
	private ArrayList<Treino> getTreinosAluno(String cpf) throws NegocioException{
		ArrayList<Treino> resultado = new ArrayList<Treino>();
		try{
			for(AlunoTreino at : this.repositorioAlunoTreino.listar(cpf)){
				for(Treino t : this.repositorioTreinos.listar()){
					if(t.getCodigo() == at.getCodigoTreino()){
						resultado.add(t);
					}
					break;
				}
			}
			return resultado;
		}
		catch(SQLException e){
			throw new NegocioException(e.getMessage());
		}
	}
	private int codigoTreinoDia(Aluno a) throws SQLException{
		ArrayList<AlunoExecuta> historico = this.repositorioHistorico.listar(a.getCpf());
		if(historico.isEmpty()){
			return 0;
		}
		else{
			Collections.sort(historico);
			int result = historico.get(historico.size()).getCodigoTreino() + 1;
			if(result < a.getRotinaTreinos().size())
				return result;
			else
				return 0;
			
		}
	}

}
