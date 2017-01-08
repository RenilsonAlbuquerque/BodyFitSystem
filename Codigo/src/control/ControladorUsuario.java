package control;

import java.util.ArrayList;

import beans.Usuario;
import data.IRepositorioUsuario;
import data.UsuarioDao;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;
import exceptions.NegocioException;

public class ControladorUsuario {
	
	private IRepositorioUsuario controlador;
	
	public ControladorUsuario(){
		this.controlador = new UsuarioDao();
	}
	
	public boolean autenticar(String cpf,String senha) throws ConexaoBancoException{
		return this.controlador.autenticar(cpf, senha);
	}
	
	public void cadastrar(Usuario usuario) throws NegocioException, ConexaoBancoException, CRUDException{
		if(Validadores.validarCPF(usuario.getCpf())){
			if(Validadores.validarSenha(usuario.getSenha())){
				this.controlador.cadastrar(usuario);
			}
			else
				throw new NegocioException("Senha inválida");
		}else
				throw new NegocioException("CPF inválido");
		
	}
	
	public boolean existe(String cpf) throws ConexaoBancoException{
		return this.controlador.existe(cpf);
	}
	
	
	public void atualizar(Usuario usuario) throws NegocioException, ConexaoBancoException, CRUDException{
		if(Validadores.validarCPF(usuario.getCpf())){
			if(Validadores.validarSenha(usuario.getSenha())){
				this.controlador.atualizar(usuario);
			}
			else
				throw new NegocioException("Senha inválida");
		}else
				throw new NegocioException("CPF inválido");
	}
	
	public ArrayList<Usuario> listar() throws ConexaoBancoException, CRUDException, NegocioException{
		if(!(controlador.listar().isEmpty())){
			return this.controlador.listar();
		}
		else
			throw new NegocioException("Não existem usuários cadastrados");
	}
	public void remover(Usuario usuario) throws ConexaoBancoException, CRUDException{
		controlador.remover(usuario);
	}

}
