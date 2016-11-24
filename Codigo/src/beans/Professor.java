package beans;

public class Professor extends Usuario{
	private String cref;
	private String turno;
			
	public Professor(String cref, String turno, String cpf, String nome, String senha, String caminhoFoto) {
		super();
		this.setCref(cref);
		this.setTurno(turno);
	}
	
	public String getCref() {
		return cref;
	}

	public void setCref(String cref) {
		this.cref = cref;
	}
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	
}
