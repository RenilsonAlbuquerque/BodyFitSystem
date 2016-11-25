package exceptions;

public class ConexaoBancoException extends Exception{

	public ConexaoBancoException(){
		super("Erro ao conectar ao banco de dados");
	}
}
