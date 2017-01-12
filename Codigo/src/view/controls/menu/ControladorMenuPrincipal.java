package view.controls.menu;

import java.io.IOException;

import control.Fachada;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import view.controls.login.ControladorTelaLogin;

public class ControladorMenuPrincipal extends AnchorPane{
	@FXML
	private AnchorPane painelTitulos;
	
	@FXML
	private Circle fotoPerfil;
	
	@FXML
	private Label labelNomeUsuario;
	
	@FXML
	private Circle botaoSair;
	
	@FXML
	private Pane painelPrincipal;
	//maybe here
	
	public ControladorMenuPrincipal(Pane painel){

		try{
			
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/menus/MenuPrincipal.fxml"));
			loader.setController(this);
			this.getChildren().add(loader.load());
			setComponentLayout();
			setDadosUsuario();
			this.painelPrincipal.getChildren().set(0, painel);
		}
		catch(IOException e){
			
		}
	}
	
	public void setComponentLayout(){
		painelTitulos.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth());
		painelTitulos.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth());
		painelTitulos.setRightAnchor(botaoSair, 10.0);
		
		//labelNomeUsuario.setStyle(".label {-fx-font-weight: bold;-fx-text-fill: white;-fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );}");
		//labelNomeUsuario.applyCss();
		
		painelPrincipal.setStyle("fx-background: yellow");
		
	}
	public void setDadosUsuario(){
		//this.fotoPerfil.setFill(new ImagePattern(new Image(Fachada.getInstance().getUsuarioLogado().getCaminhoFoto())));
		this.labelNomeUsuario.setText(Fachada.getInstance().getUsuarioLogado().getNome());

	}
}
