package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import beans.AlunoTreino;

public class AlunoTreinoDAO implements IRelacionamento<AlunoTreino,String>{
	
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
		String sql2 = "INSERT INTO aluno_treino(CPF_ALUNO,CODIGO_TREINO,ORDEM) VALUES (?,?,?)";
		statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql2);
		statement.setString(1, objeto.getCpfAluno());
		statement.setInt(2, objeto.getCodigoTreino());
		statement.setInt(3, objeto.getOrdem());
		statement.execute();
		
		return true;
	}


	@Override
	public boolean remover(AlunoTreino objeto) throws SQLException {
		String sql2 = "DELETE FROM aluno_treino WHERE (CPF_ALUNO = ? AND CODIGO_TREINO = ?)";
		statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql2);
		statement.setString(1, objeto.getCpfAluno());
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
				String cpfAluno = rSet.getString("CPF_ALUNO");
				int codigoTreino = rSet.getInt("CODIGO_TREINO");
				int ordem = rSet.getInt("ORDEM");
				AlunoTreino alunoTreino = new AlunoTreino(cpfAluno,codigoTreino,ordem);
				relacionamentos.add(alunoTreino);
			}
		
		return relacionamentos;
	}
	@Override
	public ArrayList<AlunoTreino> listar(String cpf) throws SQLException {
		ArrayList<AlunoTreino> relacionamentos = new ArrayList<AlunoTreino>();
		String query = "SELECT * FROM aluno_treino WHERE CPF_ALUNO = ?";
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.statement.setString(1, cpf);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				String cpfAluno = rSet.getString("CPF_ALUNO");
				int codigoTreino = rSet.getInt("CODIGO_TREINO");
				int ordem = rSet.getInt("ORDEM");
				AlunoTreino alunoTreino = new AlunoTreino(cpfAluno,codigoTreino,ordem);
				 relacionamentos.add(alunoTreino);
			}
		return relacionamentos;
	}
}
