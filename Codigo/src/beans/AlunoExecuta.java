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
	public int compareTo(AlunoExecuta o) {
		return this.getDataExecucao().compareTo(o.getDataExecucao());
	}

	
	
	
}
