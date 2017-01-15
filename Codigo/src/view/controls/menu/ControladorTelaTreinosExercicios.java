package view.controls.menu;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import view.controls.login.ControladorTelaLogin;

public class ControladorTelaTreinosExercicios extends FlowPane {
	
	@FXML
	private Button btnBancoTreinos;
	
	@FXML
	private Button btnBancoExercicios;
	
	@FXML
	private Button btnMeusTreinos;
	
	@FXML
	private Button btnMeusExercicios;
	
	public ControladorTelaTreinosExercicios(){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/menus/TelaTreinosExercicios.fxml"));
			loader.setController(this);
			this.getChildren().add(loader.load());
		}catch(IOException e){
			e.getMessage();
		}
	}
	
	@FXML
	public void acaoBancoTreinos(){
		
	}
	@FXML
	public void acaoBancoExercicios(){
		
	}
	@FXML
	public void acaoMeusTreinos(){
		
	}
	@FXML
	public void acaoMeusExercicios(){
		
	}
	

}