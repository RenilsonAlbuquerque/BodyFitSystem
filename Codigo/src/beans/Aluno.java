package beans;

public class Aluno extends Usuario {
	private int idade;
	private float peso;
	private float altura;
	
	
	
	public Aluno(int idade, float peso, float altura, String cpf, String nome, String senha, String caminhoFoto) {
		super();
		this.setIdade(idade);
		this.setAltura(altura);
		this.setPeso(peso);
	}
	
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public float getPeso() {
		return peso;
	}
	public void setPeso(float peso) {
		this.peso = peso;
	}
	public float getAltura() {
		return altura;
	}
	public void setAltura(float altura) {
		this.altura = altura;
	}
	
	
}
