package control;

public class GeradorPDF {

	private static GeradorPDF instance;
	
	private GeradorPDF(){
		
	}
	public static GeradorPDF getInstance(){
		if(instance == null)
			instance = new GeradorPDF();
		return instance;
	} 
}
