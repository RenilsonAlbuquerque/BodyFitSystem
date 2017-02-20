package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import beans.Treino;

public class TreinoDAO implements IAtividadesDao<Treino>{
	
	private static TreinoDAO instance;
	private PreparedStatement statement;
	private ResultSet rSet;
	
	private TreinoDAO(){
		
	}
	public static TreinoDAO getInstance(){
		if(instance == null)
			instance = new TreinoDAO();
		return instance;
	}
	
	@Override
	public boolean existe(int codigo) throws SQLException {
		
		boolean resultado = false;
		String sql = "SELECT * FROM academia.treino WHERE CODIGO_T = " + codigo;
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			this.rSet = (ResultSet) statement.executeQuery();
			
			if(rSet.next()){
				resultado = true;
			}
			return resultado;
	}
	

	@Override
	public int cadastrar(Treino objeto) throws SQLException {
		String sql = "INSERT INTO academia.treino(CPF_P,NOME,PADRAO) "
				+ "values(?,?,?)";
		
			statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, objeto.getCpfProfessor());
			statement.setString(2, objeto.getNome());
			statement.setBoolean(3, objeto.isPadrao());
			statement.execute();
			ResultSet st = statement.getGeneratedKeys();
			st.next();

		return st.getInt(1);

	}
	
	@Override
	public boolean remover(Treino objeto) throws SQLException {
		String sql = "DELETE FROM treino "
				+ " WHERE CODIGO_T = ?";
				
				statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				statement.setInt(1, objeto.getCodigo());
				statement.execute();
				
			return true;
	}
	
	@Override
	public boolean atualizar(Treino objeto) throws SQLException{
		String sql = "UPDATE academia.treino SET CODIGO_T = ?, NOME = ? "
				+ " WHERE CODIGO_T =" + objeto.getCodigo();
		
				statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				statement.setInt(1, objeto.getCodigo());
				statement.setString(2, objeto.getNome());
				statement.execute();
			return true;
	}
	
	
	
	@Override
	public ArrayList<Treino> listar() throws SQLException{
		ArrayList<Treino> treinos = new ArrayList<Treino>();
		String query = "SELECT * FROM academia.treino";
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				int codigo = rSet.getInt("CODIGO_T");
				String cpf = rSet.getString("CPF_P");
				String nome = rSet.getString("NOME");
				boolean padrao = rSet.getBoolean("PADRAO");
				
				Treino treino = new Treino(codigo,nome,cpf,padrao);
				treinos.add(treino);
			}
		
		return treinos;
		
	}

	@Override
	public Treino buscar(int chave) throws SQLException {
		Treino resultado = null;
		String sql = "SELECT * FROM academia.treino WHERE CODIGO_T = " + chave;
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			this.rSet = (ResultSet) statement.executeQuery();
			
			if(rSet.next()){
				int codigo = rSet.getInt("CODIGO_TP");
				String cpf = rSet.getString("CPF_P");
				String nome = rSet.getString("NOME");
				boolean padrao = rSet.getBoolean("PADRAO");
				resultado = new Treino(codigo,nome,cpf,padrao);
			}
			return resultado;
	}

	
		
}

	


