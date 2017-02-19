package control;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.Administrador;
import beans.Aluno;
import beans.PerfisEnum;
import beans.Professor;
import beans.Usuario;
import data.AdministradorDao;
import data.AlunoDao;
import data.DBConnectionFactory;
import data.InterfaceCRUD;
import data.ProfessorDao;
import data.UsuarioDao;
import exceptions.NegocioException;

public class ControladorUsuario {
	
	
	private static ControladorUsuario instance;	
	
	private InterfaceCRUD<Usuario,String> repositorio;
	private InterfaceCRUD<Aluno,String> repositorioAluno;
	private InterfaceCRUD<Administrador,String> repositorioAdministrador;
	private InterfaceCRUD<Professor,String> repositorioProfessor;
	
	
	
	
	
	
	private ControladorUsuario(){
		this.repositorio = new UsuarioDao();
		this.repositorioAluno = AlunoDao.getInstance();
		this.repositorioProfessor = ProfessorDao.getInstance();
		this.repositorioAdministrador = AdministradorDao.getInstance();
	}
	
	public static ControladorUsuario getInstance(){
		if(instance == null)
			instance = new ControladorUsuario();
		return instance;
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
			if (this.validacaoNegocio(usuario)) {
				if(!this.repositorio.existe(usuario.getCpf())){
					this.repositorio.cadastrar(usuario);
					DBConnectionFactory.getInstance().getConnection().commit();
				}
			}
		}
		catch(SQLException e){
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
	}
	public void cadastrar(ArrayList<Usuario> usuarios) throws NegocioException{
		Usuario usuarioACadastrar = usuarios.get(0);
		try{
			this.validacaoNegocio(usuarioACadastrar);
		}catch(NegocioException e){
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
			}
			throw new NegocioException(e.getMessage());
		}
		for(Usuario user : usuarios){
			if(user instanceof Aluno)
				usuarioACadastrar.getPerfis().add(PerfisEnum.aluno);
			if(user instanceof Professor)
				usuarioACadastrar.getPerfis().add(PerfisEnum.professor);
			if(user instanceof Administrador)
				usuarioACadastrar.getPerfis().add(PerfisEnum.administrador);
		}
		
		try {
			this.repositorio.cadastrar(usuarioACadastrar);
			for (Usuario usuario : usuarios) {
				if(usuario instanceof Aluno){
					this.repositorioAluno.cadastrar((Aluno)usuario);
					continue;
				}		
				if(usuario instanceof Professor){
					this.repositorioProfessor.cadastrar((Professor) usuario);
					continue;
				}	
				if(usuario instanceof Administrador){
					this.repositorioAdministrador.cadastrar((Administrador)usuario);
					continue;
				}
					
			}
			DBConnectionFactory.getInstance().getConnection().commit();
		} catch (SQLException e) {
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
			}
			throw new NegocioException(e.getMessage());
		}
		
		
	}
	public void atualizar(ArrayList<Usuario> usuarios) throws NegocioException{
		Usuario usuarioACadastrar = null;
		try{
			usuarioACadastrar = this.repositorio.buscar(usuarios.get(0).getCpf());
		}catch(SQLException e){
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
			}
			throw new NegocioException(e.getMessage());
		}
		usuarioACadastrar.getPerfis().clear();
		for(Usuario user : usuarios){
			if(user instanceof Aluno){
				usuarioACadastrar.getPerfis().add(PerfisEnum.aluno);
				continue;
			}
			if(user instanceof Professor){
				usuarioACadastrar.getPerfis().add(PerfisEnum.professor);
				continue;
			}
			if(user instanceof Administrador){
				usuarioACadastrar.getPerfis().add(PerfisEnum.administrador);
				continue;
			}
				
		}
		
		try {
			this.repositorio.atualizar(usuarioACadastrar);
			for (Usuario usuario : usuarios) {
				if(usuario instanceof Aluno){
					if(this.repositorioAluno.existe(usuario.getCpf())){
						this.repositorioAluno.atualizar((Aluno)usuario);
					}
					else{
						this.repositorioAluno.cadastrar((Aluno)usuario);
					}
					continue;	
				}
				if(usuario instanceof Professor){
					if(this.repositorioProfessor.existe(usuario.getCpf())){
						this.repositorioProfessor.atualizar((Professor)usuario);
					}
					else{
						this.repositorioProfessor.cadastrar((Professor)usuario);
					}
					continue;
				}
				if(usuario instanceof Administrador)
					if(this.repositorioAdministrador.existe(usuario.getCpf())){
						this.repositorioAdministrador.atualizar((Administrador)usuario);
					}
					else{
						this.repositorioAdministrador.cadastrar((Administrador)usuario);
					}
					continue;
			}
			DBConnectionFactory.getInstance().getConnection().commit();
			
		} catch (SQLException e) {
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
			}
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
			if(usuario.getPerfis().contains(PerfisEnum.aluno)){
				this.repositorioAluno.remover(this.repositorioAluno.buscar(usuario.getCpf()));
			}
			if(usuario.getPerfis().contains(PerfisEnum.professor)){
				this.repositorioProfessor.remover(this.repositorioProfessor.buscar(usuario.getCpf()));
			}if(usuario.getPerfis().contains(PerfisEnum.administrador)){
				this.repositorioAdministrador.remover(this.repositorioAdministrador.buscar(usuario.getCpf()));
			}
			this.repositorio.remover(usuario);
			DBConnectionFactory.getInstance().getConnection().commit();
		}
		catch(SQLException e){
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {}
			throw new NegocioException(e.getMessage());
		}
		
	}
	public ArrayList<PerfisEnum> getPerfis(String cpf) throws NegocioException {
		try {
			ArrayList<PerfisEnum> resultado = this.repositorio.buscar(cpf).getPerfis();
			if (resultado.contains(PerfisEnum.professor)) {
				if (this.repositorioProfessor.buscar(cpf).isCoordenador()) {
					resultado.add(PerfisEnum.coordenador);
				}
			}
			return resultado;
		} catch (SQLException e) {
			try {
				DBConnectionFactory.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
			}
			throw new NegocioException(e.getMessage());
		}
	}
	private boolean validacaoNegocio(Usuario usuario) throws NegocioException{
		if(Validadores.validarCPF(usuario.getCpf())){
			if(Validadores.validarSenha(usuario.getSenha())){
				return true;
			}
			else
				throw new NegocioException("Senha inválida");
		}else
				throw new NegocioException("CPF inválido");
		
	}

}
