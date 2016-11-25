package data;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import exceptions.ConexaoBancoException;



public class UsuarioDao implements IRepositorioUsuario {

	private PreparedStatement statement;
	private ResultSet rSet;
	
	public UsuarioDao(){
		
	}
	
	@Override
	public boolean autenticar(String cpf, String senha) throws ConexaoBancoException {
		boolean resultado = false;
		String query = "SELECT * FROM academia.usuario WHERE CPF = '" + cpf +"'" ;
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
}
