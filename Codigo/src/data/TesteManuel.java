package data;

import beans.Aluno;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;

public class TesteManuel {
	
	
	public static void main(String args[]){
		Aluno aluno = new Aluno("10870298488","Renilson","1234", "caminho",22, 70, 1.90f);
		
		UsuarioDao dao = new UsuarioDao();
		try {
			dao.autenticar(aluno.getCpf(),aluno.getSenha());
		} catch (ConexaoBancoException e) {
			
			e.printStackTrace();
		}
	}

}
