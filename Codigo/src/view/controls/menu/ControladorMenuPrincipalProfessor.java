package view.controls.menu;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import view.controls.login.ControladorTelaLogin;

public class ControladorMenuPrincipalProfessor extends AnchorPane{
	
	@FXML
	private AnchorPane painelTitulos;
	
	@FXML
	private Circle fotoPerfil;
	
	@FXML
	private Label labelNomeUsuario;
	
	@FXML
	private Circle botaoSair;
	
	public ControladorMenuPrincipalProfessor(){

		try{
			
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/menus/MenuPrincipalProfessor.fxml"));
			loader.setController(this);
			this.getChildren().add(loader.load());
			setComponentLayout();
			setDadosUsuario();
		}
		catch(IOException e){
			
		}
	}
	
	public void setComponentLayout(){
		painelTitulos.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth());
		painelTitulos.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth());
		//painelTitulos.setStyle("-fx-background-color: white");
		painelTitulos.setRightAnchor(botaoSair, 10.0);
		
		labelNomeUsuario.setStyle("fx-font-color: white; fx-font-size: 40px");
		
	}
	public void setDadosUsuario(){
		//this.fotoPerfil.setFill(new ImagePattern(new Image("/s")));
		this.labelNomeUsuario.setText("teste");
		
	}

}
