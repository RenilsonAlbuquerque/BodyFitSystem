package control;

import java.util.ArrayList;

import beans.Administrador;
import beans.Aluno;
import beans.Exercicio;
import beans.PerfisEnum;
import beans.Professor;
import beans.Treino;
import beans.Usuario;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;
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
		this.aluno = new ControladorAluno();
		this.professor = new ControladorProfessor();
		this.administrador = new ControladorAdministrador();
		this.treino = new ControladorTreino();
		this.exercicio = new ControladorExercicio();
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
	public ArrayList<PerfisEnum> getPerfis(String cpf) throws ConexaoBancoException, CRUDException{
		ArrayList<PerfisEnum> resultado = new ArrayList<PerfisEnum>();
		if(aluno.existe(cpf))
			resultado.add(PerfisEnum.aluno);
		if(professor.existe(cpf))
		{
			resultado.add(PerfisEnum.professor);
			if(professor.buscar(cpf).isCoordenador()){
				resultado.add(PerfisEnum.coordenador);
			}
		}
		if(administrador.existe(cpf))
			resultado.add(PerfisEnum.administrador);
		return resultado;
	}
	public void setUsuarioLogado(String cpf,PerfisEnum perfil ) throws ConexaoBancoException, CRUDException{
		if(perfil.equals(PerfisEnum.aluno))
			this.usuarioLogado = this.aluno.buscar(cpf);
		if(perfil.equals(PerfisEnum.professor))
			this.usuarioLogado = this.professor.buscar(cpf);
		if(perfil.equals(PerfisEnum.administrador))
			this.usuarioLogado = this.administrador.buscar(cpf);
	}
	public boolean autenticar(String cpf,String senha) throws ConexaoBancoException{
		return this.usuario.autenticar(cpf, senha);
	}
	
	public void atualizarUsuario(Usuario usuario) throws NegocioException, ConexaoBancoException, CRUDException{
		this.usuario.atualizar(usuario);
		if(usuario instanceof Aluno)
			this.aluno.atualizar((Aluno)usuario);
		if(usuario instanceof Administrador)
			this.administrador.atualizar((Administrador)usuario);
		if(usuario instanceof Professor)
			this.professor.atualizar((Professor)usuario);
	}
	
	public void cadastrarAluno(Aluno aluno) throws ConexaoBancoException, NegocioException, CRUDException{
		if(this.usuario.existe(aluno.getCpf()) == false)
			this.usuario.cadastrar(aluno);
		this.aluno.cadastrar(aluno);
	}
	public void cadastrarProfessor(Professor professor) throws ConexaoBancoException, NegocioException, CRUDException{
		if(this.usuario.existe(professor.getCpf()) == false)
			this.usuario.cadastrar(professor);
		this.professor.cadastrar(professor);
	}
	public void cadastrarAdministrador(Administrador administrador) throws ConexaoBancoException, NegocioException, CRUDException{
		if(this.usuario.existe(administrador.getCpf()) == false)
			this.usuario.cadastrar(administrador);
		this.administrador.cadastrar(administrador);
	}
	public void cadastrarTreinoPadrao(Treino treino) throws ConexaoBancoException, NegocioException, CRUDException{
		this.treino.cadastrarPadrao(treino);
	}
	public void cadastrarTreino(Treino treino,String cpfProf) throws ConexaoBancoException, NegocioException, CRUDException{
		this.treino.cadastrar(treino,cpfProf);
	}
	public void cadastrarExercicioPadrao(Exercicio exercicio) throws ConexaoBancoException, NegocioException, CRUDException{
		this.exercicio.cadastrarPadrao(exercicio);
	}
	public void cadastrarExercicio(Exercicio exercicio,String cpfProf) throws ConexaoBancoException, NegocioException, CRUDException{
		this.exercicio.cadastrar(exercicio, cpfProf);
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
	public void alterarTreino(Treino treino) throws ConexaoBancoException, CRUDException, NegocioException{
		this.treino.alterar(treino);
	}
	public void alterarExercicio(Exercicio exercicio) throws ConexaoBancoException, CRUDException, NegocioException{
		this.exercicio.alterar(exercicio);
	}
	public ArrayList<Aluno> listarAlunos() throws ConexaoBancoException, CRUDException, NegocioException{
		return this.aluno.listar();
	}
	public ArrayList<Professor> listarProfessores() throws ConexaoBancoException, CRUDException, NegocioException{
		return this.professor.listar();
	}
	public ArrayList<Administrador> listarAdministradores() throws ConexaoBancoException, CRUDException, NegocioException{
		return this.administrador.listar();
	}
	public ArrayList<Treino> treinosAluno(String cpf) throws ConexaoBancoException, CRUDException, NegocioException{
		ArrayList<Treino> resultado = this.treino.treinosAluno(cpf);
		if(!resultado.isEmpty()){
			for(Treino t : resultado){
				t.setExerciciosArray(this.exercicio.listar(t.getCodigo()));
			}
		}
		return resultado;
	}
	public ArrayList<Treino> listarTreinosPadrao() throws ConexaoBancoException, CRUDException, NegocioException{
		return this.treino.listar();
	}
	public ArrayList<Treino> listarTreinos(String cpfProf) throws ConexaoBancoException, CRUDException, NegocioException{
		return this.treino.listar(cpfProf);
	}
	public ArrayList<Exercicio> listarExerciciosPadrao() throws ConexaoBancoException, CRUDException, NegocioException{
		return this.exercicio.listar();
	}
	public ArrayList<Exercicio> listarExercicios(String cpfProf) throws ConexaoBancoException, CRUDException, NegocioException{
		return this.exercicio.listar(cpfProf);
	}
	public void removerAluno(Aluno aluno) throws ConexaoBancoException, CRUDException{
		this.aluno.remover(aluno);
	}
	public void removerProfessor(Professor professor) throws ConexaoBancoException, CRUDException, NegocioException{
		this.professor.remover(professor);
	}
	public void removerAdministrador(Administrador administrador) throws ConexaoBancoException, CRUDException, NegocioException{
		this.administrador.remover(administrador);
	}
	public void removerTreinoPadrao(Treino treino) throws ConexaoBancoException, CRUDException, NegocioException{
		this.treino.remover(treino);
	}
	public void removerTreino(Treino treino,String cpfProf) throws ConexaoBancoException, CRUDException, NegocioException{
		this.treino.remover(treino,cpfProf);
	}
	public void removerExercicioPadrao(Exercicio exercicio) throws ConexaoBancoException, CRUDException, NegocioException{
		this.exercicio.remover(exercicio);
	}
	public void removerExercicio(Exercicio exercicio,String cpfProf) throws ConexaoBancoException, CRUDException, NegocioException{
		this.exercicio.remover(exercicio,cpfProf);
	}
}
