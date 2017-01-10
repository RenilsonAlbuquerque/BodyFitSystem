package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import beans.Exercicio;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;

public class ExercicioDao implements IRepositorioAtividades<Exercicio>{
	
	private PreparedStatement statement;
	private ResultSet rSet;
	
	public ExercicioDao(){
		
	}
	
	@Override
	public boolean existe(int codigo) throws ConexaoBancoException {
		
		boolean resultado = false;
		String sql = "SELECT * FROM academia.exercicio_padrao WHERE CODIGO_EP = " + codigo;
		
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
	public boolean existe(int codigo,String cpf) throws ConexaoBancoException {
		
		boolean resultado = false;
		String sql = "SELECT * FROM academia.exercicio WHERE CODIGO_E = ? AND  CPF_P = ?";
		
		try{
			this.statement= (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
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
		
	}

	@Override
	public void cadastrar(Exercicio objeto) throws ConexaoBancoException,CRUDException {
		String sql = "INSERT INTO academia.exercicio_padrao (NOME,CARGA,REPETICOES,INTERVALO) "
				+ "values(?,?,?,?,?)";
		
		try{
			statement = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, objeto.getNome());
			statement.setString(2, objeto.getCarga());
			statement.setInt(3, objeto.getRepeticao());
			statement.setInt(4, objeto.getIntervalo());
			statement.execute();
		}catch(SQLException e){
			throw new CRUDException("Erro ao cadastrar o Exercício padrão");
		}
		finally{
			ConnectionFactory.getInstance().closeConnetion();
		}
				
	}
	@Override
	public void cadastrar(Exercicio objeto, String cpfProf) throws ConexaoBancoException,CRUDException {
		String sql = "INSERT INTO academia.exercicio(CPF_P,NOME,CARGA,REPETICOES,INTERVALO) "
				+ "values(?,?,?,?,?) WHERE (CODIGO_T,CPF_P) = (?,?)";
		
		try{
			statement = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, cpfProf);
			statement.setString(2, objeto.getNome());
			statement.setString(3, objeto.getCarga());
			statement.setInt(4, objeto.getRepeticao());
			statement.setInt(5, objeto.getIntervalo());
			statement.setInt(6, objeto.getCodigo());
			statement.setInt(7, objeto.getIntervalo());
			statement.execute();
		}catch(SQLException e){
			throw new CRUDException("Erro ao cadastrar o Exercicio do professor");
		}
		finally{
			ConnectionFactory.getInstance().closeConnetion();
		}
				
	}
	@Override
	public void remover(Exercicio objeto) throws ConexaoBancoException,CRUDException {
		String sql = "DELETE FROM academia.exercicio_padrao "
				+ " WHERE CODIGO_EP = ?";
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setInt(1, objeto.getCodigo());
				smt.execute();
			}
			catch(SQLException e){
				throw new CRUDException("Erro ao deletar o Exercício padrão");
			}
			finally{
				ConnectionFactory.getInstance().closeConnetion();
			}
		
	}
	@Override
	public void remover(Exercicio objeto,String cpfProf) throws ConexaoBancoException,CRUDException {
		String sql = "DELETE FROM academia.exercicio WHERE (CODIGO_E,CPF_P) = (?,'?')";
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setInt(1, objeto.getCodigo());
				smt.setString(2, cpfProf);
				smt.execute();
			}
			catch(SQLException e){
				throw new CRUDException("Erro ao deletar o Exercício");
			}
			finally{
				ConnectionFactory.getInstance().closeConnetion();
			}
		
	}
	@Override
	public void atualizar(Exercicio objeto) throws ConexaoBancoException,CRUDException{
		String sql = "UPDATE academia.exercicio_padrao SET  NOME = ?,CARGA = ?,REPETICOES =?, INTERVALO =? "
				+ " WHERE CODIGO_EP =" + objeto.getCodigo();
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setString(1, objeto.getNome());
				smt.setString(2, objeto.getCarga());
				smt.setInt(3, objeto.getRepeticao());
				smt.setInt(4, objeto.getIntervalo());
				smt.execute();
				
			} catch (Exception e) {
	
				throw new CRUDException("Erro ao alterar o exercício padrão");
			}
			finally{
				ConnectionFactory.getInstance().closeConnetion();
			}		
	}
	
	@Override
	public void atualizar(Exercicio objeto,String cpf) throws ConexaoBancoException,CRUDException{
		String sql = "UPDATE academia.exercicio SET  NOME = ?,CARGA = ?,REPETICOES =?, INTERVALO =? "
				+ " WHERE (CODIGO_E,CPF_P) =(" + objeto.getCodigo() +",'"+ cpf+"')";
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setString(1, objeto.getNome());
				smt.setString(2, objeto.getCarga());
				smt.setInt(3, objeto.getRepeticao());
				smt.setInt(4, objeto.getIntervalo());
				smt.execute();
				
			} catch (Exception e) {
	
				throw new CRUDException("Erro ao alterar o exercício");
			}
			finally{
				ConnectionFactory.getInstance().closeConnetion();
			}	
		
	}
	
	@Override
	public ArrayList<Exercicio> listar() throws ConexaoBancoException,CRUDException {
		ArrayList<Exercicio> exercicios = new ArrayList<Exercicio>();
		String query = "SELECT * FROM academia.exercio_padrao";
		try{
			this.statement= (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				int codigo = rSet.getInt("CODIGO_TP");
				String nome = rSet.getString("NOME");
				String carga = rSet.getNString("CARGA");
				int repeticoes = rSet.getInt("REPETICOES");
				int intervalo = rSet.getInt("INTERVALO");
				Exercicio exercicio = new Exercicio(codigo,nome,carga,repeticoes,intervalo);
				exercicios.add(exercicio);
			}
		}catch(SQLException  e){
			throw new CRUDException("Erro ao listar os treinos padrão");
		}
		finally{
			ConnectionFactory.getInstance().closeConnetion();
		}
		
		return exercicios;
		
	}
	
	@Override
	public ArrayList<Exercicio> listar(String cpfProf) throws ConexaoBancoException,CRUDException {
		ArrayList<Exercicio> exercicios = new ArrayList<Exercicio>();
		String query = "SELECT * FROM academia.exercio WHERE CPF_P = '"+ cpfProf+ "'";
		try{
			this.statement= (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				int codigo = rSet.getInt("CODIGO_TP");
				String nome = rSet.getString("NOME");
				String carga = rSet.getNString("CARGA");
				int repeticoes = rSet.getInt("REPETICOES");
				int intervalo = rSet.getInt("INTERVALO");
				Exercicio exercicio = new Exercicio(codigo,nome,carga,repeticoes,intervalo);
				exercicios.add(exercicio);
			}
		}catch(SQLException  e){
			throw new CRUDException("Erro ao listar os treinos do professor");
		}
		finally{
			ConnectionFactory.getInstance().closeConnetion();
		}
		
		return exercicios;
	}

	

	
}
