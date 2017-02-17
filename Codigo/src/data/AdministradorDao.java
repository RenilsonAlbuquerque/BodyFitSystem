package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import beans.Administrador;

public class AdministradorDao implements InterfaceCRUD<Administrador,String>{
	
	private static AdministradorDao instance;
	private PreparedStatement statement;
	private ResultSet rSet;
	
	private AdministradorDao(){
		
	}
	public static AdministradorDao getInstance(){
		if(instance == null)
			instance = new AdministradorDao();
		return instance;
	}
	@Override
	public boolean existe(String cpf) throws SQLException{
		boolean resultado = false;
		String sql = "SELECT * FROM administrador WHERE CPF_ADM =" + cpf;
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			this.rSet = (ResultSet) statement.executeQuery();
			
			if(rSet.next()){
				resultado = true;
			}
			return resultado;
		
	}

	@Override
	public boolean cadastrar(Administrador objeto) throws SQLException {
		String sql = "INSERT INTO administrador(CPF_ADM, CARGO) "
				+ " values(?,?)";
		
			statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, objeto.getCpf());
			statement.setString(2, objeto.getCargo());
			statement.execute();
		return true;	
	}

	@Override
	public boolean remover(Administrador objeto)throws SQLException{
		String sql = "DELETE FROM academia.administrador "
				+ " WHERE CPF_ADM = ?";
		
			
			statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, objeto.getCpf());
			statement.execute();
			
			return true;
		
	}

	@Override
	public boolean atualizar(Administrador objeto)throws SQLException{
		String sql = "UPDATE academia.administrador SET CPF_ADM = ?, CARGO = ? "
				+ " WHERE CPF_ADM =" + objeto.getCpf();
		
			statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, objeto.getCpf());
			statement.setString(2, objeto.getCargo());
			statement.execute();
				
			
		return true;
	}
	@Override
	public Administrador buscar(String cpf)throws SQLException{
		Administrador adm = null;
		String sql = "SELECT * FROM administrador WHERE CPF_ADM =" + cpf;
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				String cpfAdm = rSet.getString("CPF_ADM");
				String cargo = rSet.getString("CARGO");
				adm = new Administrador(cpfAdm,cargo);
			}
			return adm;
			
		
	}
	@Override
	public ArrayList<Administrador> listar() throws SQLException{
		ArrayList<Administrador> administradores = new ArrayList<Administrador>();
		String query = "SELECT * FROM administrador ";
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				String cpf = rSet.getString("CPF_ADM");
				String cargo = rSet.getString("CARGO");
				
				Administrador adm = new Administrador(cpf,cargo);
				administradores.add(adm);
			}
		
		return administradores;
		
	}

}
