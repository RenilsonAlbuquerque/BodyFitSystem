package dataTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import beans.Professor;
import data.ProfessorDao;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteProfessorDao {
	
	private ProfessorDao dao;
	private Professor professor;
	
	
	@Before
	public void iniciar(){
		this.dao = ProfessorDao.getInstance();
		this.professor = new Professor("10784567244","Cref","manhã",true);
	}
	
	@Test
	public void teste1Cadastrar(){
		try{
			dao.cadastrar(professor);
			assertEquals(dao.existe(professor.getCpf()),true);
		}catch(SQLException e ){
			fail(e.getMessage());
		}
	}
	@Test
	public void teste2Atualizar(){
		try{
			dao.atualizar(professor);
			assertEquals(dao.existe(professor.getCpf()),true);
		}catch(SQLException e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void teste3listar(){
		try{
			assertNotNull(dao.listar());
		}catch(SQLException e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void teste4Remover(){
		try{
			dao.remover(professor);
			assertEquals(dao.existe(professor.getCpf()),true);
		}catch(SQLException e){
			fail(e.getMessage());
		}
	}
}
