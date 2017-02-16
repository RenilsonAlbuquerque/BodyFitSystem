package beans;

public class Exercicio implements Comparable<Exercicio>{
	private int codigo;
	private String nome;
	private String carga;
	private int intervalo;
	private int repeticao;
	private boolean padrao;
	private String cpfProfessor;
	
	/*
	public Exercicio(String nome, String carga, int intervalo, int repeticao,boolean padrao) {
		super();
		this.setNome(nome);
		this.setCarga(carga);
		this.setIntervalo(intervalo);
		this.setRepeticao(repeticao);
		this.setCodigo(codigo);
		this.setPadrao(padrao);
	}
	*/
	public Exercicio(int codigo,String cpf,String nome, String carga, int intervalo, int repeticao,boolean padrao) {
		super();
		this.setNome(nome);
		this.setCarga(carga);
		this.setIntervalo(intervalo);
		this.setRepeticao(repeticao);
		this.setCodigo(codigo);
		this.setCpfProfessor(cpf);
		this.setPadrao(padrao);
	}
	public Exercicio(String cpf,String nome, String carga, int intervalo, int repeticao,boolean padrao) {
		super();
		this.setNome(nome);
		this.setCarga(carga);
		this.setIntervalo(intervalo);
		this.setRepeticao(repeticao);
		this.setCodigo(codigo);
		this.setPadrao(padrao);
		this.setCpfProfessor(cpf);
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCarga() {
		return carga;
	}
	public void setCarga(String carga) {
		this.carga = carga;
	}
	public int getIntervalo() {
		return intervalo;
	}
	public void setIntervalo(int intervalo) {
		this.intervalo = intervalo;
	}
	public int getRepeticao() {
		return repeticao;
	}
	public void setRepeticao(int repeticao) {
		this.repeticao = repeticao;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public boolean isPadrao() {
		return padrao;
	}

	public void setPadrao(boolean padrao) {
		this.padrao = padrao;
	}
	

	public String getCpfProfessor() {
		return this.cpfProfessor;
	}

	public void setCpfProfessor(String cpf) {
		this.cpfProfessor = cpf;
	}

	@Override
	public String toString(){
		return this.nome;
	}
	
	@Override
	public int compareTo(Exercicio exercicio) {
		return this.getNome().compareTo(exercicio.getNome());
	}
	
	
}
