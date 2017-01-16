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
	public void cadastrar(Aluno objeto) throws ConexaoBancoException,CRUDException {
		String sql = "INSERT INTO academia.aluno(CPF_ALU, IDADE, ALTURA, PESO) "
				+ "values(?,?,?,?)";
		
		try{
			statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, objeto.getCpf());
			statement.setInt(2, objeto.getIdade());
			statement.setFloat(3, objeto.getAltura());
			statement.setFloat(4, objeto.getPeso());
			statement.execute();
		}catch(SQLException e){
			throw new CRUDException("Erro ao cadastrar o Aluno");
		}
		finally{
			DBConnectionFactory.getInstance().closeConnetion();
		}
				
	}

	@Override
	public void remover(Aluno objeto) throws ConexaoBancoException,CRUDException {
		String sql = "DELETE FROM academia.aluno "
				+ " WHERE CPF_ALU = ?";
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setString(1, objeto.getCpf());
				smt.execute();
			}
			catch(SQLException e){
				throw new CRUDException("Erro ao deletar o Aluno");
			}
			finally{
				DBConnectionFactory.getInstance().closeConnetion();
			}
		
	}

	@Override
	public void atualizar(Aluno objeto) throws ConexaoBancoException,CRUDException{
		String sql = "UPDATE academia.aluno SET CPF_ALU = ?, IDADE = ?,ALTURA = ?, PESO = ? "
				+ " WHERE CPF_ALU =" + objeto.getCpf();
		
			PreparedStatement smt;
			try {
				smt = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				smt.setString(1, objeto.getCpf());
				smt.setInt(2, objeto.getIdade());
				smt.setFloat(3, objeto.getAltura());
				smt.setFloat(4, objeto.getPeso());
				smt.execute();
				
			} catch (Exception e) {
	
				throw new CRUDException("Erro ao alterar o usuario");
			}
			finally{
				DBConnectionFactory.getInstance().closeConnetion();
			}	
		
	}
	@Override
	public Aluno buscar(String cpf) throws ConexaoBancoException, CRUDException {
		Aluno aluno = null;;
		String sql = "SELECT * FROM aluno INNER JOIN academia.usuario ON aluno.CPF_ALU = usuario.CPF_U WHERE CPF_ALU =" + cpf;
		
		try{
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				String nome = rSet.getString("NOME");
				String senha = rSet.getString("SENHA");
				String caminhoFoto = rSet.getString("CAMINHO_FOTO");
				int idade = rSet.getInt("IDADE");
				float peso = rSet.getFloat("PESO");
				float altura = rSet.getFloat("ALTURA");
				aluno = new Aluno(cpf,nome,senha,caminhoFoto,idade,peso,altura);
			}
			return aluno;
			
		}catch(SQLException e){
			
			throw new ConexaoBancoException();
		}
		finally{

			DBConnectionFactory.getInstance().closeConnetion();
		}
	}
	@Override
	public ArrayList<Aluno> listar() throws ConexaoBancoException,CRUDException {
		ArrayList<Aluno> alunos = new ArrayList<Aluno>();
		String query = "SELECT * FROM academia.aluno INNER JOIN academia.usuario ON aluno.CPF_ALU = usuario.CPF_U";
		try{
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(query);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				String cpf = rSet.getString("CPF_ALU");
				String nome = rSet.getString("NOME");
				String senha = rSet.getString("SENHA");
				String caminhoFoto = rSet.getString("CAMINHO_FOTO");
				int idade = rSet.getInt("IDADE");
				float altura = rSet.getFloat("ALTURA");
				float peso = rSet.getFloat("PESO");
				
				
				alunos.add(new Aluno(cpf,nome,senha,caminhoFoto,idade,peso,altura));
			}
		}catch(SQLException  e){
			throw new CRUDException("Erro ao listar os alunos");
		}
		finally{
			DBConnectionFactory.getInstance().closeConnetion();
		}
		
		return alunos;
		
	}

	@Override
	public void cadastrarRotinaTreino(Aluno aluno) {
		
		
	}

	

}
