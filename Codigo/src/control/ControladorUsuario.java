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
	
	public boolean autenticar(String cpf,String senha) throws ConexaoBancoException, CRUDException, NegocioException{
		int indice = -1;
		ArrayList<Usuario> usuarios = this.listar();
		for(int i = 0; i < usuarios.size();i++){
			if(usuarios.get(i).getCpf().equals(cpf)){
				indice = i;
				break;
			}	
		}
		if(indice > -1  && usuarios.get(indice).getSenha().equals(senha))
			return true;
		else
			return false;
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
