package control;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Usuario;
import data.DBConnectionFactory;
import data.InterfaceCRUD;
import data.UsuarioDao;
import exceptions.NegocioException;

public class ControladorUsuario {
	
	private InterfaceCRUD<Usuario,String> repositorio;
	
	public ControladorUsuario(){
		this.repositorio = new UsuarioDao();
	}
	
	public boolean autenticar(String cpf,String senha) throws NegocioException{
		int indice = -1;
		ArrayList<Usuario> usuarios = null;
		try {
			usuarios = repositorio.listar();
			for (int i = 0; i < usuarios.size(); i++) {
				if (usuarios.get(i).getCpf().equals(cpf)) {
					indice = i;
					break;
				}
			}
		} catch (SQLException e) {
			throw new NegocioException("Erro na conexão com o banco de dados");
		}
		if (indice > -1)
			if(usuarios.get(indice).getSenha().equals(senha))
				return true;
			else
				throw new NegocioException("Senha Incorreta");
		else
			throw new NegocioException("Login inválido");
		
		
	}
	
	public void cadastrar(Usuario usuario) throws NegocioException{
		try{
		if(Validadores.validarCPF(usuario.getCpf())){
			if(Validadores.validarSenha(usuario.getSenha())){
				this.repositorio.cadastrar(usuario);
				DBConnectionFactory.getInstance().getConnection().commit();
			}
			else
				throw new NegocioException("Senha inválida");
		}else
				throw new NegocioException("CPF inválido");
		}
		catch(SQLException e){
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
	}
	
	public boolean existe(String cpf) throws NegocioException{
		try {
			return this.repositorio.existe(cpf);
		} catch (SQLException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	public Usuario buscar(String cpf) throws NegocioException{
		try {
			return this.repositorio.buscar(cpf);
		} catch (SQLException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	
	public void atualizar(Usuario usuario) throws NegocioException{
		
		try{
		if(Validadores.validarCPF(usuario.getCpf())){
			if(Validadores.validarSenha(usuario.getSenha())){
				this.repositorio.atualizar(usuario);
				DBConnectionFactory.getInstance().getConnection().commit();
			}
			else
				throw new NegocioException("Senha inválida");
		}else
				throw new NegocioException("CPF inválido");
		}
		catch(SQLException e ){
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
	}
	
	public ArrayList<Usuario> listar() throws NegocioException{
		ArrayList<Usuario> resultado = new ArrayList<Usuario>();
		try {
			resultado = repositorio.listar();
		} catch (SQLException e) {
			throw new NegocioException(e.getMessage());
		}
		 
		if(!(resultado.isEmpty())){
			return resultado;
		}
		else
			throw new NegocioException("Não existem usuários cadastrados");
			
	}
		
	public void remover(Usuario usuario) throws NegocioException{
		try{
			repositorio.remover(usuario);
		}catch(SQLException e){
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
		
	}

}
