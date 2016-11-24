package beans;

public class Administrador extends Usuario{
	private String cargo;

	
	public Administrador(String cargo) {
		super();
		this.setCargo(cargo);
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	
}
