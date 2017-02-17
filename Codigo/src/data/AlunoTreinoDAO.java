package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import beans.AlunoTreino;

public class AlunoTreinoDAO implements IRelacionamento<AlunoTreino>{
	
	private static AlunoTreinoDAO instance;
	private PreparedStatement statement;
	private ResultSet rSet;
	
	private AlunoTreinoDAO(){
		
	}
	
	public static AlunoTreinoDAO getInstance(){
		if(instance == null)
			instance = new AlunoTreinoDAO();
		return instance;
	}
	
	@Override
	public boolean inserir(AlunoTreino objeto) throws SQLException{
		String sql2 = "INSERT INTO aluno_treino(CPF_ALUNO,CODIGO_TREINO) VALUES (?,?)";
		statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql2);
		statement.setInt(1, objeto.getCodigoAluno());
		statement.setInt(2, objeto.getCodigoTreino());
		statement.execute();
		
		return true;
	}


	@Override
	public boolean remover(AlunoTreino objeto) throws SQLException {
		String sql2 = "DELETE FROM aluno_treino WHERE (CPF_ALUNO = ? AND CODIGO_TREINO = ?)";
		statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql2);
		statement.setInt(1, objeto.getCodigoAluno());
		statement.setInt(2, objeto.getCodigoTreino());
		statement.execute();
		
		return true;
	}


	@Override
	public ArrayList<AlunoTreino> listar() throws SQLException {
		ArrayList<AlunoTreino> relacionamentos = new ArrayList<AlunoTreino>();
		String query = "SELECT * FROM aluno_treino";
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				int codigoAluno = rSet.getInt("CPF_ALUNO");
				int codigoTreino = rSet.getInt("CODIGO_TREINO");
				
				AlunoTreino alunoTreino = new AlunoTreino(codigoAluno,codigoTreino);
				relacionamentos.add(alunoTreino);
			}
		
		return relacionamentos;
	}
	@Override
	public ArrayList<AlunoTreino> listar(int codigo) throws SQLException {
		ArrayList<AlunoTreino> relacionamentos = new ArrayList<AlunoTreino>();
		String query = "SELECT * FROM aluno_treino WHERE CODIGO_TREINO = ?";
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.statement.setInt(1, codigo);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				int codigoAluno = rSet.getInt("CODIGO_TRE");
				int codigoTreino = rSet.getInt("CODIGO_EXE");
				
				AlunoTreino alunoTreino = new AlunoTreino(codigoAluno,codigoTreino);
				 relacionamentos.add(alunoTreino);
			}
		return relacionamentos;
	}
}
