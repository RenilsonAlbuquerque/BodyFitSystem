package beans;

public class AlunoTreino {
	
	private int codigoAluno;
	private int codigoTreino;

	
	public AlunoTreino(int codigoAluno, int codigoTreino) {
		super();
		this.setCodigoAluno(codigoAluno);
		this.setCodigoTreino(codigoTreino);
	}
	public int getCodigoAluno() {
		return codigoAluno;
	}
	public void setCodigoAluno(int codigoAluno) {
		this.codigoAluno = codigoAluno;
	}
	public int getCodigoTreino() {
		return codigoTreino;
	}
	public void setCodigoTreino(int codigoTreino) {
		this.codigoTreino = codigoTreino;
	}

}
