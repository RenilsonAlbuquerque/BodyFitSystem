package view.controls.login;

import control.Fachada;
import exceptions.CRUDException;
import exceptions.ConexaoBancoException;
import exceptions.NegocioException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import view.controls.Principal;

public class ControladorTelaLogin extends FlowPane{
	
	
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
			 this.setAlignment(Pos.CENTER);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	     
	}
	
	
	@FXML
	private void acaoBotaoLogin(ActionEvent evt){
		try {
			if(Fachada.getInstance().autenticar(campoCPF.getText(), campoSenha.getText())){
				Principal.setCurrentStage(new ControladorTelaEscolhaPerfil(campoCPF.getText()));
			}
			else{
				labelErro.setText("Login ou senha está incorreto");
				campoCPF.setText("");
				campoSenha.setText("");
			}
		} catch (ConexaoBancoException | CRUDException | NegocioException e) {
			System.out.println(e.getMessage());
		}
		
	}	

}
