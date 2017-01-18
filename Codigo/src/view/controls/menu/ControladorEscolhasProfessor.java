package view.controls.menu;

import java.io.IOException;

import beans.Aluno;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import view.controls.gerenciamento.ControladorPGerenciaAlunos;
import view.controls.login.ControladorTelaLogin;

public class ControladorEscolhasProfessor extends HBox {
	@FXML
	private Button btTreinosExercicios;
	
	@FXML
	private Button btGerenciarAlunos;
	
	public ControladorEscolhasProfessor(){
		try{
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/menus/EscolhasProfessor.fxml"));
			loader.setController(this);
			this.getChildren().add(loader.load());
			
			btGerenciarAlunos.setId("circleButton");
		}catch(IOException e){
			
		}
	}
	
	@FXML
	public void acaoGerenciarAlunos(ActionEvent e ){
		ControladorMenuPrincipal.getInstance().adcionaTela(new ControladorPGerenciaAlunos<Aluno>(Aluno.class));
	}
	@FXML
	public void acaoTreinosExercicios(ActionEvent e ){
		ControladorMenuPrincipal.getInstance().adcionaTela(new ControladorTelaTreinosExercicios());
	}
	
}
