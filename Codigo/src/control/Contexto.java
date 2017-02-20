package control;

import beans.Administrador;
import beans.Aluno;
import beans.PerfisEnum;
import beans.Professor;
import beans.Usuario;
import exceptions.NegocioException;

public class Contexto {

	private static Contexto instance;
	private ControladorUsuario usuario;
	private ControladorAluno aluno;
	private ControladorProfessor professor;
	private ControladorAdministrador administrador;
	private Usuario usuarioLogado;
	
	private Contexto(){
		this.usuario = ControladorUsuario.getInstance();
		this.aluno = ControladorAluno.getInstance();
		this.professor = ControladorProfessor.getInstance();
		this.administrador = ControladorAdministrador.getInstance();
	}
	
	public static Contexto getInstance(){
		if(instance == null)
			instance = new Contexto();
		return instance;
	}
	
	public Usuario getUsuarioLogado(){
		return this.usuarioLogado;
	}
	public void setUsuarioLogado(Usuario usuario){
		this.usuarioLogado = usuario;
	}
	public void setUsuarioLogado(String cpf,PerfisEnum perfil ) throws NegocioException{
		Usuario usuario = this.usuario.buscar(cpf);
		
		if(perfil.equals(PerfisEnum.aluno)){
			Aluno a = this.aluno.buscar(cpf);
			a.setNome(usuario.getNome());
			a.setCpf(usuario.getCpf());
			a.setSenha(usuario.getSenha());
			a.setCaminhoFoto(usuario.getCaminhoFoto());
			a.setPerfis(usuario.getPerfis());
			this.usuarioLogado = a;
		}
			
		if(perfil.equals(PerfisEnum.professor) || perfil.equals(PerfisEnum.coordenador)){
			Professor p = this.professor.buscar(cpf);
			p.setNome(usuario.getNome());
			p.setCpf(usuario.getCpf());
			p.setSenha(usuario.getSenha());
			p.setCaminhoFoto(usuario.getCaminhoFoto());
			p.setPerfis(usuario.getPerfis());
			this.usuarioLogado = p;
		}
		if(perfil.equals(PerfisEnum.administrador)){
			Administrador a = this.administrador.buscar(cpf);
			a.setNome(usuario.getNome());
			a.setCpf(usuario.getCpf());
			a.setSenha(usuario.getSenha());
			a.setCaminhoFoto(usuario.getCaminhoFoto());
			a.setPerfis(usuario.getPerfis());
			this.usuarioLogado = a;
		}
			
	}
	
}
