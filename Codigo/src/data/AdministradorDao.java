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
	public void cadastrar(Administrador objeto) throws ConexaoBancoException,CRUDException {
		String sql = "INSERT INTO academia.administrador(CPF_ADM, CARGO) "
				+ "values(?,?)";
		
		try{
			statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, objeto.getCpf());
			statement.setString(2, objeto.getCargo());
			statement.execute();
			
		}catch(SQLException e){
			throw new CRUDException(e.getMessage());
		}
		finally{
			DBConnectionFactory.getInstance().closeConnetion();
		}
				
	}

	@Override
	public void remover(Administrador objeto) throws ConexaoBancoException,CRUDException {
		String sql = "DELETE FROM academia.administrador "
				+ " WHERE CPF_ADM = ?";
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setString(1, objeto.getCpf());
				smt.execute();
			}
			catch(SQLException e){
				throw new CRUDException("Erro ao deletar o Administrador");
			}
			finally{
				DBConnectionFactory.getInstance().closeConnetion();
			}
		
	}

	@Override
	public void atualizar(Administrador objeto) throws ConexaoBancoException,CRUDException{
		String sql = "UPDATE academia.administrador SET CPF_ADM = ?, CARGO = ? "
				+ " WHERE CPF_ADM =" + objeto.getCpf();
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setString(1, objeto.getCpf());
				smt.setString(2, objeto.getCargo());
				smt.execute();
				
			} catch (Exception e) {
	
				throw new CRUDException("Erro ao alterar o Administrador");
			}
			finally{
				DBConnectionFactory.getInstance().closeConnetion();
			}	
		
	}
	@Override
	public Administrador buscar(String cpf) throws ConexaoBancoException, CRUDException {
		Administrador adm = null;
		String sql = "SELECT * FROM administrador INNER JOIN academia.usuario ON administrador.CPF_ADM = usuario.CPF_U WHERE CPF_ADM =" + cpf;
		
		try{
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				String nome = rSet.getString("NOME");
				String senha = rSet.getString("SENHA");
				String caminhoFoto = rSet.getString("CAMINHO_FOTO");
				String cargo = rSet.getString("CARGO");
				adm = new Administrador(cpf,nome,senha,caminhoFoto,cargo);
			}
			return adm;
			
		}catch(SQLException e){
			
			throw new ConexaoBancoException();
		}
		finally{
			DBConnectionFactory.getInstance().closeConnetion();
		}
	}
	@Override
	public ArrayList<Administrador> listar() throws ConexaoBancoException,CRUDException {
		ArrayList<Administrador> administradores = new ArrayList<Administrador>();
		String query = "SELECT * FROM academia.administrador INNER JOIN academia.usuario ON administrador.CPF_ADM = usuario.CPF_U";
		try{
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				String cpf = rSet.getString("CPF_ADM");
				String nome = rSet.getString("NOME");
				String senha = rSet.getString("SENHA");
				String caminhoFoto = rSet.getString("CAMINHO_FOTO");
				String cargo = rSet.getString("CARGO");
				
				Administrador adm = new Administrador(cpf,nome,senha,caminhoFoto,cargo);
				administradores.add(adm);
			}
		}catch(SQLException  e){
			throw new CRUDException("Erro ao listar os administradores");
		}
		finally{
			DBConnectionFactory.getInstance().closeConnetion();
		}
		
		return administradores;
		
	}

}
