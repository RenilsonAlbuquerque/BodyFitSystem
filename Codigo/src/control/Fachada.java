package control;

import java.util.ArrayList;

import beans.Administrador;
import beans.Aluno;
import beans.Exercicio;
import beans.PerfisEnum;
import beans.Professor;
import beans.Treino;
import beans.Usuario;
import exceptions.NegocioException;

public class Fachada {
	

	private static Fachada instance;
	private Usuario usuarioLogado;
	
	private ControladorUsuario usuario;
	private ControladorAluno aluno;
	private ControladorProfessor professor;
	private ControladorAdministrador administrador;
	private ControladorTreino treino;
	private ControladorExercicio exercicio;

	private Fachada(){
		this.usuario = new ControladorUsuario();
		this.aluno = ControladorAluno.getInstance();
		this.professor = ControladorProfessor.getInstance();
		this.administrador = ControladorAdministrador.getInstance();
		this.treino = ControladorTreino.getInstance();
		this.exercicio = ControladorExercicio.getInstance();
	}
	
	public static Fachada getInstance(){
		if(instance == null){
			instance = new Fachada();
		}
		return instance;
	}
	
	public Usuario getUsuarioLogado(){
		return this.usuarioLogado;
	}
	public void setUsuarioLogado(Usuario usuario){
		this.usuarioLogado = usuario;
	}
	public void setUsuarioLogado(String cpf,PerfisEnum perfil ) throws NegocioException{
		if(perfil.equals(PerfisEnum.aluno))
			this.usuarioLogado = this.aluno.buscar(cpf);
		if(perfil.equals(PerfisEnum.professor))
			this.usuarioLogado = this.professor.buscar(cpf);
		if(perfil.equals(PerfisEnum.administrador))
			this.usuarioLogado = this.administrador.buscar(cpf);
	}
	public boolean autenticar(String cpf,String senha) throws NegocioException{
		return this.usuario.autenticar(cpf, senha);
	}
	public ArrayList<PerfisEnum> getPerfisUsuario(String cpf) throws NegocioException{
		return this.usuario.getPerfis(cpf);
	}
	
	public void atualizarUsuario(Usuario usuario) throws NegocioException{
		this.usuario.atualizar(usuario);
		if(usuario instanceof Aluno)
			this.aluno.atualizar((Aluno)usuario);
		if(usuario instanceof Administrador)
			this.administrador.atualizar((Administrador)usuario);
		if(usuario instanceof Professor)
			this.professor.atualizar((Professor)usuario);
	}
	
	public ArrayList<Usuario> getPerfisObject(Usuario usuario) throws NegocioException{
		ArrayList<Usuario> resultado = new ArrayList<Usuario>();
		if(usuario.getPerfis().contains(PerfisEnum.aluno))
			resultado.add(aluno.buscar(usuario.getCpf()));
		if(usuario.getPerfis().contains(PerfisEnum.professor))
			resultado.add(professor.buscar(usuario.getCpf()));
		if(usuario.getPerfis().contains(PerfisEnum.administrador))
			resultado.add(administrador.buscar(usuario.getCpf()));
		
		return resultado;
		
	}
	public void cadastrarUsuario(ArrayList<Usuario> usuarios) throws NegocioException{
		this.usuario.cadastrar(usuarios);
	}
	public void alterarUsuario(ArrayList<Usuario> perfis) throws NegocioException{
		this.usuario.atualizar(perfis);
	}
	/*
	public void cadastrarAluno(Aluno aluno) throws NegocioException{
		this.aluno.cadastrar(aluno);
	}
	public void cadastrarProfessor(Professor professor) throws NegocioException{
		this.professor.cadastrar(professor);
	}
	public void cadastrarAdministrador(Administrador administrador) throws NegocioException{
		this.administrador.cadastrar(administrador);
	}
	*/
	
	public void cadastrarTreino(Treino treino) throws NegocioException{
		this.treino.cadastrar(treino);
	}
	public void cadastrarExercicio(Exercicio exercicio) throws NegocioException{
		this.exercicio.cadastrar(exercicio);
	}
	/*
	public void alterarAluno(Aluno aluno) throws NegocioException, ConexaoBancoException, CRUDException{
		this.aluno.atualizar(aluno);
	}
	public void alterarProfessor(Professor professor) throws NegocioException, ConexaoBancoException, CRUDException{
		this.professor.atualizar(professor);
	}
	public void alterarAdministrador(Administrador administrador) throws NegocioException, ConexaoBancoException, CRUDException{
		this.administrador.atualizar(administrador);
	}
	*/
	
	public void alterarTreino(Treino treino) throws NegocioException{
		this.treino.alterar(treino);
	}
	public void alterarExercicio(Exercicio exercicio) throws NegocioException{
		this.exercicio.alterar(exercicio);
	}
	
	public ArrayList<Aluno> listarAlunos() throws NegocioException{
		return this.aluno.listar();
	}
	public ArrayList<Professor> listarProfessores() throws NegocioException{
		return this.professor.listar();
	}
	public ArrayList<Administrador> listarAdministradores() throws NegocioException{
		return this.administrador.listar();
	}
	
	public ArrayList<Treino> listarTreinosPadrao() throws  NegocioException{
		return this.treino.listarPadrao();
	}
	public ArrayList<Treino> listarTreinos(String cpf) throws NegocioException{
		return this.treino.listar(cpf);
	}
	public ArrayList<Exercicio> listarExerciciosPadrao() throws NegocioException{
		return this.exercicio.listarPadrao();
	}
	public ArrayList<Exercicio> listarExercicios(String cpfProf) throws NegocioException{
		return this.exercicio.listar(cpfProf);
	}
	public void remover(Usuario usuario) throws NegocioException{
		this.usuario.remover(usuario);
	}
	/*
	public void removerAluno(Aluno aluno) throws NegocioException{
		this.aluno.remover(aluno);
	}
	public void removerProfessor(Professor professor) throws NegocioException{
		this.professor.remover(professor);
	}
	public void removerAdministrador(Administrador administrador) throws NegocioException{
		this.administrador.remover(administrador);
	}
	*/
	public void removerTreino(Treino treino) throws NegocioException{
		this.treino.remover(treino);
	}
	public void removerExercicio(Exercicio exercicio) throws NegocioException{
		this.exercicio.remover(exercicio);
	}
}
