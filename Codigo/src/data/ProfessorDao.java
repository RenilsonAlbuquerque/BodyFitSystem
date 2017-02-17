package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import beans.Professor;

public class ProfessorDao implements InterfaceCRUD<Professor,String>{
	
	private static ProfessorDao instance;
	private PreparedStatement statement;
	private ResultSet rSet;
	
	private ProfessorDao(){
		
	}
	
	public static ProfessorDao getInstance(){
		if(instance == null)
			instance = new ProfessorDao();
		return instance;
	}
	
	@Override
	public boolean existe(String cpf) throws SQLException{
		boolean resultado = false;
		String sql = "SELECT * FROM professor WHERE CPF_PROF =" + cpf;
		
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			this.rSet = (ResultSet) statement.executeQuery();
			
			if(rSet.next()){
				resultado = true;
			}
			return resultado;
	}

	@Override
	public boolean cadastrar(Professor objeto)throws SQLException {
		String sql = "INSERT INTO academia.professor(CPF_PROF, CREF, TURNO,COORDENADOR) "
				+ "values(?,?,?,?)";
		
			statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, objeto.getCpf());
			statement.setString(2, objeto.getCref());
			statement.setString(3, objeto.getTurno());
			statement.setBoolean(4, objeto.isCoordenador());
			statement.execute();
			
		return true;
				
	}

	@Override
	public boolean remover(Professor objeto) throws SQLException {
		String sql = "DELETE FROM academia.professor "
				+ " WHERE CPF_PROF = ?";
		
				statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				statement.setString(1, objeto.getCpf());
				statement.execute();
		
			return true;
		
	}

	@Override
	public boolean atualizar(Professor objeto)throws SQLException{
		String sql = "UPDATE academia.professor SET  CREF = ?,TURNO = ?,COORDENADOR = ? "
				+ " WHERE CPF_PROF =" + objeto.getCpf();
		
			statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			
			statement.setString(1, objeto.getCref());
			statement.setString(2, objeto.getTurno());
			statement.setBoolean(3, objeto.isCoordenador());
			statement.execute();
				
		 
			return true;
		
	}
	@Override
	public Professor buscar(String cpf) throws SQLException{
		Professor professor = null;
		String sql = "SELECT * FROM professor WHERE CPF_PROF =" + cpf;
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				String cref = rSet.getString("CREF");
				String turno = rSet.getString("TURNO");
				boolean coordenador = rSet.getBoolean("COORDENADOR");
				professor = new Professor("",cref,turno,coordenador);
			}
			return professor;
			
		
	}
	@Override
	public ArrayList<Professor> listar() throws SQLException {
		ArrayList<Professor> professores = new ArrayList<Professor>();
		String query = "SELECT * FROM professor";

			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				String cpf = rSet.getString("CPF_PROF");
				String cref = rSet.getString("CREF");
				String turno = rSet.getString("TURNO");
				boolean coordenador = rSet.getBoolean("COORDENADOR");
				
				Professor professor = new Professor(cpf,cref,turno,coordenador);
				professores.add(professor);
			}
		
		return professores;
		
	}

	
}
