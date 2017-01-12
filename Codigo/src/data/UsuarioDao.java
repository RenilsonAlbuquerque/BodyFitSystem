package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import beans.Usuario;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;



public class UsuarioDao implements IRepositorioUsuario {

	private PreparedStatement statement;
	private ResultSet rSet;
	
	public UsuarioDao(){
		
	}
	
	@Override
	public boolean autenticar(String cpf, String senha) throws ConexaoBancoException {
		boolean resultado = false;
		String query = "SELECT * FROM academia.usuario WHERE CPF_U = '" + cpf +"'" ;
		try{
			this.statement= (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.rSet = (ResultSet) statement.executeQuery();
			
		
		if(rSet.next()){
			String rSenha = rSet.getString("SENHA");
			if(rSenha.equals(senha)){
				
				resultado = true;
			}
			else
				resultado = false;
		}
		else
			resultado =  false;
		
		return resultado;
		
		}catch(SQLException e){
			throw new ConexaoBancoException();
		}
		finally{
			
				ConnectionFactory.getInstance().closeConnetion();
		}
		
	}
	@Override
	public Usuario buscar(String cpf) throws ConexaoBancoException, CRUDException {
		Usuario usuario = null;;
		String sql = "SELECT * FROM usuario WHERE CPF_U =" + cpf;
		
		try{
			this.statement= (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				String nome = rSet.getString("NOME");
				String senha = rSet.getString("SENHA");
				String caminhoFoto = rSet.getString("CAMINHO_FOTO");
				usuario = new Usuario(cpf,nome,senha,caminhoFoto);
			}
			return usuario;
			
		}catch(SQLException e){
			
			throw new ConexaoBancoException();
		}
		finally{

			ConnectionFactory.getInstance().closeConnetion();
		}
	}

	@Override
	public void cadastrar(Usuario objeto) throws ConexaoBancoException, CRUDException {
		String sql = "INSERT INTO academia.usuario(CPF_U,NOME, SENHA, CAMINHO_FOTO) "
				+ "values(?,?,?,?)";
		
		try{
			statement = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, objeto.getCpf());
			statement.setString(2, objeto.getNome());
			statement.setString(3, objeto.getSenha());
			statement.setString(4, objeto.getCaminhoFoto());
			statement.execute();
		}catch(SQLException e){
			throw new CRUDException("Erro ao cadastrar o Usuário");
		}
		finally{
			ConnectionFactory.getInstance().closeConnetion();
		}
			
	}

	@Override
	public void remover(Usuario objeto) throws ConexaoBancoException, CRUDException {
		String sql = "DELETE FROM academia.usuario "
				+ " WHERE CPF_U = (?)";
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setString(1, objeto.getCpf());
				smt.execute();
			}
			catch(SQLException e){
				throw new CRUDException("Erro ao deletar o Usuário");
			}
			finally{
				ConnectionFactory.getInstance().closeConnetion();
			}
		
	}

	@Override
	public void atualizar(Usuario objeto) throws ConexaoBancoException, CRUDException {
		String sql = "UPDATE academia.usuario SET CPF_U = ?, NOME =?, SENHA =?, CAMINHO_FOTO =? "
				+ " WHERE CPF_U =" +objeto.getCpf();
		
			try {
				ConnectionFactory.getInstance().getConnection().setAutoCommit(true);
				statement = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				
				statement.setString(1, objeto.getCpf());
				statement.setString(2, objeto.getNome());
				statement.setString(3, objeto.getSenha());
				statement.setString(4, objeto.getCaminhoFoto());
				
				statement.execute();
				
				
			} catch (SQLException e) {
	
				throw new CRUDException("Erro ao alterar o usuario");
				
			}
			finally{
				ConnectionFactory.getInstance().closeConnetion();
			}		
			
	}
	
	@Override
	public ArrayList<Usuario> listar() throws ConexaoBancoException, CRUDException {
		return null;
	}
	
	@Override
	public boolean existe(String cpf) throws ConexaoBancoException{
		boolean resultado = false;
		String sql = "SELECT * FROM usuario WHERE CPF_U ='" + cpf+"'";
		
		try{
			this.statement= (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			this.rSet = (ResultSet) statement.executeQuery();
			
			if(rSet.next()){
				resultado = true;
			}
			return resultado;
			
		}catch(SQLException  e){
			
			throw new ConexaoBancoException();
		}
	}

	

	
	
}
