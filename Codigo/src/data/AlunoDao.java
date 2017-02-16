package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import beans.Aluno;


public class AlunoDao implements InterfaceCRUD<Aluno,String>{
	
	private static AlunoDao instance;
	private PreparedStatement statement;
	private ResultSet rSet;
	
	private AlunoDao(){
		
	}
	
	public static AlunoDao getInstance(){
		if(instance == null)
			instance = new AlunoDao();
		return instance;
	}

	@Override
	public boolean existe(String cpf) throws SQLException {
		
		boolean resultado = false;
		String sql = "SELECT * FROM aluno WHERE CPF_ALU =" + cpf;
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			this.rSet = (ResultSet) statement.executeQuery();
			
			if(rSet.next()){
				resultado = true;
			}
			return resultado;
	}

	@Override
	public boolean cadastrar(Aluno objeto) throws SQLException {
		String sql = "INSERT INTO academia.aluno(CPF_ALU, IDADE, ALTURA, PESO) "
				+ "values(?,?,?,?)";
		
			statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, objeto.getCpf());
			statement.setInt(2, objeto.getIdade());
			statement.setFloat(3, objeto.getAltura());
			statement.setFloat(4, objeto.getPeso());
			statement.execute();
		return true;
				
	}

	@Override
	public boolean remover(Aluno objeto)throws SQLException {
		String sql = "DELETE FROM academia.aluno "
				+ " WHERE CPF_ALU = ?";
		
				statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				statement.setString(1, objeto.getCpf());
				statement.execute();
			
		return true;	
		
	}

	@Override
	public boolean atualizar(Aluno objeto) throws SQLException{
		String sql = "UPDATE academia.aluno SET CPF_ALU = ?, IDADE = ?,ALTURA = ?, PESO = ? "
				+ " WHERE CPF_ALU =" + objeto.getCpf();
	
				statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				statement.setString(1, objeto.getCpf());
				statement.setInt(2, objeto.getIdade());
				statement.setFloat(3, objeto.getAltura());
				statement.setFloat(4, objeto.getPeso());
				statement.execute();
			return true;	
		
	}
	
	@Override
	public Aluno buscar(String cpf) throws SQLException {
		Aluno aluno = null;;
		String sql = "SELECT * FROM aluno INNER JOIN academia.usuario ON aluno.CPF_ALU = usuario.CPF_U WHERE CPF_ALU =" + cpf;
		
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

	}
	@Override
	public ArrayList<Aluno> listar()throws SQLException{
		ArrayList<Aluno> alunos = new ArrayList<Aluno>();
		String query = "SELECT * FROM academia.aluno INNER JOIN academia.usuario ON aluno.CPF_ALU = usuario.CPF_U";
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
		
		return alunos;
		
	}

	
	

	

}
