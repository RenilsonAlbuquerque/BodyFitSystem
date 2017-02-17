	package beans;

import java.util.ArrayList;

public class Administrador extends Usuario{
	private String cargo;

	
	public Administrador(String cpf, String nome, String senha, String caminhoFoto,String cargo) {
		super(cpf,nome,senha,caminhoFoto);
		this.setCargo(cargo);
	}
	public Administrador(String cpf, String nome, String senha, String caminhoFoto,ArrayList<PerfisEnum> perfis,String cargo) {
		super(cpf,nome,senha,caminhoFoto,perfis);
		this.setCargo(cargo);
	}
	public Administrador(String cpf,String cargo) {
		super(cpf);
		this.setCargo(cargo);
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	
}
