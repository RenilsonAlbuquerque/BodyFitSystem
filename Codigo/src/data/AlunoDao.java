package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import beans.Aluno;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;


public class AlunoDao implements IRepositorioAluno{
	
	private PreparedStatement statement;
	private ResultSet rSet;
	
	public AlunoDao(){
		
	}

	@Override
	public boolean existe(String cpf) throws ConexaoBancoException {
		
		boolean resultado = false;
		String sql = "SELECT * FROM aluno WHERE CPF_ALU =" + cpf;
		
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
			//statement.close();
			ConnectionFactory.getInstance().closeConnetion();
		}
		
	}

	@Override
	public void cadastrar(Aluno objeto) throws ConexaoBancoException,CRUDException {
		String sql = "INSERT INTO academia.aluno(CPF_ALU, IDADE, ALTURA, PESO) "
				+ "values(?,?,?,?)";
		
		try{
			statement = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, objeto.getCpf());
			statement.setInt(2, objeto.getIdade());
			statement.setFloat(3, objeto.getAltura());
			statement.setFloat(4, objeto.getPeso());
			statement.execute();
		}catch(SQLException e){
			throw new CRUDException("Erro ao cadastrar o Aluno");
		}
		finally{
			ConnectionFactory.getInstance().closeConnetion();
		}
				
	}

	@Override
	public void remover(Aluno objeto) throws ConexaoBancoException,CRUDException {
		String sql = "DELETE FROM academia.aluno "
				+ " WHERE CPF_ALU = ?";
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setString(1, objeto.getCpf());
				smt.execute();
			}
			catch(SQLException e){
				throw new CRUDException("Erro ao deletar o Aluno");
			}
			finally{
				ConnectionFactory.getInstance().closeConnetion();
			}
		
	}

	@Override
	public void atualizar(Aluno objeto) throws ConexaoBancoException,CRUDException{
		String sql = "UPDATE academia.aluno SET CPF_ALU = ?, IDADE = ?,ALTURA = ?, PESO = ? "
				+ " WHERE CPF_ALU =" + objeto.getCpf();
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setString(1, objeto.getCpf());
				smt.setInt(2, objeto.getIdade());
				smt.setFloat(3, objeto.getAltura());
				smt.setFloat(4, objeto.getPeso());
				smt.execute();
				
			} catch (Exception e) {
	
				throw new CRUDException("Erro ao alterar o usuario");
			}
			finally{
				ConnectionFactory.getInstance().closeConnetion();
			}	
		
	}

	@Override
	public ArrayList<Aluno> listar() throws ConexaoBancoException,CRUDException {
		ArrayList<Aluno> alunos = new ArrayList<Aluno>();
		String query = "SELECT * FROM academia.aluno";
		try{
			this.statement= (PreparedStatement) ConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				String cpf = rSet.getString("CPF_ALU");
				int idade = rSet.getInt("IDADE");
				float altura = rSet.getFloat("ALTURA");
				float peso = rSet.getFloat("PESO");
				
				Aluno aluno = new Aluno(cpf,idade,altura,peso);
				alunos.add(aluno);
			}
		}catch(SQLException  e){
			throw new CRUDException("Erro ao listar os alunos");
		}
		finally{
			ConnectionFactory.getInstance().closeConnetion();
		}
		
		return alunos;
		
	}

	@Override
	public void cadastrarRotinaTreino(Aluno aluno) {
		// TODO Auto-generated method stub
		
	}

}
