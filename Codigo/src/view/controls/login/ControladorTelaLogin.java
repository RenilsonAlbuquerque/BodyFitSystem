package view.controls.login;

import control.Fachada;
import exceptions.ConexaoBancoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import view.controls.Principal;

public class ControladorTelaLogin extends AnchorPane{
	
	
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
		 try {
			 FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/login/TelaLogin.fxml"));
			 loader.setController(this);
			 this.getChildren().add(loader.load());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	     
	}
	
	
	@FXML
	private void acaoBotaoLogin(ActionEvent evt){
		try {
			if(Fachada.getInstance().autenticar(campoCPF.getText(), campoSenha.getText())){
				//Fachada.getInstance().setUsuarioLogado();
				Principal.setCurrentStage(new ControladorTelaEscolhaPerfil(campoCPF.getText()));
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


	

	

}
