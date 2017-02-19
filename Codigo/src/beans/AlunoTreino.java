package beans;

public class AlunoTreino implements Comparable<AlunoTreino>{
	
	private String cpfAluno;
	private int codigoTreino;
	private int ordem;
	
	public AlunoTreino(String cpfAluno, int codigoTreino,int ordem) {
		super();
		this.setCodigoAluno(cpfAluno);
		this.setCodigoTreino(codigoTreino);
		this.setOrdem(ordem);
	}
	public String getCpfAluno() {
		return cpfAluno;
	}
	public void setCodigoAluno(String cpfAluno) {
		this.cpfAluno = cpfAluno;
	}
	public int getCodigoTreino() {
		return codigoTreino;
	}
	public void setCodigoTreino(int codigoTreino) {
		this.codigoTreino = codigoTreino;
	}
	public int getOrdem() {
		return ordem;
	}
	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	@Override
	public int compareTo(AlunoTreino o) {
		if(this.ordem > o.getOrdem())
			return 1;
		else
			return 0;
	}

}
