package dataTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import beans.Aluno;
import data.UsuarioDao;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteUsuarioDao {
	
	private Aluno usuario;
	private UsuarioDao dao;
	
	@Before
	public void iniciar(){
		this.usuario = new Aluno("12345678910","Renilson","1234", "caminho",22, 70, 1.90f);
		this.dao = new UsuarioDao();
	}
	
	
	@Test
	public void teste1Cadastrar(){
		try {
			dao.cadastrar(usuario);
			assertEquals(dao.existe(usuario.getCpf()), true);
		} catch (ConexaoBancoException | CRUDException  e) {
			fail("Falha na conexão com o banco de dados");
		}
	}
	
	@Test
	public void teste2Autenticacao(){
		
		 try {
				boolean resultado = dao.autenticar(usuario.getCpf(), usuario.getSenha());
				assertEquals(resultado,true);
			} catch (ConexaoBancoException  e) {
				fail("Falha na conexão com o banco de dados");
			}
		
	}
	
	@Test
	public void teste3Atualizar(){
		try {
			this.usuario.setNome("Maria");
			this.dao.atualizar(usuario);
			assertEquals(dao.existe(usuario.getCpf()), true);
			
		} catch (ConexaoBancoException | CRUDException  e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void teste4Remover(){
		try {
			dao.remover(usuario);
			assertEquals(dao.existe(usuario.getCpf()), false);
		} catch (ConexaoBancoException | CRUDException  e) {
			fail("Falha na conexão com o banco de dados");
		}
	}
	
	

}
