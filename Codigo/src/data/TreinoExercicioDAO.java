package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import beans.TreinoExercicio;

public class TreinoExercicioDAO implements IRelacionamento<TreinoExercicio>{
	
	
	private static TreinoExercicioDAO instance;
	private PreparedStatement statement;
	private ResultSet rSet;
	
	private TreinoExercicioDAO(){
		
	}
	
	public static TreinoExercicioDAO getInstance(){
		if(instance == null)
			instance = new TreinoExercicioDAO();
		return instance;
	}
	
	@Override
	public boolean inserir(TreinoExercicio objeto) throws SQLException{
		String sql2 = "INSERT INTO treino_exercicio(CODIGO_TRE,CODIGO_EXE) VALUES (?,?)";
		statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql2);
		statement.setInt(1, objeto.getCodigoTreino());
		statement.setInt(2, objeto.getCodigoExercicio());
		statement.execute();
		
		return true;
	}


	@Override
	public boolean remover(TreinoExercicio objeto) throws SQLException {
		String sql2 = "DELETE FROM treino_exercicio WHERE (CODIGO_TRE = ? AND CODIGO_EXE = ?)";
		statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql2);
		statement.setInt(1, objeto.getCodigoTreino());
		statement.setInt(2, objeto.getCodigoExercicio());
		statement.execute();
		
		return true;
	}


	@Override
	public ArrayList<TreinoExercicio> listar() throws SQLException {
		ArrayList<TreinoExercicio> treinos = new ArrayList<TreinoExercicio>();
		String query = "SELECT * FROM treino_exercicio";
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				int codigoTreino = rSet.getInt("CODIGO_TRE");
				int codigoExercicio = rSet.getInt("CODIGO_EXE");
				
				TreinoExercicio treinoExercicio = new TreinoExercicio(codigoTreino,codigoExercicio);
				treinos.add(treinoExercicio);
			}
		
		return treinos;
	}
	@Override
	public ArrayList<TreinoExercicio> listar(int codigo) throws SQLException {
		ArrayList<TreinoExercicio> treinos = new ArrayList<TreinoExercicio>();
		String query = "SELECT * FROM treino_exercicio WHERE CODIGO_TRE = ?";
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.statement.setInt(1, codigo);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				int codigoTreino = rSet.getInt("CODIGO_TRE");
				int codigoExercicio = rSet.getInt("CODIGO_EXE");
				
				TreinoExercicio treinoExercicio = new TreinoExercicio(codigoTreino,codigoExercicio);
				treinos.add(treinoExercicio);
			}
		return treinos;
	}
}
