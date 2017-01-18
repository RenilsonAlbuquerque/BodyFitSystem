package view.controls.menu;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import view.controls.login.ControladorTelaLogin;

public class ControladorOpcoesAdministrador extends FlowPane{
	
	@FXML
	private Button botaoGerenciarAlunos;
	
	@FXML
	private Button botaoGerenciarProfessores;
	
	@FXML
	private Button botaoGerenciarCoordenadores;
	
	@FXML
	private Button botaoGerenciarAdminsitradores;
	
	
	public ControladorOpcoesAdministrador(){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/menus/TelaOpcoesAdministrador.fxml"));
			loader.setController(this);
			this.getChildren().add(loader.load());
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@FXML
	public void acaoBtnAluno(){
		
	}
	
	@FXML
	public void acaoBtnProfessor(){
		
	}
	@FXML
	public void acaoBtnCoordenador(){
		
	}
	
	@FXML
	public void acaoBtnAdministrador(){
		
	}

}
