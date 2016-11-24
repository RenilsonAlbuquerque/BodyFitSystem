package beans;

public class Exercicio {
	private String nome;
	private String carga;
	private int intervalo;
	private int repeticao;
	private int codigoEp;
	
	
	
	public Exercicio(String nome, String carga, int intervalo, int repeticao, int codigoEp) {
		super();
		this.setNome(nome);
		this.setCarga(carga);
		this.setIntervalo(intervalo);
		this.setRepeticao(repeticao);
		this.setCodigoEp(codigoEp);
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
	public int getCodigoEp() {
		return codigoEp;
	}
	public void setCodigoEp(int codigoEp) {
		this.codigoEp = codigoEp;
	}

	
}
