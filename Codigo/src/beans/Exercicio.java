package beans;

public class Exercicio {
	private String nome;
	private String carga;
	private int intervalo;
	private int repeticao;
	private int codigo;
	
	
	
	public Exercicio(int codigo,String nome, String carga, int intervalo, int repeticao) {
		super();
		this.setNome(nome);
		this.setCarga(carga);
		this.setIntervalo(intervalo);
		this.setRepeticao(repeticao);
		this.setCodigo(codigo);
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

	
}
