package dataTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import beans.Administrador;
import beans.Aluno;
import beans.Professor;
import data.AdministradorDao;
import data.AlunoDao;
import data.ProfessorDao;
import data.UsuarioDao;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteCRUDUsuarios {
	
	private UsuarioDao uDao;
	private AlunoDao aDao;
	private ProfessorDao pDao;
	private AdministradorDao dDao;
	
	private Aluno aluno;
	private Professor professor;
	private Administrador administrador;
	
	
	@Before
	public void inicio(){
		
		this.uDao = new UsuarioDao();
		this.aDao = AlunoDao.getInstance();
		this.pDao = ProfessorDao.getInstance();
		this.dDao = AdministradorDao.getInstance();
		
		this.aluno = new Aluno("10870298445", "Roberto", "senha", "caminho",22, 70, 1.90f);
		this.professor = new Professor("10784567244","maria","4321","caminho","Cref","manhã",true);
		this.administrador = new Administrador("16748392088","João","3241","milkway","patrão");
	}
	
	@Test
	public void testeA1CadastroUsuarios(){
		try{
			uDao.cadastrar(aluno);
			uDao.cadastrar(professor);
			uDao.cadastrar(administrador);
			assertEquals(uDao.existe(aluno.getCpf()),true);
			assertEquals(uDao.existe(professor.getCpf()),true);
			assertEquals(uDao.existe(administrador.getCpf()),true);
		}catch(SQLException e ){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testeA2CadastroAluno(){
		try{
			aDao.cadastrar(aluno);
			assertEquals(aDao.existe(aluno.getCpf()),true);
		}catch(SQLException e ){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testeA3CadastroProfessor(){
		try{
			pDao.cadastrar(professor);
			assertEquals(pDao.existe(professor.getCpf()),true);
		}catch(SQLException e ){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testeA4CadastroAdministrador(){
		try{
			dDao.cadastrar(administrador);
			assertEquals(dDao.existe(administrador.getCpf()),true);
		}catch(SQLException e ){
			fail(e.getMessage());
		}
	}
	@Test
	public void testeA5Autenticacao(){
		/*
		 try {
				boolean resultado = uDao.autenticar(aluno.getCpf(), aluno.getSenha());
				assertEquals(resultado,true);
			} catch (ConexaoBancoException  e) {
				fail(e.getMessage());
			}
		*/
	}
	@Test
	public void testeA6AtualizarUsuario(){
		try {
			this.aluno.setNome("Marcos");
			this.uDao.atualizar(aluno);
			assertEquals(uDao.existe(aluno.getCpf()), true);
		} catch (SQLException  e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testeA7AtualizarAluno(){
		try{
			this.aluno.setNome("Pedro");
			this.aDao.atualizar(aluno);
			assertEquals(aDao.existe(aluno.getCpf()), true);
		}catch(SQLException  e){
			fail(e.getMessage());
		}
	}
	@Test
	public void testeA8AtualizarProfessor(){
		try{
			this.professor.setNome("Joana");
			this.pDao.atualizar(professor);
			assertEquals(pDao.existe(professor.getCpf()), true);
		}catch(SQLException  e){
			fail(e.getMessage());
		}
	}
	@Test
	public void testeA9AtualizarAdministrador(){
		try{
			this.administrador.setNome("Carlos");
			this.dDao.atualizar(administrador);
			assertEquals(uDao.existe(aluno.getCpf()), true);
		}catch(SQLException  e){
			fail(e.getMessage());
		}
	}
	@Test
	public void testeB1RemoverAluno(){
		try {
			uDao.remover(aluno);
			assertEquals(aDao.existe(aluno.getCpf()), false);
		} catch (SQLException  e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testeB2RemoverAluno(){
		try {
			aDao.remover(aluno);
			assertEquals(aDao.existe(aluno.getCpf()), false);
		} catch (SQLException  e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testeB3RemoverProfessor(){
		try {
			pDao.remover(professor);
			assertEquals(pDao.existe(professor.getCpf()), false);
		} catch (SQLException  e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testeB4RemoverAdministrador(){
		try {
			dDao.remover(administrador);
			assertEquals(dDao.existe(administrador.getCpf()), false);
		} catch (SQLException  e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testeB5RemoverUsuarios(){
		try {
			uDao.remover(aluno);
			uDao.remover(professor);
			uDao.remover(administrador);
			assertEquals(uDao.existe(aluno.getCpf()), false);
			assertEquals(uDao.existe(professor.getCpf()), false);
			assertEquals(uDao.existe(administrador.getCpf()), false);
		} catch (SQLException  e) {
			fail(e.getMessage());
		}
	}
}
