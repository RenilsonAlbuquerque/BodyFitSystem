package control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import beans.Aluno;
import beans.AlunoExecuta;
import beans.AlunoTreino;
import beans.Treino;
import beans.Usuario;
import data.AlunoDAO;
import data.AlunoExecutaDAO;
import data.AlunoTreinoDAO;
import data.DBConnectionFactory;
import data.IRelacionamento;
import data.InterfaceCRUD;
import data.UsuarioDAO;
import exceptions.NegocioException;

public class ControladorAluno {
	
private static ControladorAluno instance;	
private InterfaceCRUD<Aluno,String> repositorio;
private InterfaceCRUD<Usuario,String> usuario;
private IRelacionamento<AlunoTreino,String> repositorioAlunoTreino;
private ControladorTreino treinos;
private IRelacionamento<AlunoExecuta,String> repositorioHistorico;
	
	private ControladorAluno(){
		this.repositorio = AlunoDAO.getInstance();
		this.usuario = new UsuarioDAO();
		this.repositorioAlunoTreino = AlunoTreinoDAO.getInstance();
		this.treinos = ControladorTreino.getInstance();
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
			resultado.setTreinoDoDia(this.codigoTreinoDia(resultado));
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
					a.setRotinaTreinos(this.getTreinosAluno(a.getCpf()));
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
				a.setRotinaTreinos(this.getTreinosAluno(a.getCpf()));
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
			for(Treino treinos : aluno.getRotinaTreinos()){
				this.repositorioAlunoTreino.inserir(new AlunoTreino(aluno.getCpf(),treinos.getCodigo(),ordem));
				ordem++;
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
	public void salvarHistoricoTreino(Aluno aluno) throws NegocioException{
		AlunoExecuta relacao = new AlunoExecuta(aluno.getCpf(),
				aluno.getTreinoDoDia().getCodigo(),
				new Date(System.currentTimeMillis()));
		try{
			
			if(!this.repositorioHistorico.listar().contains(relacao)){
				this.repositorioHistorico.inserir(relacao);
			}else{
				throw new NegocioException("Você já treinou hoje");
			}
			DBConnectionFactory.getInstance().getConnection().commit();
		}
		catch(SQLException e){
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			if(e.getErrorCode() == 1062)
				throw new NegocioException(e.getMessage());
		}
	}
	private ArrayList<Treino> getTreinosAluno(String cpf) throws NegocioException{
		ArrayList<Treino> resultado = new ArrayList<Treino>();
		try{
			for(AlunoTreino at : this.repositorioAlunoTreino.listar(cpf)){
				for(Treino t : this.treinos.listar()){
					if(t.getCodigo() == at.getCodigoTreino()){
						resultado.add(t);
					}
					//break;
				}
			}
			return resultado;
		}
		catch(SQLException e){
			throw new NegocioException(e.getMessage());
		}
	}
	private Treino codigoTreinoDia(Aluno a) throws SQLException{
		if(a.getRotinaTreinos().isEmpty()){
			return null;
		}
		else{
			ArrayList<AlunoExecuta> historico = this.repositorioHistorico.listar(a.getCpf());
			if(historico.isEmpty()){
				return  a.getRotinaTreinos().get(0);
			}
			else{
				Collections.sort(historico);
				for(int i = 0; i< a.getRotinaTreinos().size();i++){
					if(a.getRotinaTreinos().get(i).getCodigo() == historico.get(historico.size() -1).getCodigoTreino()){
						if(i + 1 <= a.getRotinaTreinos().size() -1){
							return a.getRotinaTreinos().get(i+1);
						}
					}
				}
				return a.getRotinaTreinos().get(0);
				
			}
		}
		
	}

}
