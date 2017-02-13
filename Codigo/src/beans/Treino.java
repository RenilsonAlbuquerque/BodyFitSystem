package beans;

import java.util.ArrayList;

public class Treino implements Comparable<Treino>{
	private int codigo;
	private String nome;
	private ArrayList<Exercicio> exerciciosArray;
	private boolean padrao;
	
	public Treino(String nome) {
		this.setNome(nome);
		this.exerciciosArray = new ArrayList<Exercicio>();
	}
	public Treino(int codigo, String nome) {
		this.setCodigo(codigo);
		this.setNome(nome);
		this.exerciciosArray = new ArrayList<Exercicio>();
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public ArrayList<Exercicio> getExerciciosArray() {
		return exerciciosArray;
	}
	public void setExerciciosArray(ArrayList<Exercicio> exerciciosArray) {
		this.exerciciosArray = exerciciosArray;
	}
	public boolean isPadrao() {
		return padrao;
	}
	public void setPadrao(boolean padrao) {
		this.padrao = padrao;
	}
	@Override
	public String toString(){
		return this.getNome();
	}
	@Override
	public int compareTo(Treino treino) {
		return this.getNome().compareTo(treino.getNome());
	}
	
	
}
