package view.controls.menu;

import java.io.IOException;

import control.Contexto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import view.controls.gerenciamento.ControladorPGerenciaExercicioPadrao;
import view.controls.gerenciamento.ControladorPGerenciaExercicios;
import view.controls.gerenciamento.ControladorPGerenciaTreinos;
import view.controls.login.ControladorTelaLogin;

public class ControladorTelaTreinosExerciciosCoordenador extends FlowPane {
	
	@FXML
	private Button btnBancoTreinos;
	
	@FXML
	private Button btnBancoExercicios;
	
	@FXML
	private Button btnMeusTreinos;
	
	@FXML
	private Button btnMeusExercicios;
	
	public ControladorTelaTreinosExerciciosCoordenador(){
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
		ControladorMenuPrincipal.getInstance().adcionaTela(new ControladorPGerenciaExercicioPadrao());
	}
	@FXML
	public void acaoMeusTreinos(){
		ControladorMenuPrincipal.getInstance().adcionaTela(new ControladorPGerenciaTreinos(Contexto.getInstance().getUsuarioLogado().getCpf()));
	}
	@FXML
	public void acaoMeusExercicios(){
		ControladorMenuPrincipal.getInstance().adcionaTela(new ControladorPGerenciaExercicios());
	}
	

}
