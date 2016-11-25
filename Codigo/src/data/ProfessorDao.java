package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import beans.Professor;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;

public class ProfessorDao implements IRepositorio<Professor>{
	
	private PreparedStatement statement;
	private ResultSet rSet;
	
	public ProfessorDao(){
		
	}
	
	@Override
	public boolean existe(String cpf) throws ConexaoBancoException {
		boolean resultado = false;
		String sql = "SELECT * FROM professor WHERE CPF_PROF =" + cpf;
		
		try{
			this.statement= (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			this.rSet = (ResultSet) statement.executeQuery();
			
			if(rSet.next()){
				resultado = true;
			}
			return resultado;
			
		}catch(SQLException e){
			
			throw new ConexaoBancoException();
		}
		
	}

	@Override
	public void cadastrar(Professor objeto) throws ConexaoBancoException,CRUDException {
		String sql = "INSERT INTO academia.professor(CPF_PROF, CREF, TURNO) "
				+ "values(?,?,?)";
		
		try{
			statement = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, objeto.getCpf());
			statement.setString(2, objeto.getCref());
			statement.setString(3, objeto.getTurno());
			statement.execute();
			
		}catch(SQLException e){
			throw new CRUDException("Erro ao cadastrar o Professor");
		}
		finally{
			ConnectionFactory.getInstance().closeConnetion();
		}
				
	}

	@Override
	public void remover(Professor objeto) throws ConexaoBancoException,CRUDException {
		String sql = "DELETE FROM academia.professor "
				+ " WHERE CPF_PROF = ?";
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setString(1, objeto.getCpf());
				smt.execute();
			}
			catch(SQLException e){
				throw new CRUDException("Erro ao remover o Professor do sistema");
			}
			finally{
				ConnectionFactory.getInstance().closeConnetion();
			}
		
	}

	@Override
	public void atualizar(Professor objeto) throws ConexaoBancoException,CRUDException{
		String sql = "UPDATE academia.professor SET CPF_PROF = ?, CREF = ?,TURNO = ? "
				+ " WHERE CPF_PROF =" + objeto.getCpf();
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setString(1, objeto.getCpf());
				smt.setString(2, objeto.getCref());
				smt.setString(3, objeto.getTurno());
				smt.execute();
				
			} catch (Exception e) {
	
				throw new CRUDException("Erro ao alterar o Professor");
			}
			finally{
				ConnectionFactory.getInstance().closeConnetion();
			}	
		
	}

	@Override
	public ArrayList<Professor> listar() throws ConexaoBancoException,CRUDException {
		ArrayList<Professor> professores = new ArrayList<Professor>();
		String query = "SELECT * FROM academia.professor";
		try{
			this.statement= (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				String cpf = rSet.getString("CPF_PROF");
				String cref = rSet.getString("CREF");
				String turno = rSet.getString("TURNO");
				
				Professor professor = new Professor(cpf,cref,turno);
				professores.add(professor);
			}
		}catch(SQLException  e){
			throw new CRUDException("Erro ao listar os professores");
		}
		finally{
			ConnectionFactory.getInstance().closeConnetion();
		}
		
		return professores;
		
	}
}
