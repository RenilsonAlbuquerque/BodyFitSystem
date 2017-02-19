package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.PreparedStatement;

import beans.AlunoExecuta;

public class AlunoExecutaDAO implements IRelacionamento<AlunoExecuta,String>{
	
	private static AlunoExecutaDAO instance;
	private PreparedStatement statement;
	private ResultSet rSet;
	
	private AlunoExecutaDAO(){
		
	}
	
	public static AlunoExecutaDAO getInstance(){
		if(instance == null)
			instance = new AlunoExecutaDAO();
		return instance;
	}
	
	@Override
	public boolean inserir(AlunoExecuta objeto) throws SQLException{
		String sql2 = "INSERT INTO aluno_executa(CPF_ALUNO,CODIGO_TREINO) VALUES (?,?)";
		statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql2);
		statement.setString(1, objeto.getCpfAluno());
		statement.setInt(2, objeto.getCodigoTreino());
		statement.execute();
		
		return true;
	}


	@Override
	public boolean remover(AlunoExecuta objeto) throws SQLException {
		String sql2 = "DELETE FROM aluno_executa WHERE (CPF_ALUNO = ? AND CODIGO_TREINO = ?)";
		statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql2);
		statement.setString(1, objeto.getCpfAluno());
		statement.setInt(2, objeto.getCodigoTreino());
		statement.execute();
		
		return true;
	}


	@Override
	public ArrayList<AlunoExecuta> listar() throws SQLException {
		ArrayList<AlunoExecuta> relacionamentos = new ArrayList<AlunoExecuta>();
		String query = "SELECT * FROM aluno_executa";
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				String cpfAluno = rSet.getString("CPF_ALUNO");
				int codigoTreino = rSet.getInt("CODIGO_TREINO");
				Date data = rSet.getDate("DATAEXECUCAO");
				AlunoExecuta alunoExecuta = new AlunoExecuta(cpfAluno,codigoTreino,data);
				relacionamentos.add(alunoExecuta);
			}
		
		return relacionamentos;
	}
	@Override
	public ArrayList<AlunoExecuta> listar(String cpf) throws SQLException {
		ArrayList<AlunoExecuta> relacionamentos = new ArrayList<AlunoExecuta>();
		String query = "SELECT * FROM aluno_executa WHERE CPF_ALUNO = ?";
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.statement.setString(1, cpf);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				String cpfAluno = rSet.getString("CPF_ALUNO");
				int codigoTreino = rSet.getInt("CODIGO_TREINO");
				Date data = rSet.getDate("DATAEXECUCAO");
				AlunoExecuta alunoExecuta = new AlunoExecuta(cpfAluno,codigoTreino,data);
				relacionamentos.add(alunoExecuta);
			}
		return relacionamentos;
	}
}
