package beans;

import java.util.ArrayList;

public class Professor extends Usuario{
	private String cref;
	private String turno;
	private boolean coordenador;
			
	public Professor(String cpf, String nome, String senha, String caminhoFoto,ArrayList<PerfisEnum> perfis,
			String cref, String turno,boolean coordenador) {
		super(cpf,nome,senha,caminhoFoto,perfis);
		this.setCref(cref);
		this.setTurno(turno);
		this.setCoordenador(coordenador);
		
	}
	public Professor(String cpf, String nome, String senha, String caminhoFoto,
			String cref, String turno,boolean coordenador) {
		super(cpf,nome,senha,caminhoFoto);
		this.setCref(cref);
		this.setTurno(turno);
		this.setCoordenador(coordenador);
		
	}
	public Professor(String cpf,
			String cref, String turno,boolean coordenador) {
		super(cpf);
		this.setCref(cref);
		this.setTurno(turno);
		this.setCoordenador(coordenador);
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
	public boolean isCoordenador() {
		return coordenador;
	}
	public void setCoordenador(boolean coordenador) {
		this.coordenador = coordenador;
	}
	
	
}
