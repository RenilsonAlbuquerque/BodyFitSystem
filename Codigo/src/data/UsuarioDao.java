package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import beans.PerfisEnum;
import beans.Usuario;



public class UsuarioDao implements InterfaceCRUD<Usuario,String>{

	private PreparedStatement statement;
	private ResultSet rSet;
	
	public UsuarioDao(){
		
	}
		
	@Override
	public Usuario buscar(String cpf)throws SQLException{
		Usuario usuario = null;
		String sql = "SELECT * FROM usuario WHERE CPF_U =" + cpf;
		
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			this.rSet = (ResultSet) statement.executeQuery();
			
			while(rSet.next()){
				String nome = rSet.getString("NOME");
				String senha = rSet.getString("SENHA");
				String caminhoFoto = rSet.getString("CAMINHO_FOTO");
				ArrayList<PerfisEnum> perfis = new ArrayList<PerfisEnum>();
				if(rSet.getInt("USUARIOALUNO") == 1)
					perfis.add(PerfisEnum.aluno);
				if(rSet.getInt("USUARIOPROFESSOR") == 1)
					perfis.add(PerfisEnum.professor);
				if(rSet.getInt("USUARIOADMINISTRADOR") == 1)
					perfis.add(PerfisEnum.administrador);
				
				usuario = new Usuario(cpf,nome,senha,caminhoFoto,perfis);
			}
			return usuario;
	}

	@Override
	public boolean cadastrar(Usuario objeto)throws SQLException {
		String sql = "INSERT INTO usuario(CPF_U,NOME, SENHA, CAMINHO_FOTO,USUARIOALUNO,USUARIOPROFESSOR,USUARIOADMINISTRADOR) "
				+ "values(?,?,?,?,?,?,?)";
		
			statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, objeto.getCpf());
			statement.setString(2, objeto.getNome());
			statement.setString(3, objeto.getSenha());
			statement.setString(4, objeto.getCaminhoFoto());
			statement.setBoolean(5, objeto.getPerfis().contains(PerfisEnum.aluno));
			statement.setBoolean(6, objeto.getPerfis().contains(PerfisEnum.professor) );
			statement.setBoolean(7, objeto.getPerfis().contains(PerfisEnum.administrador) );
			statement.execute();
		return true;
	}

	@Override
	public boolean remover(Usuario objeto) throws SQLException {
		String sql = "DELETE FROM academia.usuario WHERE CPF_U = ?";
		
		
		statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
		statement.setString(1, objeto.getCpf());
		statement.execute();		
		return true;
	}

	@Override
	public boolean atualizar(Usuario objeto)throws SQLException{
		String sql = "UPDATE usuario SET NOME =?, SENHA =?, CAMINHO_FOTO =?, USUARIOALUNO =?, USUARIOPROFESSOR = ?, "
				+ " USUARIOADMINISTRADOR = ? WHERE CPF_U = ?";
		
				DBConnectionFactory.getInstance().getConnection().setAutoCommit(true);
				statement = (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
				
				//statement.setString(, objeto.getCpf());
				statement.setString(1, objeto.getNome());
				statement.setString(2, objeto.getSenha());
				statement.setString(3, objeto.getCaminhoFoto());
				statement.setBoolean(4, objeto.getPerfis().contains(PerfisEnum.aluno));
				statement.setBoolean(5, objeto.getPerfis().contains(PerfisEnum.professor));
				statement.setBoolean(6, objeto.getPerfis().contains(PerfisEnum.administrador));
				statement.setString(7, objeto.getCpf());
				statement.execute();
				return true;
	}
	
	@Override
	public ArrayList<Usuario> listar()throws SQLException  {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		String query = "SELECT * FROM academia.usuario";
		
		this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(query);
		this.rSet = (ResultSet) statement.executeQuery();
			
		while(rSet.next()){
			String cpf = rSet.getString("CPF_U");
			String nome = rSet.getString("NOME");
			String senha = rSet.getString("SENHA");
			String caminhoFoto = rSet.getString("CAMINHO_FOTO");
			int aluno = rSet.getInt("USUARIOALUNO");
			int professor = rSet.getInt("USUARIOPROFESSOR");
			int administrador = rSet.getInt("USUARIOADMINISTRADOR");
			ArrayList<PerfisEnum> perfis = new ArrayList<PerfisEnum>();
			if(aluno == 1)
				perfis.add(PerfisEnum.aluno);
			if(professor == 1)
				perfis.add(PerfisEnum.professor);
			if(administrador == 1)
				perfis.add(PerfisEnum.administrador);
			
			
			usuarios.add(new Usuario(cpf,nome,senha,caminhoFoto,perfis));
		}
		return usuarios;
	}
	
	@Override
	public boolean existe(String cpf)throws SQLException{
		boolean resultado = false;
		String sql = "SELECT * FROM usuario WHERE CPF_U ='" + cpf+"'";
		
			this.statement= (PreparedStatement) DBConnectionFactory.getInstance().getConnection().prepareStatement(sql);
			this.rSet = (ResultSet) statement.executeQuery();
			
			if(rSet.next()){
				resultado = true;
			}
			return resultado;
		
	}


	
}
