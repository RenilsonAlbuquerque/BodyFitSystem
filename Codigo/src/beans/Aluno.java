package beans;

import java.util.ArrayList;
import java.util.HashMap;

public class Aluno extends Usuario {
	private int idade;
	private float peso;
	private float altura;
	private String cpfProfessor;
	private ArrayList<Treino> rotinaTreinos;
	private Treino treinoDoDia;
	private HashMap<Integer,Treino> rotinaTreinosMapa;
	
	public Aluno(String cpf,String cpfProf, String nome, String senha, String caminhoFoto,ArrayList<PerfisEnum> perfis,
			int idade, float peso, float altura) {
		super(cpf,nome,senha,caminhoFoto,perfis);
		this.setCpfProfessor(cpfProf);
		this.setIdade(idade);
		this.setAltura(altura);
		this.setPeso(peso);
		this.rotinaTreinos = new ArrayList<Treino>();
	}
	public Aluno(String cpf, String nome, String senha, String caminhoFoto,
			int idade, float peso, float altura) {
		super(cpf,nome,senha,caminhoFoto);
		this.setIdade(idade);
		this.setAltura(altura);
		this.setPeso(peso);
		this.rotinaTreinos = new ArrayList<Treino>();
	}
	public Aluno(String cpf,String cpfProfessor,int idade, float altura, float peso) {
		super(cpf);
		this.setIdade(idade);
		this.setCpfProfessor(cpfProfessor);
		this.setAltura(altura);
		this.setPeso(peso);
		this.rotinaTreinos = new ArrayList<Treino>();
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
	
	public String getCpfProfessor() {
		return cpfProfessor;
	}
	public void setCpfProfessor(String cpfProfessor) {
		this.cpfProfessor = cpfProfessor;
	}
	
	public ArrayList<Treino> getRotinaTreinos() {
		return rotinaTreinos;
	}
	public void setRotinaTreinos(ArrayList<Treino> rotinaTreinos) {
		this.rotinaTreinos = rotinaTreinos;
	}
	
	
	public HashMap<Integer, Treino> getRotinaTreinosMapa() {
		return rotinaTreinosMapa;
	}
	public void setRotinaTreinosMapa(HashMap<Integer, Treino> rotinaTreinosMapa) {
		this.rotinaTreinosMapa = rotinaTreinosMapa;
	}
	public Treino getTreinoDoDia() {
		return treinoDoDia;
	}
	public void setTreinoDoDia(Treino treinoDoDia) {
		this.treinoDoDia = treinoDoDia;
	}
	@Override
	public String toString(){
		return this.getNome();
	}
	
}
