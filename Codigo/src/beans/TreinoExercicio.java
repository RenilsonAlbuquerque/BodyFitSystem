package beans;

public class TreinoExercicio {

	private int codigoTreino;
	private int codigoExercicio;
	
	
	public TreinoExercicio(int codigoTreino, int codigoExercicio) {
		super();
		this.setCodigoTreino(codigoTreino);
		this.setCodigoExercicio(codigoExercicio);
	}
	
	public int getCodigoTreino() {
		return codigoTreino;
	}
	public void setCodigoTreino(int codigoTreino) {
		this.codigoTreino = codigoTreino;
	}
	public int getCodigoExercicio() {
		return codigoExercicio;
	}
	public void setCodigoExercicio(int codigoExercicio) {
		this.codigoExercicio = codigoExercicio;
	}
	
	
	
}
