package view.controls.login;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ControladorTelaEscolhaPerfil implements Initializable{
	
	@FXML
	private GridPane painel;
	@FXML
	private Button botaoProfessor;
	@FXML
	private Button botaoAluno;
	@FXML
	private Button botaoAdministrador;
	@FXML
	private Button botaoCoordenador;
	@FXML
	private Button botaoVoltar;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	

	public void adcionarBotao(Button botao){
		
		 painel.setRowIndex(botao,1);

	} 
	


}
