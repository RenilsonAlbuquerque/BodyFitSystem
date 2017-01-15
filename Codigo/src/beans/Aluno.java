package beans;

public class Aluno extends Usuario implements Comparable<Aluno>{
	private int idade;
	private float peso;
	private float altura;
	
	
	
	public Aluno(String cpf, String nome, String senha, String caminhoFoto,
			int idade, float peso, float altura) {
		super(cpf,nome,senha,caminhoFoto);
		this.setIdade(idade);
		this.setAltura(altura);
		this.setPeso(peso);
	}
	public Aluno(String cpf,int idade, float peso, float altura) {
		super(cpf);
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
	
	@Override
	public String toString(){
		return this.getNome();
	}
	@Override
	public int compareTo(Aluno arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
