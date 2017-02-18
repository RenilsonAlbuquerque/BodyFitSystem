package view.controls.menu;

import java.io.IOException;

import control.Fachada;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import view.controls.gerenciamento.ControladorPGerenciaExercicios;
import view.controls.gerenciamento.ControladorPGerenciaTreinos;
import view.controls.login.ControladorTelaLogin;

public class ControladorTelaTreinosExerciciosProfessor extends FlowPane{
	
	@FXML
	private Button btnBancoTreinos;
	
	@FXML
	private Button btnBancoExercicios;
	
	@FXML
	private Button btnMeusTreinos;
	
	@FXML
	private Button btnMeusExercicios;
	
	public ControladorTelaTreinosExerciciosProfessor(){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/menus/TelaTreinosExerciciosProfessor.fxml"));
			loader.setController(this);
			this.getChildren().add(loader.load());
		}catch(IOException e){
			e.getMessage();
		}
	}
	@FXML
	public void acaoMeusTreinos(){
		ControladorMenuPrincipal.getInstance().adcionaTela(new ControladorPGerenciaTreinos(Fachada.getInstance().getUsuarioLogado().getCpf()));
	}
	@FXML
	public void acaoMeusExercicios(){
		ControladorMenuPrincipal.getInstance().adcionaTela(new ControladorPGerenciaExercicios());
	}
	

}
