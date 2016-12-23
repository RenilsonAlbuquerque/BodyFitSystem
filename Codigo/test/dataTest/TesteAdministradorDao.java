package dataTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import beans.Administrador;
import data.AdministradorDao;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteAdministradorDao {

	private Administrador administrador;
	private AdministradorDao dao;
	
	@Before
	public void iniciar(){
		this.administrador = new Administrador("18754938744","chefe");
		this.dao = new AdministradorDao();
	}
	
	@Test
	public void teste1Cadastrar(){
		try{
			this.dao.cadastrar(administrador);
			assertEquals(dao.existe(administrador.getCpf()),true);
			
		}catch( ConexaoBancoException | CRUDException  e ){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void teste2Atualizar(){
		try{
			
			this.dao.atualizar(administrador);
			assertEquals(dao.existe(administrador.getCpf()),true);
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
			dao.remover(administrador);
			assertEquals(dao.existe(administrador.getCpf()),true);
		}catch(CRUDException | ConexaoBancoException e){
			fail(e.getMessage());
		}
	}
}
