package beans;

import java.util.ArrayList;

public class Usuario implements Comparable<Usuario>
{
	private String cpf;
	private String nome;
	private String senha;
	private String caminhoFoto;
	private ArrayList<PerfisEnum> perfis;	

	
	public Usuario(String cpf, String nome, String senha, String caminhoFoto,ArrayList<PerfisEnum> perfis){
		this.setCpf(cpf);
		this.setNome(nome);
		this.setSenha(senha);
		this.setCaminhoFoto(caminhoFoto);
		this.setPerfis(perfis);
		
	}
	
	public Usuario(String cpf, String nome, String senha, String caminhoFoto){
		this.setCpf(cpf);
		this.setNome(nome);
		this.setSenha(senha);
		this.setCaminhoFoto(caminhoFoto);
		
	}
	
	public Usuario(String cpf){
		this.setCpf(cpf);
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getCaminhoFoto() {
		return caminhoFoto;
	}
	public void setCaminhoFoto(String caminhoFoto) {
		this.caminhoFoto = caminhoFoto;
	}
	
	public ArrayList<PerfisEnum> getPerfis() {
		return perfis;
	}
	public void setPerfis(ArrayList<PerfisEnum> perfis) {
		this.perfis = perfis;
	}
	@Override
	public String toString(){
		return this.getNome();
	}
	@Override
	public int compareTo(Usuario usuario) {
		return this.getNome().compareTo(usuario.getNome());
	}
	
	
}
