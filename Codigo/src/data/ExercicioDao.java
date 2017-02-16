package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import beans.Exercicio;

public class ExercicioDao implements IRepositorioAtividades<Exercicio,Integer>{
	
	private PreparedStatement statement;
	private ResultSet rSet;
	
	public ExercicioDao(){
		
	}
	
	@Override
	public boolean existe(Integer codigo) throws SQLException {
		
		boolean resultado = false;
		String sql = "SELECT * FROM academia.exercicio WHERE CODIGO_E = " + codigo;
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			this.rSet = (ResultSet) statement.executeQuery();
			
			if(rSet.next()){
				resultado = true;
			}
			return resultado;	
		
	}
	

	@Override
	public boolean cadastrar(Exercicio objeto) throws SQLException {
		String sql = "INSERT INTO academia.exercicio(CPF_P,NOME,CARGA,REPETICOES,INTERVALO,PADRAO) "
				+ "values(?,?,?,?,?,?)";
			
			
			statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, objeto.getCpfProfessor());
			statement.setString(2, objeto.getNome());
			statement.setString(3, objeto.getCarga());
			statement.setInt(4, objeto.getRepeticao());
			statement.setInt(5, objeto.getIntervalo());
			statement.setBoolean(6, objeto.isPadrao());
			statement.execute();
			
		return true;	
	}
	
				
	
	@Override
	public boolean remover(Exercicio objeto) throws SQLException {
		String sql = "DELETE FROM academia.exercicio WHERE CODIGO_E = ?";
		
				statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				statement.setInt(1, objeto.getCodigo());
				statement.execute();
			
			return true;	
	}
	
	@Override
	public boolean atualizar(Exercicio objeto) throws SQLException{
		
		String sql = "UPDATE exercicio SET NOME = ?,CARGA= ?,REPETICOES =?,INTERVALO=?  "
				+ " WHERE CODIGO_E = " + objeto.getCodigo();
				statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				statement.setString(1, objeto.getNome());
				statement.setString(2, objeto.getCarga());
				statement.setInt(3, objeto.getRepeticao());
				statement.setInt(4, objeto.getIntervalo());
				statement.execute();
				
		return true;
	}
	
	
	
	@Override
	public ArrayList<Exercicio> listar() throws SQLException{
		ArrayList<Exercicio> exercicios = new ArrayList<Exercicio>();
		String query = "SELECT * FROM exercicio";
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				int codigo = rSet.getInt("CODIGO_E");
				String cpfProf = rSet.getString("CPF_P");
				String nome = rSet.getString("NOME");
				String carga = rSet.getNString("CARGA");
				int repeticoes = rSet.getInt("REPETICOES");
				int intervalo = rSet.getInt("INTERVALO");
				boolean padrao = rSet.getBoolean("PADRAO");
				Exercicio exercicio = new Exercicio(codigo,cpfProf,nome,carga,repeticoes,intervalo,padrao);
				exercicios.add(exercicio);
			}
		
		return exercicios;
		
	}
		
	
	

	@Override
	public boolean cadastrar(Exercicio objeto, String cpfProf) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Exercicio buscar(Integer chave) throws SQLException {
		Exercicio resultado = null;
		String sql = "SELECT * FROM academia.exercicio WHERE CODIGO_E = " + chave;

		this.statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
		this.rSet = (ResultSet) statement.executeQuery();

		while (rSet.next()) {
			int codigo = rSet.getInt("CODIGO_E");
			String cpf = rSet.getString("CPF_P");
			String nome = rSet.getString("NOME");
			String carga = rSet.getNString("CARGA");
			int repeticoes = rSet.getInt("REPETICOES");
			int intervalo = rSet.getInt("INTERVALO");
			boolean padrao = rSet.getBoolean("PADRAO");
			resultado = new Exercicio(codigo,cpf, nome, carga, repeticoes, intervalo, padrao);
		}
		return resultado;
	}

	

	
}
