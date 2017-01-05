package view.controls.login;

import java.net.URL;
import java.util.ResourceBundle;

import control.Fachada;
import exceptions.ConexaoBancoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import view.controls.Principal;
import view.fxmls.FXMLNames;

public class ControladorTelaLogin implements Initializable{
	
	
	@FXML
	private Label labelCPF;
	@FXML
	private Label labelSenha;
	@FXML
	private Button botaoLogin;
	@FXML
	private TextField campoCPF;
	@FXML
	private PasswordField campoSenha;
	@FXML
	private Label labelErro;

	public ControladorTelaLogin(){
		 
	     
	}
	
	
	@FXML
	private void acaoBotaoLogin(ActionEvent evt){
		try {
			if(Fachada.getInstance().autenticar(campoCPF.getText(), campoSenha.getText())){
				Principal.setCurrentStage(FXMLNames.telaEscolhaPerfil);
			}
			else{
				labelErro.setText("Login ou senha incorreto");
				campoCPF.setText("");
				campoSenha.setText("");
			}
		} catch (ConexaoBancoException e) {
			System.out.println(e.getMessage());
		}
		
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}


	

}
