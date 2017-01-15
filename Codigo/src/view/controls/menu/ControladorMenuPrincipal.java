package view.controls.menu;

import java.io.IOException;
import java.util.Optional;
import java.util.Stack;

import control.Fachada;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import view.controls.Principal;
import view.controls.login.ControladorTelaLogin;

public class ControladorMenuPrincipal extends BorderPane{
	
	private static ControladorMenuPrincipal instance;
	private Stack<Pane> memoria;
	
	@FXML
	private GridPane painelTitulos;
	
	@FXML
	private Circle fotoPerfil;
	
	@FXML
	private Label labelNomeUsuario;
	
	@FXML
	private Button botaoSair;
	
	@FXML
	private Button botaoVoltar;
	
	
	
	
	private ControladorMenuPrincipal(){

		try{
			
			memoria = new Stack<Pane>();
			FXMLLoader loader = new FXMLLoader(ControladorTelaLogin.class.getClass().getResource("/view/fxmls/menus/MenuPrincipal.fxml"));
			loader.setController(this);
			this.getChildren().add(loader.load());
			
			
			setComponentLayout();
			setDadosUsuario();
			
			
			this.setTop(painelTitulos);
			
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static ControladorMenuPrincipal getInstance(){
		if(instance == null)
			instance = new ControladorMenuPrincipal();
		return instance;
	}
	public void setComponentLayout(){
		
		painelTitulos.setStyle("-fx-border-color: midnightblue;-fx-border-width: 1px;");
		Circle c = new Circle(3);
		botaoVoltar.setShape(c);
		botaoSair.setShape(c);
		painelTitulos.setId("painelDeTitulos");
		
	}
	public void setDadosUsuario(){
		//this.fotoPerfil.setFill(new ImagePattern(new Image(Fachada.getInstance().getUsuarioLogado().getCaminhoFoto())));
		this.labelNomeUsuario.setText(Fachada.getInstance().getUsuarioLogado().getNome());
	
	}
	
	@FXML
	public void acaoBotaoSair(){
		Alert dialogo = new Alert(Alert.AlertType.CONFIRMATION);
		DialogPane d = dialogo.getDialogPane();
		d.getStylesheets().add(
				   getClass().getResource("/view/style/stylesheet.css").toExternalForm());
				d.getStyleClass().add("dialog-pane");
		dialogo.setContentText("tem certeza que deseja sair?");
		Optional<ButtonType> result = dialogo.showAndWait();
		if(result.get() == ButtonType.OK){
			Principal.setCurrentStage(new ControladorTelaLogin());
		}
			
	}
	
	@FXML
	public void acaoBotaoVoltar(){
		
		if(memoria.size() > 1){
			memoria.pop();
			if(memoria.isEmpty() == false){
				this.adcionaTela(memoria.pop());
			}		
		}	
			
	}
	public void adcionaTela(Pane painel){
		this.setCenter(painel);
		
		if(painel instanceof HBox)
			((HBox) painel).setAlignment(Pos.CENTER);
		if(painel instanceof FlowPane)
			((FlowPane) painel).setAlignment(Pos.CENTER);
		this.memoria.push(painel);

	}
}
