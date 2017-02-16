package dataTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import beans.Aluno;
import data.AlunoDao;
import data.UsuarioDao;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteAlunoDao {

	private Aluno aluno;
	private AlunoDao dao;
	private UsuarioDao daoUs;
	
	@Before
	public void comecar(){
		this.daoUs = new UsuarioDao();
		this.aluno = new Aluno("10870298445", "Roberto", "senha", "caminho",22, 70, 1.90f);
		this.dao = AlunoDao.getInstance();
		
	}
	
	
	
	@Test
	public void teste1Cadastrar(){
		try{
			dao.cadastrar(aluno);
			assertEquals(dao.existe(aluno.getCpf()),true);
		}
		catch(SQLException e){
			fail("Erro ao cadastrar o aluno");
		}
	}
	
	
	@Test
	public void teste2Atualizar(){
		try{
			this.aluno.setNome("pedro");
			dao.atualizar(aluno);
			assertEquals(dao.existe(aluno.getCpf()),true);
		}
		catch(SQLException e){
			fail("Erro ao atualizar o aluno");
		}
	}
	
	
	@Test
	public void teste3Listar(){
		try{
			assertNotNull(this.dao.listar());
			
		}catch(SQLException e){
			fail("Erro ao listar os alunos");
		}
	}
	
	@Test
	public void teste4Remover(){
		try{
			this.dao.remover(aluno);
			assertEquals(this.dao.existe(aluno.getCpf()),false);
		}catch(SQLException e){
			fail("Erro ao remover o aluno");
		}
	}
}
