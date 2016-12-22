package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import beans.Administrador;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;

public class AdministradorDao implements IRepositorioFuncionarios<Administrador>{
	
	private PreparedStatement statement;
	private ResultSet rSet;
	
	public AdministradorDao(){
		
	}

	@Override
	public boolean existe(String cpf) throws ConexaoBancoException {
		boolean resultado = false;
		String sql = "SELECT * FROM administrador WHERE CPF_ADM =" + cpf;
		
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
		finally{
			ConnectionFactory.getInstance().closeConnetion();
		}
		
	}

	@Override
	public void cadastrar(Administrador objeto) throws ConexaoBancoException,CRUDException {
		String sql = "INSERT INTO academia.administrador(CPF_ADM, CARGO) "
				+ "values(?,?)";
		
		try{
			statement = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, objeto.getCpf());
			statement.setString(2, objeto.getCargo());
			statement.execute();
			
		}catch(SQLException e){
			throw new CRUDException(e.getMessage());
		}
		finally{
			ConnectionFactory.getInstance().closeConnetion();
		}
				
	}

	@Override
	public void remover(Administrador objeto) throws ConexaoBancoException,CRUDException {
		String sql = "DELETE FROM academia.administrador "
				+ " WHERE CPF_ADM = ?";
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setString(1, objeto.getCpf());
				smt.execute();
			}
			catch(SQLException e){
				throw new CRUDException("Erro ao deletar o Administrador");
			}
			finally{
				ConnectionFactory.getInstance().closeConnetion();
			}
		
	}

	@Override
	public void atualizar(Administrador objeto) throws ConexaoBancoException,CRUDException{
		String sql = "UPDATE academia.administrador SET CPF_ADM = ?, CARGO = ? "
				+ " WHERE CPF_ADM =" + objeto.getCpf();
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setString(1, objeto.getCpf());
				smt.setString(2, objeto.getCargo());
				smt.execute();
				
			} catch (Exception e) {
	
				throw new CRUDException("Erro ao alterar o Administrador");
			}
			finally{
				ConnectionFactory.getInstance().closeConnetion();
			}	
		
	}

	@Override
	public ArrayList<Administrador> listar() throws ConexaoBancoException,CRUDException {
		ArrayList<Administrador> administradores = new ArrayList<Administrador>();
		String query = "SELECT * FROM academia.administrador";
		try{
			this.statement= (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				String cpf = rSet.getString("CPF_ADM");
				String cargo = rSet.getString("CARGO");
				
				Administrador adm = new Administrador(cpf,cargo);
				administradores.add(adm);
			}
		}catch(SQLException  e){
			throw new CRUDException("Erro ao listar os administradores");
		}
		finally{
			ConnectionFactory.getInstance().closeConnetion();
		}
		
		return administradores;
		
	}


}
