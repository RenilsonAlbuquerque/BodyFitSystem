package view.controls.visualizacao;

import java.io.IOException;

import control.Fachada;
import exceptions.ConexaoBancoException;
import exceptions.NegocioException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.controls.login.ControladorTelaLogin;

public class ControladorVisualizacaoPerfilProprio extends HBox{
	
	
	private Stage estagio;
	
	@FXML
	private VBox painelPrincipal;
	
	@FXML
	private Button btnAlterarSenha;
	
	public ControladorVisualizacaoPerfilProprio(){	
		
		try{
			this.setAlignment(Pos.CENTER);
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/visualizacao/VisualisacaoPerfilProprio.fxml"));
			loader.setController(this);
			
			this.getChildren().add(loader.load());
			this.painelPrincipal.getChildren().add(0,new ControladorTelaVisualizacao(Fachada.getInstance().getUsuarioLogado()));
			
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
	
		
			
		this.estagio = new Stage();
		this.estagio.setScene(new Scene(this));
		this.estagio.getScene().getStylesheets().add("/view/style/stylesheet.css");
		this.estagio.show();
	}
	
	
	@FXML
	public void acaoAlterarSenha(){
		
		Stage dialogo = new Stage();
		
		GridPane painel = new GridPane();
		painel.setHgap(10);
		painel.setVgap(10);
		painel.setPadding(new Insets(20,150,10,10));
		
		PasswordField senhaAntiga = new PasswordField();
		PasswordField novaSenha = new PasswordField();
		Label erro = new Label();
		Button botaoConfirmar = new Button("Alterar");
		
		painel.add(new Label("Senha antiga:"), 0, 0);
		painel.add(senhaAntiga, 1, 0);
		painel.add(new Label("Nova Senha:"), 0, 1);
		painel.add(novaSenha, 1, 1);
		painel.add(erro, 1, 2);
		painel.add(botaoConfirmar, 2, 3);
		
		
		
		botaoConfirmar.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	try {
					if(Fachada.getInstance().autenticar(Fachada.getInstance().getUsuarioLogado().getCpf(), senhaAntiga.getText())){
						Fachada.getInstance().getUsuarioLogado().setSenha(novaSenha.getText());
						Fachada.getInstance().atualizarUsuario(Fachada.getInstance().getUsuarioLogado());
						dialogo.close();
					}
					else{
						erro.setText("A senha atual não está certa");
					}
				} catch (NegocioException ex) {
					erro.setText(ex.getMessage());
				} 		
		    }
		});
		
		
		dialogo.setScene(new Scene(painel));
		dialogo.getScene().getStylesheets().add("/view/style/stylesheet.css");
		dialogo.show();
	
		

	
	}
	
}
