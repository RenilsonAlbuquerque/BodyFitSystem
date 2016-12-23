package control;

import java.util.ArrayList;

import beans.Administrador;
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
	
	public void atualizar(Administrador administrador) throws NegocioException, ConexaoBancoException, CRUDException{
		if(usuario.existe(administrador.getCpf())){
			this.repositorio.atualizar(administrador);
		}else
			throw new NegocioException("CPF inválido");
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
