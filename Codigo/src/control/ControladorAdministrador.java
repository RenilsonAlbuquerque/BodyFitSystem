package control;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Administrador;
import beans.PerfisEnum;
import beans.Usuario;
import data.AdministradorDao;
import data.DBConnectionFactory;
import data.InterfaceCRUD;
import data.UsuarioDao;
import exceptions.NegocioException;

public class ControladorAdministrador {
	
	private static ControladorAdministrador instance;	
	private InterfaceCRUD<Administrador,String> repositorio;
	private InterfaceCRUD<Usuario,String> usuario;
	private ControladorUsuario controladorUsuario;
	
	private ControladorAdministrador(){
		this.repositorio = AdministradorDao.getInstance();
		this.usuario = new UsuarioDao();
		this.controladorUsuario = new ControladorUsuario();
		
	}
	
	public static ControladorAdministrador getInstance(){
		if(instance == null)
			instance = new ControladorAdministrador();
		return instance;
	}
	
	public void cadastrar(Administrador administrador) throws NegocioException{
		try {
			if(!this.repositorio.existe(administrador.getCpf())){
					
				if(!this.usuario.existe(administrador.getCpf())){
					this.usuario.cadastrar(administrador);
					this.repositorio.cadastrar(administrador);
				}else{
					
					Usuario u = usuario.buscar(administrador.getCpf());
					u.getPerfis().add(PerfisEnum.administrador);
					this.usuario.atualizar(u);
					this.repositorio.cadastrar(administrador);
				}
				DBConnectionFactory.getInstance().getConnection().commit();
				
			}else{			
				try {
					DBConnectionFactory.getInstance().getConnection().rollback();
				} catch (SQLException e1) {}
				throw new NegocioException("O administrador já está cadastrado");
				
			}
				
		} catch (SQLException e) {
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
		
	}
	/*
	public boolean existe(String cpf) throws ConexaoBancoException{
		return this.repositorio.existe(cpf);
	}
	*/
	public void atualizar(Administrador administrador) throws NegocioException{
		try {
			if (this.repositorio.existe(administrador.getCpf())) {
				this.repositorio.atualizar(administrador);
				DBConnectionFactory.getInstance().getConnection().commit();
			} else
				throw new NegocioException("O aluno não está cadastrado no sistema");
		} catch (SQLException e) {
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
	}
	
	public Administrador buscar(String cpf) throws NegocioException{
		try{
			return this.repositorio.buscar(cpf);
		}catch(SQLException e){
			throw new NegocioException(e.getMessage());
		}
		
	}

	public ArrayList<Administrador> listar() throws NegocioException{
		ArrayList<Administrador> resultado = new ArrayList<Administrador>();
		try {
			resultado = repositorio.listar();
		} catch (SQLException e) {
			throw new NegocioException(e.getMessage());
		}
		 
		if(!(resultado.isEmpty())){
			return resultado;
		}
		else
			throw new NegocioException("Não existem administradores cadastrados");
	}
	public void remover(Administrador administrador) throws NegocioException{
		try{
			if(this.repositorio.existe(administrador.getCpf())){
				repositorio.remover(administrador);
				DBConnectionFactory.getInstance().getConnection().commit();
			}
			else{
				throw new NegocioException("O administrador não está cadaastrado no sistema");
			}
			
		}
		catch(SQLException e){
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
	}
}
