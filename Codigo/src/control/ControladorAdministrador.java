package control;

import java.util.ArrayList;

import beans.Administrador;
import beans.Professor;
import beans.Usuario;
import data.AdministradorDao;
import data.IRepositorioFuncionarios;
import data.IRepositorioUsuario;
import data.UsuarioDao;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;
import exceptions.NegocioException;

public class ControladorAdministrador {
	private IRepositorioFuncionarios<Administrador> repositorio;
	private IRepositorioUsuario usuario;
	
	public ControladorAdministrador(){
		this.repositorio = new AdministradorDao();
		this.usuario = new UsuarioDao();
	}
	
	public void cadastrar(Administrador administrador) throws NegocioException, ConexaoBancoException, CRUDException{
		if(usuario.existe(administrador.getCpf())){
			this.repositorio.cadastrar(administrador);
		}else
			throw new NegocioException("CPF inválido");
		
	}
	public boolean existe(String cpf) throws ConexaoBancoException{
		return this.repositorio.existe(cpf);
	}
	public void atualizar(Administrador administrador) throws NegocioException, ConexaoBancoException, CRUDException{
		if(usuario.existe(administrador.getCpf())){
			this.repositorio.atualizar(administrador);
		}else
			throw new NegocioException("CPF inválido");
	}
	public Administrador buscar(String cpf) throws ConexaoBancoException, CRUDException{
		Administrador administrador = this.repositorio.buscar(cpf);
		Usuario usuario = this.usuario.buscar(cpf);
		administrador.setNome(usuario.getNome());
		administrador.setSenha(usuario.getSenha());
		administrador.setCaminhoFoto(usuario.getCaminhoFoto());
		return administrador;
	}
	
	public ArrayList<Administrador> listar() throws ConexaoBancoException, CRUDException, NegocioException{
		if(!(repositorio.listar().isEmpty())){
			return this.repositorio.listar();
		}
		else
			throw new NegocioException("Não existem Administradores cadastrados");
	}
	public void remover(Administrador administrador) throws ConexaoBancoException, CRUDException{
		repositorio.remover(administrador);
	}
}
