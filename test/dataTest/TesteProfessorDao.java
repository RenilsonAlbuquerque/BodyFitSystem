package dataTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import beans.Professor;
import data.ProfessorDao;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteProfessorDao {
	
	private ProfessorDao dao;
	private Professor professor;
	
	
	@Before
	public void iniciar(){
		this.dao = new ProfessorDao();
		this.professor = new Professor("10784567244","Cref","manhã");
	}
	
	@Test
	public void teste1Cadastrar(){
		try{
			dao.cadastrar(professor);
			assertEquals(dao.existe(professor.getCpf()),true);
		}catch(CRUDException | ConexaoBancoException e ){
			fail(e.getMessage());
		}
	}
	@Test
	public void teste2Atualizar(){
		try{
			dao.atualizar(professor);
			assertEquals(dao.existe(professor.getCpf()),true);
		}catch(CRUDException | ConexaoBancoException e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void teste3listar(){
		try{
			assertNotNull(dao.listar());
		}catch(CRUDException | ConexaoBancoException e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void teste4Remover(){
		try{
			dao.remover(professor);
			assertEquals(dao.existe(professor.getCpf()),true);
		}catch(CRUDException | ConexaoBancoException e){
			fail(e.getMessage());
		}
	}
}
