package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import beans.Treino;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;

public class TreinoDao implements IRepositorioAtividades<Treino>{
	
	private PreparedStatement statement;
	private ResultSet rSet;
	
	public TreinoDao(){
		
	}
	
	@Override
	public boolean existe(int codigo) throws ConexaoBancoException {
		
		boolean resultado = false;
		String sql = "SELECT * FROM academia.treino WHERE CODIGO_T = " + codigo;
		
		try{
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			this.rSet = (ResultSet) statement.executeQuery();
			
			if(rSet.next()){
				resultado = true;
			}
			return resultado;
			
		}catch(SQLException e){
			
			throw new ConexaoBancoException();
		}
		finally{
			DBConnectionFactory.getInstance().closeConnetion();
		}
		
	}
	@Override
	public boolean existe(int codigo,String cpf) throws ConexaoBancoException {
		
		boolean resultado = false;
		String sql = "SELECT * FROM academia.treino WHERE CODIGO_T = ? AND  CPF_P = ?";
		
		try{
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			this.statement.setInt(1, codigo);
			this.statement.setString(2, cpf);
			this.rSet = (ResultSet) statement.executeQuery();
			
			if(rSet.next()){
				resultado = true;
			}
			return resultado;
			
		}catch(SQLException e){
			
			throw new ConexaoBancoException();
		}
		finally{
			DBConnectionFactory.getInstance().closeConnetion();
		}
		
	}

	@Override
	public void cadastrar(Treino objeto) throws ConexaoBancoException,CRUDException {
		String sql = "INSERT INTO academia.treino_padrao(NOME) "
				+ "values(?)";
		
		try{
			statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, objeto.getNome());
			statement.execute();
		}catch(SQLException e){
			throw new CRUDException("Erro ao cadastrar o Treino");
		}
		finally{
			DBConnectionFactory.getInstance().closeConnetion();
		}
				
	}
	@Override
	public void cadastrar(Treino objeto, String cpfProf) throws ConexaoBancoException,CRUDException {
		String sql = "INSERT INTO academia.treino(CPF_P, NOME) "
				+ "values(?,?)";
		
		try{
			statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, cpfProf);
			statement.setString(2, objeto.getNome());
			statement.execute();
		}catch(SQLException e){
			throw new CRUDException("Erro ao cadastrar o Treino");
		}
		finally{
			DBConnectionFactory.getInstance().closeConnetion();
		}
				
	}
	@Override
	public void remover(Treino objeto) throws ConexaoBancoException,CRUDException {
		String sql = "DELETE FROM academia.treino_padrao "
				+ " WHERE CODIGO_TP = ?";
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setInt(1, objeto.getCodigo());
				smt.execute();
			}
			catch(SQLException e){
				throw new CRUDException("Erro ao deletar o Treino padrão");
			}
			finally{
				DBConnectionFactory.getInstance().closeConnetion();
			}
		
	}
	@Override
	public void remover(Treino objeto,String cpfProf) throws ConexaoBancoException,CRUDException {
		String sql = "DELETE FROM `treino` WHERE (CODIGO_T,CPF_P) = (?,?)";
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setInt(1, objeto.getCodigo());
				smt.setString(2, cpfProf);
				smt.execute();
			}
			catch(SQLException e){
				throw new CRUDException("Erro ao deletar o Treino");
			}
			finally{
				DBConnectionFactory.getInstance().closeConnetion();
			}
		
	}
	@Override
	public void atualizar(Treino objeto) throws ConexaoBancoException,CRUDException{
		String sql = "UPDATE academia.treino_padrao SET CODIGO_TP = ?, NOME = ? "
				+ " WHERE CODIGO_TP =" + objeto.getCodigo();
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setInt(1, objeto.getCodigo());
				smt.setString(2, objeto.getNome());
				smt.execute();
				
			} catch (Exception e) {
	
				throw new CRUDException("Erro ao alterar o treino padrão");
			}
			finally{
				DBConnectionFactory.getInstance().closeConnetion();
			}		
	}
	
	@Override
	public void atualizar(Treino objeto,String cpf) throws ConexaoBancoException,CRUDException{
		String sql = "UPDATE academia.treino SET CODIGO_T = ?, NOME = ? "
				+ " WHERE CODIGO_T =" + objeto.getCodigo();
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setInt(1, objeto.getCodigo());
				smt.setString(2, objeto.getNome());
				smt.execute();
				
			} catch (Exception e) {
	
				throw new CRUDException("Erro ao alterar o treino padrão");
			}
			finally{
				DBConnectionFactory.getInstance().closeConnetion();
			}	
		
	}
	
	@Override
	public ArrayList<Treino> listar() throws ConexaoBancoException,CRUDException {
		ArrayList<Treino> treinos = new ArrayList<Treino>();
		String query = "SELECT * FROM academia.treino_padrao";
		try{
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				int codigo = rSet.getInt("CODIGO_TP");
				String nome = rSet.getString("NOME");
				
				
				Treino treino = new Treino(codigo,nome);
				treinos.add(treino);
			}
		}catch(SQLException  e){
			throw new CRUDException("Erro ao listar os treinos padrão");
		}
		finally{
			DBConnectionFactory.getInstance().closeConnetion();
		}
		
		return treinos;
		
	}
	@Override
	public ArrayList<Treino> listar(String cpfProf) throws ConexaoBancoException,CRUDException {
		ArrayList<Treino> treinos = new ArrayList<Treino>();
		String query = "SELECT * FROM academia.treino WHERE CPF_P = " + cpfProf;
		try{
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				int codigo = rSet.getInt("CODIGO_TP");
				String nome = rSet.getString("NOME");
				
				Treino treino = new Treino(codigo,nome);
				treinos.add(treino);
			}
		}catch(SQLException  e){
			throw new CRUDException("Erro ao listar os treinos do professor");
		}
		finally{
			DBConnectionFactory.getInstance().closeConnetion();
		}
		
		return treinos;
		
	}

	@Override
	public Treino buscar(Integer chave) throws ConexaoBancoException, CRUDException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
