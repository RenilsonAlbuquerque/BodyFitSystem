package beans;

import java.util.Date;

public class AlunoExecuta implements Comparable<AlunoExecuta>{
	
	private String cpfAluno;
	private int codigoTreino;
	private Date dataExecucao;
	
	public AlunoExecuta(String cpfAluno, int codigoTreino, Date dataExecucao) {
		super();
		this.setCpfAluno(cpfAluno);
		this.setCodigoTreino(codigoTreino);
		this.setDataExecucao(dataExecucao);
	}
	public AlunoExecuta(String cpfAluno, int codigoTreino) {
		super();
		this.setCpfAluno(cpfAluno);
		this.setCodigoTreino(codigoTreino);
	}
	
	public String getCpfAluno() {
		return cpfAluno;
	}
	public void setCpfAluno(String cpfAluno) {
		this.cpfAluno = cpfAluno;
	}
	public int getCodigoTreino() {
		return codigoTreino;
	}
	public void setCodigoTreino(int codigoTreino) {
		this.codigoTreino = codigoTreino;
	}
	public Date getDataExecucao() {
		return dataExecucao;
	}
	public void setDataExecucao(Date dataExecucao) {
		this.dataExecucao = dataExecucao;
	}
	
	@Override
	public boolean equals(Object e){
		return (this.dataExecucao.getDay() == ((AlunoExecuta)e).getDataExecucao().getDay() 
				&& this.dataExecucao.getMonth() == ((AlunoExecuta)e).getDataExecucao().getMonth()
				&& this.dataExecucao.getYear() == ((AlunoExecuta)e).getDataExecucao().getYear())  && 
				(this.getCpfAluno().equals(((AlunoExecuta)e).getCpfAluno()) && this.getCodigoTreino() == ((AlunoExecuta)e).getCodigoTreino());
	}

	@Override
	public int compareTo(AlunoExecuta o) {
		return this.getDataExecucao().compareTo(o.getDataExecucao());
	}

	
	
	
}
