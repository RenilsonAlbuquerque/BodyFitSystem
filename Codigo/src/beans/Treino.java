package beans;

import java.util.ArrayList;

public class Treino {
	private int codTp;
	private String nome;
	private ArrayList<Exercicio>exerciciosArray;
	
	public Treino(int codTp, String nome, ArrayList<Exercicio>exerciciosArray) {
		super();
		this.codTp = codTp;
		this.nome = nome;
		this.exerciciosArray = new ArrayList<Exercicio>();
	}
	public int getCodTp() {
		return codTp;
	}
	public void setCodTp(int codTp) {
		this.codTp = codTp;
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
	
}
