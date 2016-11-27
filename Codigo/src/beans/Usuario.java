package beans;

public abstract class Usuario 
{
	private String cpf;
	private String nome;
	private String senha;
	private String caminhoFoto;
	
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
	
	
}
